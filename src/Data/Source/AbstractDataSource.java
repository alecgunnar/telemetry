/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.data.DataSourceObserverInterface;
import org.wmich.sunseeker.telemetry.data.DataSetInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public abstract class AbstractDataSource implements DataSourceInterface {
    protected ArrayList<DataSourceObserverInterface> observers;

    protected String[][] providedTypes;

    public AbstractDataSource () {
        observers = new ArrayList<DataSourceObserverInterface>();
    }

    public String[][] getTypes () {
        return providedTypes;
    }

    public boolean provides (String type) {
        Arrays.sort(providedTypes);

        return Arrays.binarySearch(providedTypes, type) >= 0;
    }

    public void watch (DataSourceObserverInterface observer) {
        observers.add(observer);
    }

    public void unwatch (DataSourceObserverInterface observer) {
        observers.remove(observer);
    }

    protected void sendData (DataSetInterface data) {
        for (DataSourceObserverInterface observer : observers)
            observer.putData(data);
    }
}
