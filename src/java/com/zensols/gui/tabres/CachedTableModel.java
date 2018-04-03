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
