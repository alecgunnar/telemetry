/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import javax.swing.JFrame;

abstract class AbstractView extends JFrame {
    public void showView () {
        setVisible(true);
    }

    public void hideView () {
        setVisible(false);
    }
}