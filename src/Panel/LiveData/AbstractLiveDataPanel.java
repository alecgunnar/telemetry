/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import javax.swing.JPanel;

abstract class AbstractLiveDataPanel extends AbstractPanel {
    abstract public void refresh();
}
