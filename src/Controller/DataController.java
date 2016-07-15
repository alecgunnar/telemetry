/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 8, 2016
*/

package org.wmich.sunseeker.telemetry.controller;

import org.wmich.sunseeker.telemetry.dispatcher.Dispatcher;
import org.wmich.sunseeker.telemetry.data.DataSourceObserverInterface;
import org.wmich.sunseeker.telemetry.data.DataSetInterface;
import org.wmich.sunseeker.telemetry.*;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Thread;
import javax.swing.JOptionPane;

public class DataController extends AbstractController implements DataSourceObserverInterface {
    /*
     * Events triggered by this controller
     */
    final public static int NEW_DATA_SET_EVENT    = 200;
    final public static int NEW_DATA_SOURCE_EVENT = 201;

    protected AbstractDataTypeCollection dataTypes;

    protected HashMap<String, DataSourceInterface> dataSources;

    protected DataSourceInterface dataSource;

    protected Thread dataThread;

    protected JFrame parent;

    public DataController (Dispatcher dispatcher) {
        super(dispatcher);

        dataSources = new HashMap<String, DataSourceInterface>();

        registerDataSource(new PseudoRandomDataSource());
        registerDataSource(new TenCarDataSource());
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
        if (dataSource != null) {
            try {
                dataSource.stop();
                dataThread.join();
            } catch (InterruptedException e) {
                System.out.println("Could not stop the data source thread...");
            }
        }
    }

    public void restart () {
        stop();
        start();
    }

    public void promptForDataSource () {
        /*
         * Stop collecing data
         */
        stop();

        /*
         * Present the available data sources in a menu to the user
         */
        String source = (String) JOptionPane.showInputDialog(
            parent,
            "Choose a source for the data:",
            "Choose a Data Source",
            JOptionPane.PLAIN_MESSAGE,
            null,
            dataSources.keySet().toArray(),
            dataSource
        );

        /*
         * Update the data source to the one selected by the user
         */
        if (source != null) {
            /*
             * If we already have a data source unsubscribe from it
             */
            if (dataSource != null)
                dataSource.unwatch(this);

            dataSource = dataSources.get(source);
            dataSource.watch(this);

            emit(NEW_DATA_SOURCE_EVENT, dataSource);
        } else if (dataSource == null) {
            /*
             * If no data source is selected, force one to be
             */
            promptForDataSource();

            return;
        }

        /*
         * Resume collecting data
         */
        start();
    }

    public void registerEventTypes (Dispatcher dispatcher) throws RuntimeException {
        /*
         * Triggered when a data set is ready
         */
        dispatcher.register(NEW_DATA_SET_EVENT);

        /*
         * Triggered when a new data source is chosen by the user
         */
        dispatcher.register(NEW_DATA_SOURCE_EVENT);
    }

    public void registerEventListeners () {
        track(Telemetry.APP_START_EVENT);
    }

    public void dispatch (int eventType, Object data) {
        switch (eventType) {
            /*
             * When the app starts, prompt the user to pick a data source
             */
            case Telemetry.APP_START_EVENT:
                promptForDataSource();
                break;
        }
    }

    public void putData (DataSetInterface dataSet) {
        emit(NEW_DATA_SET_EVENT, dataSet);
    }

    protected void registerDataSource (DataSourceInterface source) {
        dataSources.put(source.getName(), source);
    }
}