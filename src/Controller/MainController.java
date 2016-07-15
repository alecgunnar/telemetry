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
import javax.swing.Timer;
import java.awt.EventQueue;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.lang.Runnable;
import java.lang.Thread;

public class MainController extends AbstractController implements Runnable, WindowListener {
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
                setupMainFrame((AbstractMainFrame) data);
                break;
        }
    }

    public void windowClosing (WindowEvent e) {
        emit(USER_CLOSE_APP_EVENT);
    }

    public void windowActivated (WindowEvent e) {
        /*
         * Don't care...
         */
    }

    public void windowClosed (WindowEvent e) {
        /*
         * Don't care...
         */
    }

    public void windowDeactivated (WindowEvent e) {
        /*
         * Don't care...
         */
    }

    public void windowDeiconified (WindowEvent e) {
        /*
         * Don't care...
         */
    }

    public void windowGainedFocus (WindowEvent e) {
        /*
         * Don't care...
         */
    }

    public void windowIconified (WindowEvent e) {
        /*
         * Don't care...
         */
    }

    public void windowLostFocus (WindowEvent e) {
        /*
         * Don't care...
         */
    }

    public void windowOpened (WindowEvent e) {
        /*
         * Don't care...
         */
    }

    public void windowStateChanged (WindowEvent e) {
        /*
         * Don't care...
         */
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

    protected void setupMainFrame (AbstractMainFrame frame) {
        mainFrame = frame;

        /*
         * Configure the frame to use the various panels
         */
        mainFrame.useGraphPanel(graphPanel);
        mainFrame.useLiveDataPanel(liveDataPanel);
        mainFrame.useDataSelectPanel(dataSelectPanel);

        /*
         * Listen to the frame
         */
        mainFrame.addWindowListener(this);
    }
}
