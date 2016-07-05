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
 * A table model decorator that uses {@link FirstRowRotationTableModel} if the
 * result set has only one row and the delegate otherwise.
 *
 * @author Paul Landes
 */
public class BestGuessPresTableModel implements TableModel {

    private TableModel delegate;

    public BestGuessPresTableModel(TableModel delegate) {
	if (delegate.getRowCount() == 1) {
	    this.delegate = new FirstRowRotationTableModel(delegate);
	} else {
	    this.delegate = delegate;
	}
    }

    public void addTableModelListener(TableModelListener tableModelListener) {
	delegate.addTableModelListener(tableModelListener);
    }

    public Class getColumnClass(int n) {
	return delegate.getColumnClass(n);
    }

    public int getColumnCount() {
	return delegate.getColumnCount();
    }

    public String getColumnName(int n) {
	return delegate.getColumnName(n);
    }

    public boolean isCellEditable(int row, int col) {
	return delegate.isCellEditable(row, col);
    }

    public void removeTableModelListener(TableModelListener tableModelListener) {
	delegate.removeTableModelListener(tableModelListener);
    }

    public void setValueAt(Object object, int row, int col) {
	delegate.setValueAt(object, row, col);
    }

    public int getRowCount() {
	return delegate.getRowCount();
    }

    public Object getValueAt(int row, int col) {
	return delegate.getValueAt(row, col);
    }
}
