/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 8, 2016
*/

package sunseeker.telemetry;

import java.util.ArrayList;

class DataController {
    protected ArrayList<DataCollectionInterface> dataCollections;

    public DataController (ArrayList<DataCollectionInterface> collections) {
        dataCollections = collections;
    }
}