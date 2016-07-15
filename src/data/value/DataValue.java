/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.data.value;

import org.wmich.sunseeker.telemetry.data.Type;
import org.wmich.sunseeker.telemetry.data.Unit;

public interface DataValue {
    public double getValue();

    public Type getType();

    public Unit getUnits();

    public double getConvertedValue(int type);
}
