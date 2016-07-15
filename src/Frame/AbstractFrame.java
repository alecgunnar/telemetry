/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;;

import javax.swing.JFrame;

public abstract class AbstractFrame extends JFrame {
    public void showFrame () {
        setVisible(true);
    }

    public void hideFrame () {
        setVisible(false);
    }
}