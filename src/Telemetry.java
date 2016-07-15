/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.controller.AbstractController;
import org.wmich.sunseeker.telemetry.controller.MainController;
import org.wmich.sunseeker.telemetry.controller.DataController;
import org.wmich.sunseeker.telemetry.dispatcher.Dispatcher;
import org.wmich.sunseeker.telemetry.dispatcher.DispatchableInterface;

import java.util.ArrayList;

public class Telemetry extends AbstractController implements DispatchableInterface {
    /*
     * Events triggered at the application level
     */
    final public static int APP_START_EVENT = 0x02;
    final public static int APP_TERM_EVENT  = 0x00;

    final public static int MAIN_FRAME_CREATED_EVENT = 0x01;

    /*
     * The controllers to manage each part of the application
     */
    protected ArrayList<AbstractController> controllers;

	public static void main (String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        Telemetry telemetry   = new Telemetry(dispatcher);

        telemetry.run();
	}

    public Telemetry (Dispatcher dispatcher) {
        super(dispatcher);

        controllers = new ArrayList<AbstractController>();
    }

    public void run () {
        /*
         * Initialize the application
         */
        registerControllers();
        registerEventTypes();
        registerEventListeners();

        /*
         * Create the main frame
         */
        emit(MAIN_FRAME_CREATED_EVENT, new MainFrame());

        /*
         * Get the application rolling
         */
        emit(APP_START_EVENT);
    }

    public void dispatch (int eventType, Object data) {
        switch (eventType) {
            case MainController.USER_CLOSE_APP_EVENT:
                terminate();
                break;
        }
    }

    protected void registerControllers () {
        controllers.add(new MainController(dispatcher));
        controllers.add(new DataController(dispatcher));
    }

    protected void registerEventTypes () {
        try {
            /*
             * Triggered when all initialization is complete
             */
            dispatcher.register(APP_START_EVENT);

            /*
             * Triggered once the user has chosen to quit
             */
            dispatcher.register(APP_TERM_EVENT);

            /*
             * Triggered when the main frame has been created
             */
            dispatcher.register(MAIN_FRAME_CREATED_EVENT);

            for (AbstractController controller : controllers)
                controller.registerEventTypes(dispatcher);
        } catch (Exception e) {
            System.out.println("Dispatcher error: " + e.getMessage());
        }
    }

    protected void registerEventListeners () {
        try {
            track(MainController.USER_CLOSE_APP_EVENT);

            for (AbstractController controller : controllers)
                controller.registerEventListeners(dispatcher);
        } catch (Exception e) {
            System.out.println("Dispatcher error: " + e.getMessage());
        }
    }

    protected void terminate () {
        emit(APP_TERM_EVENT);
    }

    // public void begin () {
    //     /*
    //      * This is the main frame which appears
    //      */
    //     AbstractMainFrame mainFrame = new MainFrame();

    //     /*
    //      * Controls the rendering of the main window Interface
    //      */
    //     mainController = new MainController(mainFrame);

    //     /*
    //      * The graph to display the data
    //      */
    //     AbstractGraphPanel graph = new GraphPanel();
    //     mainController.useGraphPanel(graph);

    //     /*
    //      * Options regarding which data to display
    //      */
    //     AbstractDataSelectPanel dataSelect = new DataSelectPanel();
    //     mainController.useDataSelectPanel(dataSelect);

    //     /*
    //      * Display for the most recent values of the data being displayed
    //      */
    //     AbstractLiveDataPanel liveData = new LiveDataPanel(dataTypes);
    //     mainController.useLiveDataPanel(liveData);

    //     /*
    //      * Add the line panels to the graph
    //      */
    //     mainController.useLinePanels(getLinePanels());

    //     /*
    //      * Create the data controller and get the source
    //      */
    //     dataController = new DataController(dataTypes, mainFrame);

    //     getDataSource();

        
    //      * Start collecting data
         
    //     dataController.start();

    //     /*
    //      * Start the application
    //      */
    //     mainController.start();
    // }

    // protected void registerDataType (String type, String units) {
    //     DataTypeInterface collection = new DataType(type, units);

    //     dataTypes.add(collection);
    // }

    // protected AbstractLinePanel[] getLinePanels () {
    //     AbstractLinePanel[] panels = new AbstractLinePanel[dataTypes.size()];
    //     int i = 0;

    //     for (DataTypeInterface type : dataTypes)
    //         panels[i++] = new LinePanel(type);

    //     return panels;
    // }

    // protected void getDataSource () {
    //     DataSourceInterface current;

    //     if ((current = dataController.getDataSource()) != null) {
    //         current.stop();
    //     }

    //     dataController.promptForDataSource();

    //     checkDataTypes(dataController.getDataSource());
    // }

    // protected void checkDataTypes (DataSourceInterface dataSource) {
    //     if (dataSource != null) {
    //         for (DataTypeInterface type : dataTypes) {
    //             type.setProvided(
    //                 dataSource.provides(type.getType())
    //             );

    //             type.setEnabled(true);
    //         }
    //     }
    // }
}
