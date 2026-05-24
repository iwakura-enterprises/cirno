package enterprises.iwakura.cirno.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import enterprises.iwakura.cirno.swing.listeners.MouseClickListener;
import enterprises.iwakura.cirno.swing.listeners.TextFieldValueChangedListener;

/**
 * Utility class for registering listeners and other stuff.
 */
public final class CinnamonRoll {

    public static final int KEY_MODIFIERS_MASK =
        InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK | InputEvent.ALT_DOWN_MASK | InputEvent.META_DOWN_MASK;
    public static final Font MONOSPACED_FONT_CACHE = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    /**
     * Prevent instantiation of this utility class.
     */
    private CinnamonRoll() {
    }

    /**
     * Registers a window listener with windowClosing to a {@link Window}.
     *
     * @param window    The {@link Window} to register the listener to.
     * @param onClosing The {@link Consumer} to call when the {@link Window} is closing.
     */
    public static void onClosing(Window window, Consumer<WindowEvent> onClosing) {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClosing.accept(e);
            }
        });
    }

    /**
     * Registers a window listener with windowClosed to a {@link Window}.
     *
     * @param window   The {@link Window} to register the listener to.
     * @param onClosed The {@link Consumer} to call when the {@link Window} is closed.
     */
    public static void onClosed(Window window, Consumer<WindowEvent> onClosed) {
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                onClosed.accept(e);
            }
        });
    }

    /**
     * Registers a {@link MouseClickListener} to a {@link Component}.
     *
     * @param component The {@link Component} to register the listener to.
     * @param onClick   The {@link Consumer} to call when the {@link Component} is clicked.
     */
    public static void onClick(Component component, Consumer<MouseEvent> onClick) {
        MouseClickListener.register(component, onClick);
    }

    /**
     * Registers a {@link TextFieldValueChangedListener} to a {@link JTextField}.
     *
     * @param textField      The {@link JTextField} to register the listener to.
     * @param onValueChanged The {@link Consumer} to call when the value of the {@link JTextField} changes.
     */
    public static void onValueChanged(JTextField textField, Consumer<DocumentEvent> onValueChanged) {
        TextFieldValueChangedListener.register(textField, onValueChanged);
    }

    /**
     * Registers a {@link TextFieldValueChangedListener} to a {@link JTextField}.
     *
     * @param textField The {@link JTextField} to register the listener to.
     * @param onConfirm The {@link Consumer} to call when the enter (or any confirm) key is pressed.
     */
    public static void onConfirm(JTextField textField, Consumer<ActionEvent> onConfirm) {
        textField.addActionListener(onConfirm::accept);
    }

    /**
     * Limits the length of a {@link JTextField} using a {@link DocumentLengthLimit}.
     *
     * @param textField The {@link JTextField} to limit the length of.
     * @param limit     The limit to use.
     */
    public static void limitDocumentLength(JTextField textField, int limit) {
        textField.setDocument(new DocumentLengthLimit(limit));
    }

    /**
     * Waits until a {@link Window} is disposed.<br>
     * If the passed {@link Window} is an instance of {@link BaseFrameDesign}, it will call
     * {@link BaseFrameDesign#waitUntilDisposed()}.<br>
     * Otherwise, it will create window closed listener and wait for it to be called.<br>
     * The {@link InterruptedException} is wrapped in a {@link RuntimeException}.
     *
     * @param window The {@link Window} to wait for.
     */
    public static void waitUntilDisposed(Window window) {
        if (window instanceof BaseFrameDesign) {
            ((BaseFrameDesign) window).waitUntilDisposed();
            return;
        }

        final Object mutex = new Object();

        synchronized (mutex) {
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    synchronized (mutex) {
                        mutex.notify();
                    }
                }
            });

            try {
                mutex.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates a vertical {@link JSeparator}.
     *
     * @return The vertical {@link JSeparator}.
     */
    public static JSeparator verticalSeparator() {
        return new JSeparator(SwingConstants.VERTICAL);
    }

    /**
     * Creates a horizontal {@link JSeparator}.
     *
     * @return The horizontal {@link JSeparator}.
     */
    public static JSeparator horizontalSeparator() {
        return new JSeparator(SwingConstants.HORIZONTAL);
    }

    /**
     * Creates a global keystroke listener.
     *
     * @param keystroke Keystroke (e.g. "control shift F1")
     * @param onPress   Consumer to run when the keystroke is pressed supplied by the event.
     */
    public static void registerGlobalKeystroke(String keystroke, Consumer<AWTEvent> onPress) {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keystroke);

        Toolkit.getDefaultToolkit().addAWTEventListener((e) -> {
            if (e.getID() == KeyEvent.KEY_RELEASED &&
                ((KeyEvent) e).getKeyCode() == keyStroke.getKeyCode() &&
                (((KeyEvent) e).getModifiersEx() & KEY_MODIFIERS_MASK) == (keyStroke.getModifiers()
                    & KEY_MODIFIERS_MASK)) {
                onPress.accept(e);
            }
        }, AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * Derive the font of a component with a new style and size and set it.
     *
     * @param component The component to derive the font of and set to.
     * @param style     The new style.
     * @param size      The new size.
     */
    public static void deriveFontWith(JComponent component, int style, int size) {
        component.setFont(component.getFont().deriveFont(style, size));
    }

    /**
     * Make the foreground of a component darker.
     *
     * @param component The component.
     */
    public static void makeForegroundDarker(JComponent component) {
        component.setForeground(component.getForeground().darker());
    }

    /**
     * Make the foreground of a component lighter.
     *
     * @param component The component.
     */
    public static void makeForegroundLighter(JComponent component) {
        component.setForeground(component.getForeground().brighter());
    }

    /**
     * Make the background of a component darker.
     *
     * @param component The component.
     */
    public static void makeBackgroundDarker(JComponent component) {
        component.setBackground(component.getBackground().darker());
    }

    /**
     * Make the background of a component lighter.
     *
     * @param component The component.
     */
    public static void makeBackgroundLighter(JComponent component) {
        component.setBackground(component.getBackground().brighter());
    }

    /**
     * Create a monospaced font.
     *
     * @return The created font.
     */
    public static Font createMonospacedFont() {
        return MONOSPACED_FONT_CACHE;
    }

    /**
     * Create a monospaced font with the specified size.
     *
     * @param size The size of the font.
     *
     * @return The created font.
     */
    public static Font createMonospacedFont(int size) {
        return MONOSPACED_FONT_CACHE.deriveFont(Font.PLAIN, size);
    }

    /**
     * Create a monospaced font with the specified size.
     *
     * @param style The style of the font. (Font.PLAIN, Font.BOLD, Font.ITALIC)
     * @param size  The size of the font.
     *
     * @return The created font.
     */
    public static Font createMonospacedFont(int style, int size) {
        return MONOSPACED_FONT_CACHE.deriveFont(style, size);
    }

    /**
     * Creates a new {@link JLabel} that displays the character count of a {@link JTextField}.
     *
     * @param textField        The {@link JTextField} to count the characters of.
     * @param prefix           The prefix of the label.
     * @param suffix           The suffix of the label.
     * @param supportMaxLength Whether to display the maximum length of the document, if it is a
     *                         {@link DocumentLengthLimit} (see
     *                         {@link #limitDocumentLength(JTextField, int)})
     *
     * @return The created {@link JLabel}.
     */
    public static JLabel createDynamicCharacterCountLabel(
        JTextField textField,
        String prefix,
        String suffix,
        boolean supportMaxLength
    ) {
        Document document = textField.getDocument();

        if (document == null) {
            throw new IllegalArgumentException("Component must have a document.");
        }

        JLabel label = new JLabel();
        label.setText(getLabelCountText(document, prefix, suffix, supportMaxLength)); // Default text

        document.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                run();
            }

            private void run() {
                label.setText(getLabelCountText(document, prefix, suffix, supportMaxLength));
            }
        });

        return label;
    }

    /**
     * Returns the text for the character count label.
     *
     * @param document         The document to get the length of.
     * @param prefix           The prefix of the label.
     * @param suffix           The suffix of the label.
     * @param supportMaxLength Whether to display the maximum length of the document, if it is a
     *                         {@link DocumentLengthLimit} (see
     *                         {@link #limitDocumentLength(JTextField, int)})
     *
     * @return The text for the character count label.
     */
    private static String getLabelCountText(Document document, String prefix, String suffix, boolean supportMaxLength) {
        StringBuilder builder = new StringBuilder(prefix);

        builder.append(document.getLength());

        if (supportMaxLength && document instanceof DocumentLengthLimit) {
            builder.append("/");
            builder.append(((DocumentLengthLimit) document).getLimit());
        }

        builder.append(suffix);

        return builder.toString();
    }

    /**
     * Creates a new {@link JLabel} that displays the character count of a {@link JTextField}.
     *
     * @param textField The {@link JTextField} to count the characters of.
     *
     * @return The created {@link JLabel}.
     */
    public static JLabel createDynamicCharacterCountLabel(JTextField textField) {
        return createDynamicCharacterCountLabel(textField, "", "", false);
    }

    /**
     * Creates a new {@link JLabel} that displays the character count of a {@link JTextField}.
     *
     * @param textField        The {@link JTextField} to count the characters of.
     * @param supportMaxLength Whether to display the maximum length of the document, if it is a
     *                         {@link DocumentLengthLimit} (see
     *                         {@link #limitDocumentLength(JTextField, int)})
     *
     * @return The created {@link JLabel}.
     */
    public static JLabel createDynamicCharacterCountLabel(JTextField textField, boolean supportMaxLength) {
        return createDynamicCharacterCountLabel(textField, "", "", supportMaxLength);
    }

    /**
     * Registers a keyboard action to a {@link JComponent}.
     *
     * @param component   The {@link JComponent} to register the action to.
     * @param keyStroke   The {@link KeyStroke} to register the action to. (see {@link KeyStroke#getKeyStroke(int, int)}
     *                    for more information)
     * @param condition   The condition to register the action to. (see
     *                    {@link JRootPane#registerKeyboardAction(ActionListener, KeyStroke, int)} for
     *                    more information)
     * @param onKeyStroke The action to run when the key stroke is pressed.
     */
    public static void onKeyStoke(
        JComponent component,
        KeyStroke keyStroke,
        int condition,
        Consumer<ActionEvent> onKeyStroke
    ) {
        component.registerKeyboardAction(onKeyStroke::accept, keyStroke, condition);
    }

    /**
     * Registers a keyboard action to a {@link JFrame} that is called when the escape key is pressed.
     *
     * @param frame           The {@link JFrame} to register the action to.
     * @param onEscapePressed The action to run when the escape key is pressed.
     */
    public static void onEscapePress(JFrame frame, Consumer<ActionEvent> onEscapePressed) {
        onKeyStoke(frame.getRootPane(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW, onEscapePressed);
    }

    /**
     * Registers a keyboard action to a {@link JFrame} that is called when the enter key is pressed.
     *
     * @param frame          The {@link JFrame} to register the action to.
     * @param onEnterPressed The action to run when the enter key is pressed.
     */
    public static void onEnterPress(JFrame frame, Consumer<ActionEvent> onEnterPressed) {
        onKeyStoke(frame.getRootPane(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW,
            onEnterPressed);
    }
}
