/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.data.source;

import org.wmich.sunseeker.telemetry.event.EventDispatcher;
import org.wmich.sunseeker.telemetry.data.source.listener.NewDataListener;
import org.wmich.sunseeker.telemetry.data.Type;
import org.wmich.sunseeker.telemetry.data.Unit;

import java.util.Map;

public interface DataSource extends EventDispatcher<NewDataListener> {
    public String getName();

    public Map<Type, Unit> getProvided();

    public void start();

    public void stop();

    public void restart();
}
