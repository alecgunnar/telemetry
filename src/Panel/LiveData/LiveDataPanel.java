/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

class LiveDataPanel extends AbstractLiveDataPanel {
    public LiveDataPanel () {
        TitledBorder border = BorderFactory.createTitledBorder(" Live Data ");

        setBorder(border);
    }
}
