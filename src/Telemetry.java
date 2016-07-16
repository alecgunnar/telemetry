/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.gui.component.frame.MainFrame;
import org.wmich.sunseeker.telemetry.gui.component.menu.MainMenu;
import org.wmich.sunseeker.telemetry.gui.component.dialog.ChooseDataSource;
import org.wmich.sunseeker.telemetry.gui.component.dialog.SelectableDataSource;
import org.wmich.sunseeker.telemetry.gui.event.MainFrameInitializer;
import org.wmich.sunseeker.telemetry.data.source.DataSource;
import org.wmich.sunseeker.telemetry.data.source.PseudoRandom;

import java.awt.EventQueue;
import java.util.ArrayList;

public class Telemetry {
    protected MainFrame mainFrame;
    protected MainMenu mainMenu;

    protected ChooseDataSource dataSourceDialog;

    public static void main (String[] args) {
        Telemetry telemetry = new Telemetry();

        telemetry.start();
    }

    public Telemetry () {
        mainFrame = new MainFrame();
        mainMenu  = new MainMenu();

        dataSourceDialog = new SelectableDataSource();

        configureMenubar();
    }

    public void start () {
        ArrayList<DataSource> sources = new ArrayList<DataSource>();

        sources.add(new PseudoRandom());

        dataSourceDialog.prompt(sources);

        // EventQueue.invokeLater(new MainFrameInitializer(mainFrame));
    }

    protected void configureMenubar () {
        mainFrame.setJMenuBar(mainMenu);
    }
}
