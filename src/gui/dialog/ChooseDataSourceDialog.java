/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.gui.dialog;

import javax.swing.JDialog;

public class ChooseDataSourceDialog extends JDialog {
    /*
     * Define the dimensions of the window
     */
    final protected int DIALOG_WIDTH  = 1000;
    final protected int DIALOG_HEIGHT = 800;

    public ChooseDataSourceDialog () {
        /*
         * Set the title of the frame
         */
        super("Choose a data source...");

        /*
         * Set the size of the dialog
         */
        setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
    }
}
