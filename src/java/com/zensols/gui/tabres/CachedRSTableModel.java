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
