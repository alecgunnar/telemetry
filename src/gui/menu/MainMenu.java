/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.gui.menu;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

public class MainMenu extends JMenuBar {
    public MainMenu () {
        JMenu menu = new JMenu("Options");

        add(menu);
    }
}
