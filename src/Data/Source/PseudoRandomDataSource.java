/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.util.Arrays;
import java.util.Random;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.HashMap;
import java.util.ArrayList;

class PseudoRandomDataSource implements DataSourceInterface, Runnable {
    protected String[] types = {
        "speed", "voltage", "current", "array"
    };

    protected HashMap<String, DataCollectionInterface<Double>> collections;

    public PseudoRandomDataSource (ArrayList<DataCollectionInterface<Double>> collections) {
        this.collections = new HashMap<String, DataCollectionInterface<Double>>();

        for (DataCollectionInterface<Double> collection : collections)
            this.collections.put(collection.getType(), collection);
    }

    public String[] getTypes () {
        return types;
    }

    public boolean provides (String type) {
        Arrays.sort(types);

        return Arrays.binarySearch(types, type) >= 0;
    }

    public void run () {
        Random rand = new Random();

        double val;

        while (true) {
            for (String type : types) {
                if (!collections.containsKey(type))
                    continue;

                val = 200 * ((rand.nextDouble() * 2) - 1);

                collections.get(type).offer(val);
            }

            try {
                Thread.sleep(MainController.LINE_REFRESH_INTERVAL);
            } catch (Exception e) {

            }
        }
    }
}
