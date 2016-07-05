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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A GUI panel that displays data from an {@link java.sql.ResultSet} instance.
 *
 * @author Paul Landes
 */
public class ResultSetPanel extends JPanel {

    private final static int QUERY_PANEL_HEIGHT = 140;
    private final static int SCREEN_HEIGHT_FUDGE = 100;
    private final static int SCREEN_WIDTH_FUDGE = 20;

    private Connection connection;
    private Statement stmt;
    private ResultSet resultSet;
    private boolean hasQueryBox;

    private Action sendAction;
    private PackingColumnWidthJTable table;
    private TableModel model;
    private JTextArea queryBox;
    private JComponent mainPane;
    private JScrollPane tablePane;

    /** @param hasQueryBox <tt>true</tt> to display GUI controls to manually
    enter SQL statements */
    public ResultSetPanel(boolean hasQueryBox) {
	sendAction = new SendAction();
	this.hasQueryBox = hasQueryBox;
	init();
    }

    private void init() {
	JPanel root = this;

	root.setLayout(new BorderLayout());
	table = new PackingColumnWidthJTable();
	queryBox = new JTextArea();
	tablePane = new JScrollPane(table);

	if (hasQueryBox) {
	    mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				      tablePane, getTopPane());
	} else {
	    mainPane = tablePane;
	}

	root.add(mainPane);
    }

    private JComponent getTopPane() {
	JPanel root = new JPanel();
	GridBagConstraints c = new GridBagConstraints(
	    0, 0, 1, 1, 0.0, 1.0, GridBagConstraints.NORTH,
	    GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0);

	root.setLayout(new GridBagLayout());

	c.weightx = 0;
	c.weighty = 1;
	root.add(new JButton(sendAction), c);

	c.anchor = GridBagConstraints.CENTER;
	c.fill = GridBagConstraints.BOTH;
	c.gridx++;
	c.weightx = 1;
	root.add(new JScrollPane(queryBox), c);

	return root;
    }

    public Dimension getPreferredSize() {
	Dimension screenSize = java.awt.Toolkit.
	    getDefaultToolkit().getScreenSize();
	int width = table.getPreferredSize().width + 4;
	int height;

	if (hasQueryBox) {
	    height = table.getPreferredSize().height + QUERY_PANEL_HEIGHT;
	} else {
	    height = table.getPreferredSize().height + 20;
	}

	// use 100 for windows task bar, OSX menu bar, etc
	height = Math.min(screenSize.height - SCREEN_HEIGHT_FUDGE, height);
	width = Math.min(screenSize.width - SCREEN_WIDTH_FUDGE, width);

	return new Dimension(width, height);
    }

    /** @return the DB connection used get results from a query */
    public Connection getConnection() {
	return connection;
    }

    /** @param connection the DB connection used get results from a query */
    public void setConnection(Connection connection) {
	this.connection = connection;
    }

    /** Execute the query in the query box (GUI) and show the results. */
    protected final void executeQuery() {
	try {
	    executeQuery(queryBox.getText());
	} catch(SQLException e) {
	    e.printStackTrace();
	    clearTable();
	    JOptionPane.showMessageDialog(
		null, e.getMessage(),
		"Database Error",
		JOptionPane.ERROR_MESSAGE);
	}
    }

    /** Clear out all table data. */
    protected void clearTable() {
	table.setModel(new javax.swing.table.AbstractTableModel() {
	    public int getRowCount() { return 0; }
	    public int getColumnCount() { return 0; }
	    public Object getValueAt(int row, int column) { return ""; }
	});
    }

    /**
     * Execute an SQL query and show the results in the GUI table in the panel.
     *
     * @param query the SQL query to execute on {@link #getConnection}
     * @exception SQLException by the DB layer during the query
     */
    public void executeQuery(String query) throws SQLException {
	executeQuery(query, false);
    }

    /**
     * Execute an SQL query and show the results in the GUI table in the panel.
     *
     * @param query the SQL query to execute on {@link #getConnection}
     * @param bestLayout if <tt>true</tt> then re-adjust size and other GUI
     * components to try to give the best visual layout for the results
     * returned from the DB
     * @exception SQLException by the DB layer during the query
     */
    public void executeQuery(String query, boolean bestLayout)
	throws SQLException {

	dispose();
	stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					  ResultSet.CONCUR_READ_ONLY);
	resultSet = stmt.executeQuery(query);
	displayResults(
	    query, new ScrollableRSTableModel(resultSet), bestLayout);
    }

    /**
     * Show the results in the GUI table in the panel.
     *
     * @param rs the results to display in the panel
     * @exception SQLException by the DB layer during the query
     * @return the row count (number of rows generated from the query)
     */
    public int displayResults(ResultSet rs) throws SQLException {
	return displayResults("", rs);
    }

    /**
     * Show the results in the GUI table in the panel.
     *
     * @param rs the results to display in the panel
     * @param bestLayout if <tt>true</tt> then re-adjust size and other GUI
     * components to try to give the best visual layout for the results
     * returned from the DB
     * @exception SQLException by the DB layer during the query
     * @return the row count (number of rows generated from the query)
     */
    public int displayResults(ResultSet rs, boolean bestLayout) throws SQLException {
	return displayResults("", rs, bestLayout);
    }

    /**
     * Show the results in the GUI table in the panel.
     *
     * @param query the SQL query to execute on {@link #getConnection}
     * @param rs the results to display in the panel
     * @exception SQLException by the DB layer during the query
     * @return the row count (number of rows generated from the query)
     */
    public int displayResults(String query, ResultSet rs) throws SQLException {
	return displayResults(query, rs, false);
    }

    /**
     * Show the results in the GUI table in the panel.
     *
     * @param query the SQL query to execute on {@link #getConnection}
     * @param rs the results to display in the panel
     * @param bestLayout if <tt>true</tt> then re-adjust size and other GUI
     * components to try to give the best visual layout for the results
     * returned from the DB
     * @exception SQLException by the DB layer during the query
     * @return the row count (number of rows generated from the query)
     */
    public int displayResults(String query, ResultSet rs, boolean bestLayout)
	throws SQLException {
	CachedRSTableModel model = new CachedRSTableModel(rs);
	displayResults(query, model, bestLayout);
	return model.getRowCount();
    }

    /**
     * Show the results in the GUI table in the panel.
     *
     * @param query the SQL query to execute on {@link #getConnection}
     * @param model the table model used to model the tabbed data
     * @param bestLayout if <tt>true</tt> then re-adjust size and other GUI
     * components to try to give the best visual layout for the results
     * returned from the DB
     * @exception SQLException by the DB layer during the query
     */
    protected void displayResults(String query, TableModel model,
				  boolean bestLayout)
	throws SQLException {

	queryBox.setText(query);
	if (bestLayout) model = new BestGuessPresTableModel(model);
	this.model = model;
	try {
	    javax.swing.SwingUtilities.invokeAndWait(
		new Runnable() {
		    public void run() {
			table.setModel(ResultSetPanel.this.model);
			if (mainPane instanceof JSplitPane) {
			    JSplitPane sp = (JSplitPane)mainPane;
			    Dimension screenSize = java.awt.Toolkit.
				getDefaultToolkit().getScreenSize();
			    int height = table.getPreferredSize().height + 20;
			    height = Math.min(
				screenSize.height -
				QUERY_PANEL_HEIGHT - SCREEN_HEIGHT_FUDGE,
				height);
			    sp.setDividerLocation(height);
			}
		    }});
	} catch(Exception e) {
	    e.printStackTrace();
	}
    }

    /** Clean up and de-allocate DB and GUI resources. */
    public final void dispose() {
	try { if (resultSet != null) resultSet.close(); }
	catch(SQLException e) { e.printStackTrace(); }
	resultSet = null;

	try { if (stmt != null) stmt.close(); }
	catch(SQLException e) { e.printStackTrace(); }
	stmt = null;
    }

    /** Action invokes {@link #executeQuery}. */
    protected class SendAction extends AbstractAction {
	public SendAction() {
	    super("Send");
	}

	public void actionPerformed(ActionEvent e) {
	    executeQuery();
	}
    }
}
