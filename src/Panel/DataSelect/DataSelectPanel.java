/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class DataSelectPanel extends AbstractDataSelectPanel {
    public DataSelectPanel () {
        /*
         * Create the titled border for the panel
         */
        TitledBorder border = BorderFactory.createTitledBorder(" Available Data ");

        setBorder(border);
    }
}
