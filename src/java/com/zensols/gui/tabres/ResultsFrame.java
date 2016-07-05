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

import java.awt.Dimension;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

import com.zensols.gui.pref.ConfigPrefFrame;
import com.zensols.gui.pref.PrefSupport;

/**
 * Create a frame to display results using data as a list of lists, optionally
 * with columns.
 *
 * @see #displayResults(List)
 * @see #displayResults(List,List)
 * @author Paul Landes
 */
public class ResultsFrame extends ConfigPrefFrame {
    /** Extra height space for munged scroll bars (dependent on OS etc). */
    public final static int HEIGHT_FUDGE = 25;
    /** Extra width space for munged scroll bars (dependent on OS etc). */
    public final static int WIDTH_FUDGE = 5;

    private int widthFudge = HEIGHT_FUDGE;
    private int heightFudge = WIDTH_FUDGE;

    /**
     * @param packageOwner class name is used for the preferences Java
     * preferences namespace
     * @param prefix a string used as part of the Java preferences namespace to
     * identify this frame
     */
    public ResultsFrame(Class packageOwner, String prefix) {
	super(packageOwner, prefix);
    }

    /**
     * @param prefix a string used as part of the Java preferences namespace to
     * identify this frame
     */
    public ResultsFrame(String prefix) {
	this(ResultsFrame.class, prefix);
    }

    /**
     * @param prefSupport used for this frame instance
     * @param prefix a string used as part of the Java preferences namespace to
     * identify this component
     */
    public ResultsFrame(PrefSupport prefSupport, String prefix) {
	super(prefSupport, prefix);
    }

    /**
     * Render <tt>data</tt> as tabulated results and update frame size.
     *
     * @param data the data to render
     */
    public void displayResults(List<List> data) {
	displayResults(data, null);
    }

    /** @return extra width space for munged scroll bars (dependent on OS
     * etc) */
    public int getWidthFudge() {
	return widthFudge;
    }

    /** @param widthFudge extra width space for munged scroll bars (dependent on OS etc). */
    public void setWidthFudge(int widthFudge) {
	this.widthFudge = widthFudge;
    }

    /** @return extra height space for munged scroll bars (dependent on OS
     * etc) */
    public int getHeightFudge() {
	return heightFudge;
    }

    /** @param heightFudge extra height space for munged scroll bars (dependent on OS etc). */
    public void setHeightFudge(int heightFudge) {
	this.heightFudge = heightFudge;
    }

    /**
     * Render <tt>data</tt> as tabulated results and update frame size.
     *
     * @param data the data to render
     * @param columns names of the columns and has to match size of <tt>data</tt>
     */
    public void displayResults(List<List> data, List<String> columns) {
	TableModel model = new BestGuessPresTableModel
	    (new CachedTableModel(data, columns));
	final PackingColumnWidthJTable table = new PackingColumnWidthJTable();
	JScrollPane scrollPane = new JScrollPane(table) {
		public Dimension getPreferredSize() {
		    Dimension dim = table.getPreferredSize();
		    dim.width += widthFudge;
		    dim.height += heightFudge;
		    return dim;
		}};
	table.setModel(model);
	setContentPane(scrollPane);
    }
}
