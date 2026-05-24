package enterprises.iwakura.cirno.swing;

import java.awt.Component;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import lombok.Getter;
import lombok.Setter;

/**
 * JTable with some custom stuff
 */
@Getter @Setter
public class JTableCinnamonRoll extends JTable {

    protected int minimumColumnWidth = 15;
    protected boolean autoResizeColumnsOnPaint = false;

    // == JTable Constructors == //

    /**
     * Constructs a default <code>JTable</code> that is initialized with a default
     * data model, a default column model, and a default selection
     * model.
     *
     * @see #createDefaultDataModel
     * @see #createDefaultColumnModel
     * @see #createDefaultSelectionModel
     */
    public JTableCinnamonRoll() {
        super(null, null, null);
    }

    /**
     * Constructs a <code>JTable</code> that is initialized with
     * <code>dm</code> as the data model, a default column model,
     * and a default selection model.
     *
     * @param dm the data model for the table
     *
     * @see #createDefaultColumnModel
     * @see #createDefaultSelectionModel
     */
    public JTableCinnamonRoll(TableModel dm) {
        super(dm, null, null);
    }

    /**
     * Constructs a <code>JTable</code> that is initialized with
     * <code>dm</code> as the data model, <code>cm</code>
     * as the column model, and a default selection model.
     *
     * @param dm the data model for the table
     * @param cm the column model for the table
     *
     * @see #createDefaultSelectionModel
     */
    public JTableCinnamonRoll(TableModel dm, TableColumnModel cm) {
        super(dm, cm, null);
    }

    /**
     * Constructs a <code>JTable</code> that is initialized with
     * <code>dm</code> as the data model, <code>cm</code> as the
     * column model, and <code>sm</code> as the selection model.
     * If any of the parameters are <code>null</code> this method
     * will initialize the table with the corresponding default model.
     * The <code>autoCreateColumnsFromModel</code> flag is set to false
     * if <code>cm</code> is non-null, otherwise it is set to true
     * and the column model is populated with suitable
     * <code>TableColumns</code> for the columns in <code>dm</code>.
     *
     * @param dm the data model for the table
     * @param cm the column model for the table
     * @param sm the row selection model for the table
     *
     * @see #createDefaultDataModel
     * @see #createDefaultColumnModel
     * @see #createDefaultSelectionModel
     */
    public JTableCinnamonRoll(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    /**
     * Constructs a <code>JTable</code> with <code>numRows</code>
     * and <code>numColumns</code> of empty cells using
     * <code>DefaultTableModel</code>.  The columns will have
     * names of the form "A", "B", "C", etc.
     *
     * @param numRows    the number of rows the table holds
     * @param numColumns the number of columns the table holds
     *
     * @see DefaultTableModel
     */
    public JTableCinnamonRoll(int numRows, int numColumns) {
        super(new DefaultTableModel(numRows, numColumns));
    }

    /**
     * Constructs a <code>JTable</code> to display the values in the
     * <code>Vector</code> of <code>Vectors</code>, <code>rowData</code>,
     * with column names, <code>columnNames</code>.  The
     * <code>Vectors</code> contained in <code>rowData</code>
     * should contain the values for that row. In other words,
     * the value of the cell at row 1, column 5 can be obtained
     * with the following code:
     *
     * <pre>((Vector)rowData.elementAt(1)).elementAt(5);</pre>
     *
     * @param rowData     the data for the new table
     * @param columnNames names of each column
     */
    @SuppressWarnings("rawtypes")
    public JTableCinnamonRoll(Vector<? extends Vector> rowData, Vector<?> columnNames) {
        super(new DefaultTableModel(rowData, columnNames));
    }

    /**
     * Constructs a <code>JTable</code> to display the values in the two dimensional array,
     * <code>rowData</code>, with column names, <code>columnNames</code>.
     * <code>rowData</code> is an array of rows, so the value of the cell at row 1,
     * column 5 can be obtained with the following code:
     *
     * <pre> rowData[1][5]; </pre>
     * <p>
     * All rows must be of the same length as <code>columnNames</code>.
     *
     * @param rowData     the data for the new table
     * @param columnNames names of each column
     */
    public JTableCinnamonRoll(final Object[][] rowData, final Object[] columnNames) {
        super(rowData, columnNames);
    }

    /**
     * Calls {@link #setDefaultRenderer(Class, TableCellRenderer)} with the {@link Object} class and the given renderer.
     *
     * @param renderer the renderer to use for this table
     */
    public void setTableCellRenderer(TableCellRenderer renderer) {
        this.setDefaultRenderer(Object.class, renderer);
    }

    /**
     * Resizes the column widths to fit the content.<br>
     * <b>The table must have {@link JTable#AUTO_RESIZE_OFF} as auto resize mode to work correctly.</b><br>
     * If the {@link TableCellRenderer} returns incorrect values for {@link Component#getPreferredSize()}, the column width might not be correct.
     */
    public void resizeColumnWidthsToFitContent() {
        final TableColumnModel columnModel = getColumnModel();

        for (int column = 0; column < getColumnCount(); column++) {
            int width = minimumColumnWidth;

            for (int row = 0; row < getRowCount(); row++) {
                TableCellRenderer renderer = getCellRenderer(row, column);
                Component comp = prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }

            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    @Override
    public void paint(Graphics g) {
        if (autoResizeColumnsOnPaint) {
            resizeColumnWidthsToFitContent();
        }

        super.paint(g);
    }
}
