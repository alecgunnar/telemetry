/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.data.source;

import org.wmich.sunseeker.telemetry.data.source.listener.NewDataListener;
import org.wmich.sunseeker.telemetry.data.value.DataValue;
import org.wmich.sunseeker.telemetry.data.Type;
import org.wmich.sunseeker.telemetry.data.Unit;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

abstract class AbstractDataSource implements DataSource {
    protected List<NewDataListener> newDataListeners;

    protected Map<Type, Unit> providedData;

    public AbstractDataSource () {
        newDataListeners = new ArrayList<NewDataListener>();
        providedData     = new HashMap<Type, Unit>();
    }

    public String[] getProvided () {
        String[] types = new String[providedData.size()];
        return providedData.values().toArray(types);
    }

    public void addListener (NewDataListener listener) {
        newDataListeners.add(listener);
    }

    public void removeListener (NewDataListener listener) {
        newDataListeners.remove(listener);
    }

    public void sendNewData (DataValue data) {
        for (NewDataListener listener : newDataListeners)
            listener.receiveData(data);
    }

    public void restart () {
        stop();
        start();
    }

    protected void addProvidedType (Type type, Unit unit) {
        providedData.put(type, unit);
    }
}
