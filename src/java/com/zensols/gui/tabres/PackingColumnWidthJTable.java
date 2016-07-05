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

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * A table GUI component that adjusts each column to show as much data as
 * possible in the narrowest width possible column.
 *
 * @author Paul Landes
 */
public class PackingColumnWidthJTable extends JTable {

    /** Max column with for all columns. */
    private static final int WIDTH_MAX = 500;

    private int margin;

    /** Create with a default margin of <tt>5</tt>. */
    public PackingColumnWidthJTable() {
	this(5);
    }

    /**
     * @param margin additional space on both sides of the column
     */
    public PackingColumnWidthJTable(int margin) {
	// Disable auto resizing
	setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	this.margin = margin;
	setAutoCreateRowSorter(true);
	setColumnSelectionAllowed(true);
    }

    /** @param dataModel has/models the data */
    public void setModel(TableModel dataModel) {
	super.setModel(dataModel);
	packColumns();
    }

    /** Resizes the columns to the data (see class description). */
    public void packColumns() {
	for (int c = 0; c < getColumnCount(); c++) {
	    packColumn(this, c, margin);
	}
    }

    /**
       * Sets the preferred width of the visible column specified by
       * <tt>vColIndex</tt>. The column will be just wide enough to show the
       * column head and the widest cell in the column.  <tt>margin</tt> pixels
       * are added to the left and right (resulting in an additional width of
       * 2*margin pixels).
       *
       * @param table component takes the tabbed data table model
       * @param vColIndex vertical col index (see method)
       * @param margin additional space on both sides of the column
       */
    protected void packColumn(JTable table, int vColIndex, int margin) {
	TableModel model = table.getModel();
	DefaultTableColumnModel colModel = (DefaultTableColumnModel)table.getColumnModel();
	TableColumn col = colModel.getColumn(vColIndex);
	int width = 0;

	// Get width of column header
	TableCellRenderer renderer = col.getHeaderRenderer();
	if (renderer == null) {
	    renderer = table.getTableHeader().getDefaultRenderer();
	}
	Component comp = renderer.getTableCellRendererComponent(
	    table, col.getHeaderValue(), false, false, 0, 0);
	width = comp.getPreferredSize().width;

	// Get maximum width of column data
	for (int r=0; r<table.getRowCount(); r++) {
	    renderer = table.getCellRenderer(r, vColIndex);
	    comp = renderer.getTableCellRendererComponent(
		table, table.getValueAt(r, vColIndex), false, false, r, vColIndex);
	    width = Math.max(width, comp.getPreferredSize().width);
	}

	// Add margin
	width += 2*margin;

	// make sure our columns don't kill usage
	width = Math.min(width, WIDTH_MAX);

	// Set the width
	col.setPreferredWidth(width);
    }
}
