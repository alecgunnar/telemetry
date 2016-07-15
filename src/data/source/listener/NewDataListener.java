/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.data.source.listener;

import org.wmich.sunseeker.telemetry.data.value.DataValue;

public interface NewDataListener {
    public void receiveData(DataValue data);
}
