package enterprises.iwakura.cirno.swing;

/**
 * Tab type for tabbed panes.
 */
public enum TabType {
    CARD,
    UNDERLINED;

    public static final String TABBED_PANE_TAB_TYPE_CARD = "card";
    public static final String TABBED_PANE_TAB_TYPE_UNDERLINED = "underline";

    /**
     * Get the client property value for the tab type.
     *
     * @return The client property value.
     */
    public String getClientPropertyValue() {
        if (this == CARD) {
            return TABBED_PANE_TAB_TYPE_CARD;
        }

        return TABBED_PANE_TAB_TYPE_UNDERLINED;
    }
}
