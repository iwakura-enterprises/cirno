package enterprises.iwakura.cirno.swing;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Clipboard utilities
 */
public final class ClipboardUtils {

    /**
     * Sets the clipboard content
     *
     * @param text The text to set
     */
    public static void setContent(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }
}
