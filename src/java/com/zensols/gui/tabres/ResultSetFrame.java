package com.zensols.gui.tabres;

import javax.swing.JFrame;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A frame that contains a {@link ResultSetPanel}.
 *
 * @author Paul Landes
 */
public class ResultSetFrame extends com.zensols.gui.pref.ConfigPrefFrame {
    private ResultSetPanel panel;

    public ResultSetFrame() {
	this(true);
    }

    /** @param hasQueryBox <tt>true</tt> to display GUI controls to manually
	enter SQL statements */
    public ResultSetFrame(boolean hasQueryBox) {
	super("resultsframe");
	setTitle("SQL Results");
	init(hasQueryBox);
    }

    private void init(boolean hasQueryBox) {
	panel = new ResultSetPanel(hasQueryBox);
	setContentPane(panel);
    }

    public ResultSetPanel getResultSetPanel() {
	return panel;
    }

    public void dispose() {
	super.dispose();
	panel.dispose();
    }
}
