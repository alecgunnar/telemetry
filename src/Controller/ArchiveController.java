/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;



public class ArchiveController {
	protected AbstractDataTypeCollection dataTypes;

    protected HashMap<String, DataSourceInterface> dataSources;

    protected DataSourceInterface dataSource;

    protected Thread dataThread;

    ArchiveController (AbstractDataTypeCollection collections) {

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
}