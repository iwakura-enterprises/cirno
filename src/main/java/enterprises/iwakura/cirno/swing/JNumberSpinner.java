package enterprises.iwakura.cirno.swing;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

/**
 * A {@link JSpinner} that only accepts numbers.
 */
public class JNumberSpinner extends JSpinner {

    /**
     * Creates new {@link JSpinner} with the specified {@link SpinnerNumberModel}.
     *
     * @param model The spinner number model.
     */
    public JNumberSpinner(SpinnerNumberModel model) {
        super(model);
    }

    /**
     * Creates new {@link JSpinner} with a {@link SpinnerNumberModel} with specified min, max, step and initial value.
     *
     * @param min          The minimum value.
     * @param max          The maximum value.
     * @param step         The step value.
     * @param initialValue The initial value.
     */
    public JNumberSpinner(int min, int max, int step, int initialValue) {
        super(new SpinnerNumberModel(initialValue, min, max, step));
    }

    /**
     * Creates new {@link JSpinner} with a {@link SpinnerNumberModel} with specified min and max values set to <code>0</code>, step of <code>1</code>
     * and initial value of <code>0</code>.
     */
    public JNumberSpinner() {
        this(0, 100, 1, 0);
    }

    /**
     * Returns the value of the spinner as a number.
     *
     * @return The number value.
     */
    public Number getNumber() {
        return (Number) getValue();
    }

    @Override
    public void setModel(SpinnerModel model) {
        if (!(model instanceof SpinnerNumberModel)) {
            throw new IllegalArgumentException("Model must be or extend a SpinnerNumberModel");
        }

        super.setModel(model);
    }

    /**
     * Sets the value of the spinner.<br>
     * If the value is not a number, an {@link IllegalArgumentException} will be thrown.
     *
     * @param value new value for the spinner
     */
    @Override
    public void setValue(Object value) {
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException("Value must be a number");
        }

        super.setValue(value);
    }

    /**
     * Sets the value of the spinner.
     *
     * @param number The number value.
     */
    public void setValue(Number number) {
        super.setValue(number);
    }
}
