/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 8, 2016
*/

package org.wmich.sunseeker.telemetry.controller;

import org.wmich.sunseeker.telemetry.dispatcher.Dispatcher;
import org.wmich.sunseeker.telemetry.*;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Thread;
import javax.swing.JOptionPane;

public class DataController extends AbstractController {
    /*
     * Events triggered by this controller
     */
    final public static int NEW_DATA_VALUE_EVENT  = 0xA01;
    final public static int NEW_DATA_SOURCE_EVENT = 0xA02;

    protected AbstractDataTypeCollection dataTypes;

    protected HashMap<String, DataSourceInterface> dataSources;

    protected DataSourceInterface dataSource;

    protected Thread dataThread;

    protected JFrame parent;

    public DataController (Dispatcher dispatcher) {
        super(dispatcher);
    }

    public void start () {
        if (dataSource == null)
            return;        

        /*
         * Start loading the data
         */
        dataThread = new Thread(dataSource, "DataSourceThread");

        dataThread.start();
    }

    public void stop () {
        try {
            dataSource.stop();
            dataThread.join();
        } catch (InterruptedException e) {
            System.out.println("Could not stop the data source thread...");
        }
    }

    public void restart () {
        stop();
        start();
    }

    public void promptForDataSource () {
        String source = (String) JOptionPane.showInputDialog(
            parent,
            "Choose a source for the data:",
            "Choose a Data Source",
            JOptionPane.PLAIN_MESSAGE,
            null,
            dataSources.keySet().toArray(),
            dataSource
        );

        dataSource = dataSources.get(source);
    }

    public DataSourceInterface getDataSource () {
        return dataSource;
    }

    public void registerEventTypes (Dispatcher dispatcher) throws Exception {
        /*
         * Triggered when a data set is ready
         */
        dispatcher.register(NEW_DATA_VALUE_EVENT);

        /*
         * Triggered when a new data source is chosen by the user
         */
        dispatcher.register(NEW_DATA_SOURCE_EVENT);
    }

    public void registerEventListeners (Dispatcher dispatcher) throws Exception {

    }

    public void dispatch (int eventType, Object data) {
        
    }

    protected void registerDataSource (DataSourceInterface source) {
        dataSources.put(source.getName(), source);
    }
}