/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.gui.component.dialog;

import org.wmich.sunseeker.telemetry.data.source.DataSource;

import java.util.List;
import javax.swing.JOptionPane;
import java.awt.Dimension;

public class SelectableDataSource extends JOptionPane implements ChooseDataSource {
    /*
     * Define the dimensions of the window
     */
    final protected int DIALOG_WIDTH  = 1000;
    final protected int DIALOG_HEIGHT = 800;

    /*
     * All of our data sources
     */
    protected List<DataSource> sources;

    public SelectableDataSource () {
        /*
         * Set the title of the frame
         */
        createDialog("Choose a Data Source");

        setMessage("Choose a data source:");

        /*
         * Set the size of the dialog
         */
        setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
    }

    public DataSource prompt (List<DataSource> sources) {
        setOptions(sources.toArray());
        setVisible(true);   

        return sources.get(0);
    }
}
