/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

class DataSelectPanel extends AbstractDataSelectPanel {
    public DataSelectPanel () {
        TitledBorder border = BorderFactory.createTitledBorder("Available Data");

        setBorder(border);
    }
}
