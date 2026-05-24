package enterprises.iwakura.cirno.swing.listeners;

import java.util.function.Consumer;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lombok.Getter;
import lombok.NonNull;

/**
 * A simple {@link DocumentListener} that listens for changes in a {@link JTextField}.
 */
public class TextFieldValueChangedListener implements DocumentListener {

    private final @Getter Consumer<DocumentEvent> onValueChanged;

    /**
     * Creates a new {@link TextFieldValueChangedListener}.
     *
     * @param onValueChanged The {@link Consumer} to call when the value of the {@link JTextField} changes.
     */
    public TextFieldValueChangedListener(@NonNull Consumer<DocumentEvent> onValueChanged) {
        this.onValueChanged = onValueChanged;
    }

    /**
     * Registers a {@link TextFieldValueChangedListener} to a {@link JTextField}.
     *
     * @param textField      The {@link JTextField} to register the listener to.
     * @param onValueChanged The {@link Consumer} to call when the value of the {@link JTextField} changes.
     */
    public static void register(JTextField textField, @NonNull Consumer<DocumentEvent> onValueChanged) {
        textField.getDocument().addDocumentListener(new TextFieldValueChangedListener(onValueChanged));
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        onValueChanged.accept(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onValueChanged.accept(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onValueChanged.accept(e);
    }
}
