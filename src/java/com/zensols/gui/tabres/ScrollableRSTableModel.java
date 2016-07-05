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

import javax.swing.table.AbstractTableModel;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * A table model that models a {@link java.sql.ResultSet} of data.
 *
 * @author Paul Landes
 */
public class ScrollableRSTableModel
    extends javax.swing.table.AbstractTableModel {

    private ResultSet rs;
    private String[] columns;
    private int rowCount;

    /**
     * @param rs the result set to model
     * @exception SQLException by the DB layer during the query
     */
    public ScrollableRSTableModel(ResultSet rs) throws SQLException {
	ResultSetMetaData meta = rs.getMetaData();
	int cols = meta.getColumnCount();

	columns = new String[cols];
	for(int i = 0; i < cols; i++) {
	    columns[i] = meta.getColumnLabel(i + 1);
	}

	rs.last();
	rowCount = rs.getRow();

	this.rs = rs;
    }

    public String getColumnName(int column) {
	return columns[column];
    }

    public int getRowCount() {
	return rowCount;
    }

    public int getColumnCount() {
	return columns.length;
    }

    public Object getValueAt(int row, int column) {
	Object ret = null;

	try {
	    rs.absolute(row + 1);
	    ret = rs.getObject(column + 1);
	} catch(SQLException e) {
	    e.printStackTrace();
	    ret = e;
	}

	return ret;
    }
}
