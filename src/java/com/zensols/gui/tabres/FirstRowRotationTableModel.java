package com.zensols.gui.tabres;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;

/**
 * A table model decorator (pattern) that gives the column names in one column
 * and the data in another column for cases where there is only one row.  The
 * data column comes from only the first row of the delegate.  This is useful
 * in cases where there are many columns and long column names.
 *
 * @author Paul Landes
 */
public class FirstRowRotationTableModel implements TableModel {

    private static final String[] COLS = { "Column", "Value" };
    private TableModel delegate;

    /**
     * @param delegate where we get our data from
     */
    public FirstRowRotationTableModel(TableModel delegate) {
	this.delegate = delegate;
    }

    public void addTableModelListener(TableModelListener tableModelListener) {
	delegate.addTableModelListener(tableModelListener);
    }

    public Class getColumnClass(int n) {
	return delegate.getColumnClass(n);
    }

    public int getColumnCount() {
	return COLS.length;
    }

    public String getColumnName(int n) {
	return COLS[n];
    }

    public boolean isCellEditable(int row, int col) {
	return delegate.isCellEditable(col, row);
    }

    public void removeTableModelListener(TableModelListener tableModelListener) {
	delegate.removeTableModelListener(tableModelListener);
    }

    public void setValueAt(Object object, int row, int col) {
	delegate.setValueAt(object, col, row);
    }

    public int getRowCount() {
	return delegate.getColumnCount();
    }

    public Object getValueAt(int row, int col) {
	return (col == 0) ?
	    delegate.getColumnName(row) :
	    delegate.getValueAt(0, row);
    }
}
