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
