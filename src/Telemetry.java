/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.gui.frame.MainFrame;
import org.wmich.sunseeker.telemetry.gui.menu.MainMenu;
import org.wmich.sunseeker.telemetry.gui.dialog.ChooseDataSourceDialog;
import org.wmich.sunseeker.telemetry.gui.event.MainFrameInitializer;

import java.awt.EventQueue;

public class Telemetry {
    protected MainFrame mainFrame;
    protected MainMenu mainMenu;

    public static void main (String[] args) {
        Telemetry telemetry = new Telemetry();

        telemetry.start();
    }

    public Telemetry () {
        mainFrame = new MainFrame();
        mainMenu  = new MainMenu();

        configureMenubar();
    }

    public void start () {
        EventQueue.invokeLater(new MainFrameInitializer(mainFrame));
    }

    protected void configureMenubar () {
        mainFrame.setJMenuBar(mainMenu);
    }
}
