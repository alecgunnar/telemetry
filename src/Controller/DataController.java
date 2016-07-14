/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 8, 2016
*/

package sunseeker.telemetry;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Thread;
import javax.swing.JOptionPane;

class DataController {
    protected AbstractDataTypeCollection dataTypes;

    protected HashMap<String, DataSourceInterface> dataSources;

    protected DataSourceInterface dataSource;

    protected Thread dataThread;

    protected JFrame parent;

    public DataController (AbstractDataTypeCollection collections, JFrame frame) {
        dataTypes = collections;
        parent = frame;

        dataSources = new HashMap<String, DataSourceInterface>();

        /*
         * Register the known data source types
         */
        registerDataSource(new PseudoRandomDataSource(dataTypes));
        registerDataSource(new TenCarDataSource(dataTypes, parent));
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

    protected void registerDataSource (DataSourceInterface source) {
        dataSources.put(source.getName(), source);
    }

}