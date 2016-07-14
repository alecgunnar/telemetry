/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import javax.swing.JFrame;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Thread;

class ArchiveController {
    protected AbstractDataTypeCollection dataTypes;

    protected HashMap<String, DataSourceInterface> dataSources;

    protected DataSourceInterface dataSource;

    protected Thread dataThread;

    protected JFrame parent;

    protected FileSelect select;

    protected ArchiveData archive;

    protected OpenData open;

    ArchiveController (AbstractDataTypeCollection collections, JFrame frame) {

        dataTypes = collections;
        parent = frame;

        dataSources = new HashMap<String, DataSourceInterface>();

        /*
         * Register the known data source types
         */
        registerDataSource(new PseudoRandomDataSource(dataTypes));
        registerDataSource(new TenCarDataSource(dataTypes, parent));
        
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
        
        promptForSaveFile();
        
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

    public void promptForSaveFile () throws IOException {

        /*
         * select and open file
         */
        try{            
            archive = new ArchiveData(select.chooseSaveFile());
        }
        catch(IOException e){
            System.out.println("Could not write to file" + e);
        }
        catch(Exception e){
            System.out.println("failure: " + e);
        }
          
    }

    public void saveFile () {
        //archive.saveFIle();
    }

    public DataSourceInterface getDataSource () {
        return dataSource;
    }

    protected void registerDataSource (DataSourceInterface source) {
        dataSources.put(source.getName(), source);
    }

}