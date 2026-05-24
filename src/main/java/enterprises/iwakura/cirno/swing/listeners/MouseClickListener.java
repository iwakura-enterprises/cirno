package enterprises.iwakura.cirno.swing.listeners;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import javax.swing.AbstractButton;

/**
 * A simple {@link MouseListener} that listens for clicks.<br>This listener will only call the {@link Consumer} when the button is enabled.<br>
 * For any other component, other than {@link AbstractButton}, this registers a MouseListener. For {@link AbstractButton}, it
 * registers an ActionListener.
 */
public class MouseClickListener implements MouseListener, ActionListener {

    private final Consumer<MouseEvent> onClick;
    private final boolean useMouseListener;

    /**
     * Creates a new {@link MouseClickListener}.
     *
     * @param onClick          The {@link Consumer} to call when the {@link Component} is clicked.
     * @param useMouseListener Whether to use a {@link MouseListener} or an {@link ActionListener}.
     */
    public MouseClickListener(Consumer<MouseEvent> onClick, boolean useMouseListener) {
        this.onClick = onClick;
        this.useMouseListener = useMouseListener;
    }

    /**
     * Registers a {@link MouseClickListener} to a {@link Component}.
     *
     * @param component The {@link Component} to register the listener to.
     * @param onClick   The {@link Consumer} to call when the {@link Component} is clicked.
     */
    public static void register(Component component, Consumer<MouseEvent> onClick) {
        if (component instanceof AbstractButton) {
            AbstractButton button = (AbstractButton) component;
            button.addActionListener(new MouseClickListener(onClick, false));
            return;
        }

        // Register it as a mouse listener
        component.addMouseListener(new MouseClickListener(onClick, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Click click
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();

        // Simulate a MouseEvent based on ActionEvent and mouse position
        onClick.accept(new MouseEvent((Component) e.getSource(), MouseEvent.MOUSE_CLICKED, e.getWhen(), e.getModifiers(), mousePosition.x, mousePosition.y, 1, false));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!useMouseListener) {
            return;
        }

        // I am using this method because sometimes
        // the mouesClicked is not invoked even
        // upon clicking. This might be just
        // a problem with my computer, but
        // it is annoying.

        // Current mouse position
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();

        // Check if the component is enabled and if the mouse is over the component
        if (e.getSource() instanceof Component) {
            Component component = (Component) e.getSource();
            Point componentLocation = component.getLocationOnScreen();
            Dimension componentSize = component.getSize();

            // Check for enabled
            if (!component.isEnabled()) {
                return;
            }

            // Check for mouse over component
            if (mousePosition.x < componentLocation.x || mousePosition.x > componentLocation.x + componentSize.width || mousePosition.y < componentLocation.y || mousePosition.y > componentLocation.y + componentSize.height) {
                return; // Mouse is not over the component
            }
        }

        // Click click
        onClick.accept(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
