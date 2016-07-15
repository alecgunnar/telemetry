/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 2, 2016
*/

package org.wmich.sunseeker.telemetry.controller;

import org.wmich.sunseeker.telemetry.dispatcher.Dispatcher;
import org.wmich.sunseeker.telemetry.data.DataSetInterface;
import org.wmich.sunseeker.telemetry.*;

import javax.swing.JFrame;
import java.lang.Runnable;
import java.lang.Thread;
import java.awt.EventQueue;
import javax.swing.Timer;

public class MainController extends AbstractController implements Runnable {
    /*
     * Define events triggered by this controller
     */
    final public static int USER_CLOSE_APP_EVENT       = 100;
    final public static int USER_NEW_DATA_SOURCE_EVENT = 101;

    final public static int LINE_REFRESH_INTERVAL = 250;

    /*
     * The application's main frame
     */
    protected AbstractMainFrame mainFrame;

    /*
     * The various panels added to the main frame
     */
    protected AbstractGraphPanel graphPanel;
    protected AbstractLiveDataPanel liveDataPanel;
    protected AbstractDataSelectPanel dataSelectPanel;

    public MainController (Dispatcher dispatcher) {
        super(dispatcher);

        initializePanels();
    }

    public void start () {
        if (!mainFrame.isVisible())
            EventQueue.invokeLater(this);
    }

    public void run () {
        mainFrame.showFrame();
    }

    public void registerEventTypes (Dispatcher dispatcher) throws RuntimeException {
        /*
         * Triggered when the user closed the main frame
         */
        dispatcher.register(USER_CLOSE_APP_EVENT);

        /*
         * Triggered when the user opts for a new data source
         */
        dispatcher.register(USER_NEW_DATA_SOURCE_EVENT);
    }

    public void registerEventListeners () {
        track(Telemetry.APP_START_EVENT);
        track(Telemetry.MAIN_FRAME_CREATED_EVENT);

        track(DataController.NEW_DATA_SOURCE_EVENT);
        track(DataController.NEW_DATA_SET_EVENT);
    }

    public void dispatch (int eventType, Object data) {
        switch (eventType) {
            case DataController.NEW_DATA_SOURCE_EVENT:
                mainFrame.useDataSource((DataSourceInterface) data);
                start();
                break;
            case DataController.NEW_DATA_SET_EVENT:
                mainFrame.putData((DataSetInterface) data);
                break;
            case Telemetry.MAIN_FRAME_CREATED_EVENT:
                mainFrame = (AbstractMainFrame) data;
                setupMainFrame();
                break;
        }
    }

    protected void initializePanels () {
        /*
         * The graph to display the data
         */
        graphPanel = new GraphPanel();

        /*
         * Display for the most recent values of the data being displayed
         */
        liveDataPanel = new LiveDataPanel();

        /*
         * Configure which data is shown in the main frame
         */
        dataSelectPanel = new DataSelectPanel();
    }

    protected void setupMainFrame () {
        mainFrame.useGraphPanel(graphPanel);
        mainFrame.useLiveDataPanel(liveDataPanel);
        mainFrame.useDataSelectPanel(dataSelectPanel);
    }
}