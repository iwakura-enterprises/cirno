package enterprises.iwakura.cirno.swing;

import java.awt.Color;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * Utility class for registering listeners and other stuff specially for FlatLaf.
 */
public final class CinnamonRollFlatLaf {

    public static final String CLIENT_PROPERTY_PLACEHOLDER_TEXT = "JTextField.placeholderText";
    public static final String CLIENT_PROPERTY_TABBED_PANE_TAB_TYPE = "JTabbedPane.tabType";
    public static final String CLIENT_PROPERTY_TABBED_PANE_SHOW_TAB_SEPARATORS = "JTabbedPane.showTabSeparators";
    public static final String CLIENT_PROPERTY_TABBED_PANE_HAS_FULL_BORDER = "JTabbedPane.hasFullBorder";
    public static final String CLIENT_PROPERTY_STYLE = "FlatLaf.style";
    public static final String CLIENT_PROPERTY_OUTLINE = "JComponent.outline";
    public static final String CLIENT_PROPERTY_VALUE_OUTLINE_ERROR = "error";
    public static final String CLIENT_PROPERTY_VALUE_OUTLINE_WARNING = "warning";

    private CinnamonRollFlatLaf() {
    }

    /**
     * Set a placeholder for a text field or other supported component. (JTextField.placeholderText)<br>
     * <b>Works only with FlatLaf</b>
     *
     * @param component   The text field.
     * @param placeholder The placeholder.
     */
    public static void setTextPlaceholder(JComponent component, String placeholder) {
        component.putClientProperty(CLIENT_PROPERTY_PLACEHOLDER_TEXT, placeholder);
    }

    /**
     * Set the tab type of tabbed pane. (JTabbedPane.tabType)
     *
     * @param tabbedPane The tabbed pane.
     * @param tabType    The tab type.
     */
    public static void setTabType(JTabbedPane tabbedPane, TabType tabType) {
        tabbedPane.putClientProperty(CLIENT_PROPERTY_TABBED_PANE_TAB_TYPE, tabType.getClientPropertyValue());
    }

    /**
     * Show or hide the tab separator of a tabbed pane. (JTabbedPane.showTabSeparator)
     *
     * @param tabbedPane The tabbed pane.
     * @param show       Whether to show the tab separator.
     */
    public static void showTabSeparator(JTabbedPane tabbedPane, boolean show) {
        tabbedPane.putClientProperty(CLIENT_PROPERTY_TABBED_PANE_SHOW_TAB_SEPARATORS, show);
    }

    /**
     * Show or hide the border around the tabbed pane. (JTabbedPane.hasFullBorder)
     *
     * @param tabbedPane The tabbed pane.
     * @param show       Whether to show the border.
     */
    public static void showBorder(JTabbedPane tabbedPane, boolean show) {
        tabbedPane.putClientProperty(CLIENT_PROPERTY_TABBED_PANE_HAS_FULL_BORDER, show);
    }

    /**
     * Show or hide the password reveal button of a password field. (showRevealButton)
     *
     * @param passwordField The password field.
     * @param show          Whether to show the password reveal button.
     */
    public static void showPasswordRevealButton(JPasswordField passwordField, boolean show) {
        passwordField.putClientProperty(CLIENT_PROPERTY_STYLE, "showRevealButton: " + show);
    }

    /**
     * Set the outline property of a component.
     *
     * @param component The component. (Supported components: JButton, JComboBox, JFormattedTextField, JPasswordField, JScrollPane, JSpinner, JTextField and JToggleButton)
     * @param object    The object.
     */
    private static void setOutlineProperty(JComponent component, Object object) {
        component.putClientProperty(CLIENT_PROPERTY_OUTLINE, object);
    }

    /**
     * Set the outline property of a component.
     *
     * @param component The component. (Supported components: JButton, JComboBox, JFormattedTextField, JPasswordField, JScrollPane, JSpinner, JTextField and JToggleButton)
     * @param color     The color.
     */
    public static void setOutlinePropertyColor(JComponent component, Color color) {
        setOutlineProperty(component, color);
    }

    /**
     * Set the outline property of a component.
     *
     * @param component The component. (Supported components: JButton, JComboBox, JFormattedTextField, JPasswordField, JScrollPane, JSpinner, JTextField and JToggleButton)
     * @param colors    The array of exactly two colors (first color is for focused state and the second for unfocused state).
     */
    public static void setOutlinePropertyColors(JComponent component, Color[] colors) {
        if (colors.length != 2) {
            throw new IllegalArgumentException("Color array must have a length of 2.");
        }

        setOutlineProperty(component, colors);
    }

    /**
     * Set the outline property of a component with an error outline (usually reddish).
     *
     * @param component The component. (Supported components: JButton, JComboBox, JFormattedTextField, JPasswordField, JScrollPane, JSpinner, JTextField and JToggleButton)
     * @param visible   Whether the outline should be visible.
     */
    public static void setOutlinePropertyError(JComponent component, boolean visible) {
        setOutlineProperty(component, visible ? CLIENT_PROPERTY_VALUE_OUTLINE_ERROR : null);
    }

    /**
     * Set the outline property of a component with a warning outline (usually yellowish).
     *
     * @param component The component. (Supported components: JButton, JComboBox, JFormattedTextField, JPasswordField, JScrollPane, JSpinner, JTextField and JToggleButton)
     * @param visible   Whether the outline should be visible.
     */
    public static void setOutlinePropertyWarning(JComponent component, boolean visible) {
        setOutlineProperty(component, visible ? CLIENT_PROPERTY_VALUE_OUTLINE_WARNING : null);
    }

    /**
     * Set the outline property of JTextField depending on the value of the supplier, which is called every time the text changes.
     *
     * @param textField The JTextField.
     * @param supplier  The supplier to get the value from.
     */
    private static void addDynamicOutline(JTextField textField, Supplier<Object> supplier) {
        Document document = textField.getDocument();

        if (document == null) {
            throw new IllegalArgumentException("Component must have a document.");
        }

        document.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                runCheck();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                runCheck();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                runCheck();
            }

            private void runCheck() {
                Object value = supplier.get();

                if (value instanceof Color) {
                    setOutlinePropertyColor(textField, (Color) value);
                } else if (value instanceof Color[]) {
                    setOutlinePropertyColors(textField, (Color[]) value);
                } else {
                    setOutlineProperty(textField, value);
                }
            }
        });
    }

    /**
     * Set the outline property of JTextField depending on the value of the supplier, which is called every time the text changes.
     *
     * @param textField The JTextField.
     * @param supplier  The supplier to get the color from, null if the outline should be hidden.
     */
    public static void addDynamicOutlineColor(JTextField textField, Supplier<Color> supplier) {
        addDynamicOutline(textField, supplier::get);
    }

    /**
     * Set the outline property of JTextField depending on the value of the supplier, which is called every time the text changes.
     *
     * @param textField The JTextField.
     * @param supplier  The supplier to get array of exactly two colors from, null if the outline should be hidden. (first color is for focused state
     *                  and the second for unfocused state).
     */
    public static void addDynamicOutlineColors(JTextField textField, Supplier<Color[]> supplier) {
        addDynamicOutline(textField, supplier::get);
    }

    /**
     * Set the outline property of JTextField depending on the value of the supplier, which is called every time the text changes.
     *
     * @param textField The JTextField.
     * @param supplier  The supplier to get whenever the outline should be visible, null or false if the outline should be hidden.
     */
    public static void addDynamicOutlineError(JTextField textField, Supplier<Boolean> supplier) {
        addDynamicOutline(textField, () -> supplier.get() ? CLIENT_PROPERTY_VALUE_OUTLINE_ERROR : null);
    }

    /**
     * Set the outline property of JTextField depending on the value of the supplier, which is called every time the text changes.
     *
     * @param textField The JTextField.
     * @param supplier  The supplier to get whenever the outline should be visible, null or false if the outline should be hidden.
     */
    public static void addDynamicOutlineWarning(JTextField textField, Supplier<Boolean> supplier) {
        addDynamicOutline(textField, () -> supplier.get() ? CLIENT_PROPERTY_VALUE_OUTLINE_WARNING : null);
    }
}
