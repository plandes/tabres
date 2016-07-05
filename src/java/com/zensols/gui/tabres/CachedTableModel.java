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

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Stores row data in memory as a table model.
 *
 * @see CachedRSTableModel
 * @author Paul Landes
 */
public class CachedTableModel extends javax.swing.table.AbstractTableModel {

    protected List<String> columns;
    protected List<List> rows;

    protected CachedTableModel() {}

    /** @param rows the data to model/return */
    public CachedTableModel(List<List> rows) {
	this(rows, null);
    }

    /**
     * @param rows the data to model/return
     * @param columns the names of the columns to use for would be delegates
     */
    public CachedTableModel(List<List> rows, List<String> columns) {
	this.rows = rows;
	this.columns = columns;
    }

    public String getColumnName(int column) {
	return (columns != null) ? columns.get(column) : null;
    }

    public int getRowCount() {
	return rows.size();
    }

    public int getColumnCount() {
	if (columns != null) return columns.size();
	else if (rows.size() > 0) return rows.get(0).size();
	else return 0;
    }

    public Object getValueAt(int row, int column) {
	List rowRows = rows.get(row);
	return rowRows.get(column);
    }
}
