/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.gui.frame;

import javax.swing.JFrame;
import java.awt.Dimension;

public class MainFrame extends JFrame {
    /*
     * Define the dimensions of the FRAME
     */
    final protected int FRAME_WIDTH  = 1000;
    final protected int FRAME_HEIGHT = 800;

    public MainFrame () {
        /*
         * Set the title of the frame
         */
        super("Sunseeker Telemetry");

        /*
         * Set the size of the frame
         */
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
    }
}
