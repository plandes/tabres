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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Stores row data in memory as a table model.  This is useful for {@link
 * java.sql.ResultSet} data that doesn't support caching--meaning only a single
 * one time pass is possible.
 *
 * @see CachedTableModel
 * @author Paul Landes
 */
public class CachedRSTableModel extends CachedTableModel {

    @SuppressWarnings("unchecked")
    public CachedRSTableModel(ResultSet rs) throws SQLException {
	ResultSetMetaData meta = rs.getMetaData();
	int cols = meta.getColumnCount();

	columns = new java.util.ArrayList(cols);
	for(int i = 0; i < cols; i++) {
	    columns.add(meta.getColumnLabel(i + 1));
	}

	rows = new java.util.ArrayList();
	while (rs.next()) {
	    List row = new java.util.ArrayList(cols);

	    for(int i = 0; i < cols; i++) {
		row.add(rs.getObject(i + 1));
	    }

	    rows.add(row);
	}

	rows = new java.util.ArrayList(rows);
    }
}
