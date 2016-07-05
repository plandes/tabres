/*
This file is part of the Zenos Solutions GUI Table Results Library (ZGTR).

ZGTR is free software: you can redistribute it and/or modify it under the
terms of the GNU Lesser General Public License as published by the Free
Software Foundation, either version 3 of the License, or (at your option) any
later version.

ZGTR is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with
ZGTR.  If not, see <http://www.gnu.org/licenses/>.
*/

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
