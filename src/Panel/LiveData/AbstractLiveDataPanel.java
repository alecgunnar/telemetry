/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;;

import javax.swing.JPanel;

public abstract class AbstractLiveDataPanel extends AbstractPanel {
    abstract public void refresh();
}
