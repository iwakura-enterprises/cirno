package enterprises.iwakura.cirno.swing;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.concurrent.CompletableFuture;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 * Base design dialog from which all other designs should be extended<br>
 * Methods are called in the following order:<br>
 * 1. {@link #prepareComponents()} - Creates the instances of your mutable components which will be used in next
 * steps<br>
 * 2. {@link #registerListeners()} - Registers the component's listeners<br>
 * 3. {@link #prepareDialog(Component)} - Prepares the dialog and packs it<br>
 */
public abstract class BaseDialogDesign extends JDialog implements WindowListener, WindowStateListener, WindowFocusListener {

    /**
     * Used for waiting for the dialog to close
     */
    protected final Object afterDisposeMutex = new Object();

    /**
     * Whether this is the first time the dialog is opened
     */
    protected boolean firstOpen = true;

    /**
     * Whether the dialog is disposed
     */
    protected boolean disposed = false;

    /**
     * Used to run custom code before the dialog is disposed
     */
    private Runnable runBeforeDisposeInternal = () -> {
    };

    // == Constructors == //

    /**
     * Creates a new BaseDialogDesign
     *
     * @param parentComponent The parent component
     */
    public BaseDialogDesign(Component parentComponent) {
        super();

        addWindowListener(this);
        addWindowStateListener(this);
        addWindowFocusListener(this);

        prepareComponents();
        registerListeners();
        prepareDialog(parentComponent);
    }

    /**
     * Creates a new BaseDialogDesign<br>calls {@link #BaseDialogDesign(Component)} with null
     */
    public BaseDialogDesign() {
        this(null);
    }

    // == Loading == //

    /**
     * Prepares the components
     */
    protected abstract void prepareComponents();

    /**
     * Prepares the dialog with the components (called after the data is loaded)
     *
     * @param parentComponent The parent component (can be null)
     */
    protected abstract void prepareDialog(Component parentComponent);

    /**
     * Registers listeners (called after the dialog is prepared)
     */
    protected void registerListeners() {
    }

    // == API == //

    /**
     * Opens the dialog (makes it visible)
     */
    public void openDialog() {
        setVisible(true);
    }

    /**
     * Opens the dialog and then runs the given consumer after the dialog is disposed<br>
     * Does not block the current thread
     *
     * @param onDialogDisposed The consumer to run after the dialog is disposed
     */
    public void openDialogAndThenAsync(Runnable onDialogDisposed) {
        SwingUtilities.invokeLater(this::openDialog);

        CompletableFuture.runAsync(() -> {
            waitUntilDisposed();

            onDialogDisposed.run();
        });
    }

    /**
     * Opens the dialog and blocks the parent component until the dialog is disposed<br>
     * By blocking, the parent component is disabled until the dialog is disposed
     *
     * @param parentComponent  The parent component
     * @param onDialogDisposed The consumer to run after the dialog is disposed
     */
    public void openDialogBlockParentAndThenAsync(Component parentComponent, Runnable onDialogDisposed) {
        SwingUtilities.invokeLater(() -> {
            parentComponent.setEnabled(false);
            this.openDialog();
        });

        CompletableFuture.runAsync(() -> {
            runBeforeDisposeInternal = () -> {
                parentComponent.setEnabled(true); // We are in event dispatch thread

                onDialogDisposed.run();
            };

            this.waitUntilDisposed();
        });
    }

    /**
     * Waits until the dialog is disposed<br>
     * If the dialog is already disposed, this method will return immediately<br>
     * Wraps the InterruptedException in a RuntimeException
     */
    public void waitUntilDisposed() {
        if (disposed) {
            return;
        }

        synchronized (afterDisposeMutex) {
            try {
                afterDisposeMutex.wait();
            } catch (InterruptedException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    // == Events == //

    /**
     * Called before the dialog is opened
     *
     * @param firstOpen Whether this is the first time the dialog is opened
     */
    protected void onBeforeOpened(boolean firstOpen) {
    }

    /**
     * Called before the dialog is disposed
     */
    protected void beforeDispose() {
    }

    /**
     * Called after the dialog is disposed
     */
    protected void afterDispose() {

    }

    /**
     * {@link WindowEvent#WINDOW_OPENED} event
     *
     * @param event The WindowEvent
     */
    public void windowOpened(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_CLOSING} event
     *
     * @param event The WindowEvent
     */
    public void windowClosing(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_CLOSED} event
     *
     * @param event The WindowEvent
     */
    public void windowClosed(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_ICONIFIED} event
     *
     * @param event The WindowEvent
     */
    public void windowIconified(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_DEICONIFIED} event
     *
     * @param event The WindowEvent
     */
    public void windowDeiconified(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_ACTIVATED} event
     *
     * @param event The WindowEvent
     */
    public void windowActivated(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_DEACTIVATED} event
     *
     * @param event The WindowEvent
     */
    public void windowDeactivated(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_STATE_CHANGED} event
     *
     * @param event The WindowEvent
     */
    public void windowStateChanged(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_GAINED_FOCUS} event
     *
     * @param event The WindowEvent
     */
    public void windowGainedFocus(WindowEvent event) {
    }

    /**
     * {@link WindowEvent#WINDOW_LOST_FOCUS} event
     *
     * @param event The WindowEvent
     */
    public void windowLostFocus(WindowEvent event) {
    }

    // == Other == //

    /**
     * Calls {@link #setEnabled(boolean)} wrapped in {@link SwingUtilities#invokeLater(Runnable)}
     *
     * @param enabled Whether to enable the dialog
     */
    public void setEnabledLater(boolean enabled) {
        SwingUtilities.invokeLater(() -> {
            setEnabled(enabled);
        });
    }

    // == Overrides == //

    @Override
    public void setVisible(boolean flag) {
        if (flag) {
            onBeforeOpened(firstOpen);

            firstOpen = false;
        }

        super.setVisible(flag);
    }

    @Override
    public void dispose() {
        runBeforeDisposeInternal.run();
        beforeDispose();
        super.dispose();
        disposed = true;
        afterDispose();

        synchronized (afterDisposeMutex) {
            afterDisposeMutex.notifyAll();
        }
    }
}
