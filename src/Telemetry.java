/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.lang.Runnable;

import java.lang.Exception;
import java.io.IOException;

class Telemetry implements Runnable {

    DataSourceInterface dataSource;
    DataTypeInterface collection;
    FileSelect select;

    protected ArchiveData archive;

    protected AbstractDataTypeCollection dataTypes;

    protected MainController mainController;
    protected DataController dataController;
    protected ArchiveController archiveController;

	public static void main (String[] args) throws IOException {
        EventQueue.invokeLater(new Telemetry());
	}

    public Telemetry () throws IOException{

        dataTypes = new DataTypeCollection();

        /*
         * Add the known data types
         */
        registerDataType("speed", "mph");
        registerDataType("voltage", "volts");
        registerDataType("current", "amps");
        registerDataType("array", "watts");

        dataSource = new PseudoRandomDataSource(dataTypes);

        try{
            select = new FileSelect();
            
            archive = new ArchiveData(select.chooseFile());
            archive.closeAll();
        }
        catch(IOException e){
            System.out.println("Could not write to file" + e);
        }
        catch(Exception e){
            System.out.println("failure: " + e);
        }

    }

    public void run () {
        /*
         * This is the main frame which appears
         */
        AbstractMainFrame mainFrame = new MainFrame();

        /*
         * Controls the rendering of the main window interface
         */
        mainController = new MainController(mainFrame);

        /*
         * The graph to display the data
         */
        AbstractGraphPanel graph = new GraphPanel();
        mainController.useGraphPanel(graph);

        /*
         * Options regarding which data to display
         */
        AbstractDataSelectPanel dataSelect = new DataSelectPanel();
        mainController.useDataSelectPanel(dataSelect);

        /*
         * Display for the most recent values of the data being displayed
         */
        AbstractLiveDataPanel liveData = new LiveDataPanel(dataTypes);
        mainController.useLiveDataPanel(liveData);

        /*
         * Add the line panels to the graph
         */
        mainController.useLinePanels(getLinePanels());

        /*
         * Create the data controller and get the source
         */
        dataController = new DataController(dataTypes, mainFrame);

        getDataSource();

        /*
        * create controller to store data
        */
        archiveController = new ArchiveController(dataTypes);

        /*
         * Start collecting data
         */
        dataController.start();

        /*
         * Start the application
         */
        mainController.start();
    }

    protected void registerDataType (String type, String units) {
        collection = new DataType(type, units);

        dataTypes.add(collection);
    }

    protected AbstractLinePanel[] getLinePanels () {
        AbstractLinePanel[] panels = new AbstractLinePanel[dataTypes.size()];
        int i = 0;

        for (DataTypeInterface type : dataTypes)
            panels[i++] = new LinePanel(type);

            collection.setProvided(
                dataSource.provides(collection.getType())
            );

        return panels;
    }

    protected void getDataSource () {
        DataSourceInterface current;

        if ((current = dataController.getDataSource()) != null) {
            current.stop();
        }

        dataController.promptForDataSource();

        checkDataTypes(dataController.getDataSource());
    }

    protected void checkDataTypes (DataSourceInterface dataSource) {
        if (dataSource != null) {
            for (DataTypeInterface type : dataTypes) {
                type.setProvided(
                    dataSource.provides(type.getType())
                );

                type.setEnabled(true);
            }
        }
    }

    protected void getDataToStore () {

    }

}