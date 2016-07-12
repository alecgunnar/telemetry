/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Thread;

class ArchiveController {
    protected HashMap<String, DataSourceInterface> dataSources;

    protected DataSourceInterface dataSource;

    protected Thread dataThread;

    protected FileSelect select;

    protected ArchiveData archive;

    protected OpenData open;

    ArchiveController (DataSourceInterface source) {
        dataSources = new HashMap<String, DataSourceInterface>();
        this.dataSource = source;

        select = new FileSelect();
    }

    public void start () throws IOException {

        if (dataSource == null)
            return;        

        /*
         * Start loading data
         */
        dataThread = new Thread(dataSource, "DataSourceThread");

        dataThread.start();

        this.promptForSaveFile();
    }

    public void stop () throws IOException {

        try {
            archive.closeAll();
            dataSource.stop();
            dataThread.join();
        } catch(IOException e) {
            System.out.println("could not close file...");
        } catch (InterruptedException e) {
            System.out.println("Could not stop the data source thread...");
        }

    }

    public void promptForSaveFile () throws IOException{

        /*
         * Start writing to file
         */
        try{            
            archive = new ArchiveData(select.chooseSaveFile());

            this.stop();
        }
        catch(IOException e){
            System.out.println("Could not write to file" + e);
        }
        catch(Exception e){
            System.out.println("failure: " + e);
        }
          
    }

    public DataSourceInterface getDataSource () {
        return dataSource;
    }

    protected void registerDataSource (DataSourceInterface source) {
        dataSources.put(source.getName(), source);
    }

}