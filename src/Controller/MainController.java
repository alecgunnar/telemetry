/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 2, 2016
*/

package org.wmich.sunseeker.telemetry.controller;

import org.wmich.sunseeker.telemetry.dispatcher.Dispatcher;
import org.wmich.sunseeker.telemetry.*;

import javax.swing.JFrame;
import java.lang.Runnable;
import java.lang.Thread;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class MainController extends AbstractController implements Runnable, ActionListener {
    /*
     * Define events triggered by this controller
     */
    final public static int USER_CLOSE_APP_EVENT       = 0xB00;
    final public static int USER_NEW_DATA_SOURCE_EVENT = 0xB01;

    final public static int LINE_REFRESH_INTERVAL = 250;

    protected AbstractMainFrame mainFrame;

    protected AbstractGraphPanel graphPanel;

    protected AbstractLiveDataPanel liveDataPanel;

    protected boolean paused = false;

    protected Timer lineUpdater;

    public MainController (Dispatcher dispatcher) {
        super(dispatcher);
    }

    // public void useGraphPanel (AbstractGraphPanel panel) {
    //     mainFrame.useGraphPanel(graphPanel = panel);
    // }

    // public void useDataSelectPanel (AbstractDataSelectPanel panel) {
    //     mainFrame.useDataSelectPanel(panel);
    // }

    // public void useLiveDataPanel (AbstractLiveDataPanel panel) {
    //     mainFrame.useLiveDataPanel(liveDataPanel = panel);
    // }

    // public void useLinePanels(AbstractLinePanel[] panels) {
    //     mainFrame.useLinePanels(panels);

    //     createLineUpdater();
    // }

    public void start () {
        EventQueue.invokeLater(this);
    }

    public void run () {
        mainFrame.showFrame();

        // lineUpdater.start();
    }

    public void actionPerformed (ActionEvent evt) {
        graphPanel.repaint();
        liveDataPanel.refresh();
    }

    public void registerEventTypes (Dispatcher dispatcher) throws Exception {
        /*
         * Triggered when the user closed the main frame
         */
        dispatcher.register(USER_CLOSE_APP_EVENT);

        /*
         * Triggered when the user opts for a new data source
         */
        dispatcher.register(USER_NEW_DATA_SOURCE_EVENT);
    }

    public void registerEventListeners (Dispatcher dispatcher) throws Exception {
        track(Telemetry.APP_START_EVENT);
        track(Telemetry.MAIN_FRAME_CREATED_EVENT);
    }

    public void dispatch (int eventType, Object data) {
        switch (eventType) {
            case Telemetry.APP_START_EVENT: start(); break;
            case Telemetry.MAIN_FRAME_CREATED_EVENT: mainFrame = (AbstractMainFrame) data; break;
        }
    }

    protected void createLineUpdater () {
        lineUpdater = new Timer(LINE_REFRESH_INTERVAL, this);
    }
}