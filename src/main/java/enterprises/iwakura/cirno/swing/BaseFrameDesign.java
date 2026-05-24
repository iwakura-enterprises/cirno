package enterprises.iwakura.cirno.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.concurrent.CompletableFuture;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Base design frame from which all other designs should be extended<br>
 * Methods are called in the following order:<br>
 * 1. {@link #prepareComponents()} - Creates the instances of your mutable components which will be used in next steps<br>
 * 2. {@link #registerListeners()} - Registers the component's listeners<br>
 * 3. {@link #prepareFrame(Component)} - Prepares the frame and packs it<br>
 */
public abstract class BaseFrameDesign extends JFrame implements WindowListener, WindowStateListener, WindowFocusListener {

    /**
     * Used for waiting for the frame to close
     */
    protected final Object afterDisposeMutex = new Object();

    /**
     * Whether this is the first time the frame is opened
     */
    protected boolean firstOpen = true;

    /**
     * Whether the frame is disposed
     */
    protected boolean disposed = false;

    /**
     * Used to run custom code before the frame is disposed
     */
    private Runnable runBeforeDisposeInternal = () -> {
    };

    // == Constructors == //

    /**
     * Creates a new BaseFrameDesign
     *
     * @param parentComponent The parent component
     */
    public BaseFrameDesign(Component parentComponent) {
        super();

        addWindowListener(this);
        addWindowStateListener(this);
        addWindowFocusListener(this);

        CinnamonRoll.onEscapePress(this, this::onEscapePressed);
        CinnamonRoll.onEnterPress(this, this::onEnterPressed);

        prepareComponents();
        registerListeners();
        prepareFrame(parentComponent);
    }

    /**
     * Creates a new BaseFrameDesign<br>calls {@link #BaseFrameDesign(Component)} with null
     */
    public BaseFrameDesign() {
        this(null);
    }

    // == Loading == //

    /**
     * Prepares the components
     */
    protected abstract void prepareComponents();

    /**
     * Prepares the frame with the components (called after the data is loaded)
     *
     * @param parentComponent The parent component (can be null)
     */
    protected abstract void prepareFrame(Component parentComponent);

    /**
     * Registers listeners (called after the frame is prepared)
     */
    protected void registerListeners() {
    }

    // == API == //

    /**
     * Opens the frame (makes it visible)
     */
    public void openFrame() {
        setVisible(true);
    }

    /**
     * Opens the frame and then runs the given consumer after the frame is disposed<br>
     * Does not block the current thread
     *
     * @param onFrameDisposed The consumer to run after the frame is disposed
     */
    public void openFrameAndThenAsync(Runnable onFrameDisposed) {
        SwingUtilities.invokeLater(this::openFrame);

        CompletableFuture.runAsync(() -> {
            waitUntilDisposed();

            onFrameDisposed.run();
        });
    }

    /**
     * Opens the frame and blocks the parent component until the frame is disposed<br>
     * By blocking, the parent component is disabled until the frame is disposed
     *
     * @param parentComponent The parent component
     * @param onFrameDisposed The consumer to run after the frame is disposed
     */
    public void openFrameBlockParentAndThenAsync(Component parentComponent, Runnable onFrameDisposed) {
        SwingUtilities.invokeLater(() -> {
            parentComponent.setEnabled(false);
            this.openFrame();
        });

        CompletableFuture.runAsync(() -> {
            runBeforeDisposeInternal = () -> {
                parentComponent.setEnabled(true); // We are in event dispatch thread

                onFrameDisposed.run();
            };

            this.waitUntilDisposed();
        });
    }

    /**
     * Waits until the frame is disposed<br>
     * If the frame is already disposed, this method will return immediately<br>
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
     * Called before the frame is opened
     *
     * @param firstOpen Whether this is the first time the frame is opened
     */
    protected void onBeforeOpened(boolean firstOpen) {
    }

    /**
     * Called before the frame is disposed
     */
    protected void beforeDispose() {
    }

    /**
     * Called after the frame is disposed
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

    /**
     * Invoked when a escape key is pressed
     *
     * @param event The ActionEvent
     */
    public void onEscapePressed(ActionEvent event) {
    }

    /**
     * Invoked when a enter key is pressed
     *
     * @param event The ActionEvent
     */
    public void onEnterPressed(ActionEvent event) {
    }

    // == Other == //

    /**
     * Calls {@link #setEnabled(boolean)} wrapped in {@link SwingUtilities#invokeLater(Runnable)}
     *
     * @param enabled Whether to enable the frame
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
