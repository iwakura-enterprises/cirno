package enterprises.iwakura.cirno.swing;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import lombok.Getter;

/**
 * A {@link PlainDocument} that limits the length of the document
 */
@Getter
public class DocumentLengthLimit extends PlainDocument {

    private final int limit;

    /**
     * Creates a new {@link DocumentLengthLimit} with the given limit
     *
     * @param limit The limit to use
     */
    public DocumentLengthLimit(int limit) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
