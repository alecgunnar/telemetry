/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 14, 2016
 */

package org.wmich.sunseeker.telemetry.dispatcher;

import java.util.HashMap;
import java.util.ArrayList;

public class Dispatcher {
    protected HashMap<Integer, ArrayList<DispatchableInterface>> dispatchables;

    public Dispatcher () {
        dispatchables = new HashMap<Integer, ArrayList<DispatchableInterface>>();
    }

    public void register (int eventType) throws Exception {
        if (dispatchables.containsKey(eventType)) {
            throw new Exception("Cannot redeclare event type: " + eventType);
        }

        dispatchables.put(eventType, new ArrayList<DispatchableInterface>());
    }

    public void listen (int eventType, DispatchableInterface dispatchable) throws Exception {
        if (!dispatchables.containsKey(eventType)) {
            throw new Exception("Cannot listen on non-existent event type: " + eventType);
        }

        dispatchables.get(eventType).add(dispatchable);
    }

    public void trigger (int eventType, Object data) throws Exception {
        if (!dispatchables.containsKey(eventType)) {
            throw new Exception("Cannot trigger non-existent event type: " + eventType);
        }

        for (DispatchableInterface dispatchable : dispatchables.get(eventType)) {
            dispatchable.dispatch(eventType, data);
        }   
    }

    public void trigger (int eventType) throws Exception {
        trigger(eventType, null);
    }
}
