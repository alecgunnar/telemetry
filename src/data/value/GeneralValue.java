/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.data.value;

import org.wmich.sunseeker.telemetry.data.Type;
import org.wmich.sunseeker.telemetry.data.Unit;

public class GeneralValue implements DataValue {
    protected double value;

    protected Type type;
    protected Unit units;

    public GeneralValue (double value, Type type, Unit units) {
        this.value = value;
        this.type  = type;
        this.units = units;
    }

    public double getValue () {
        return value;
    }

    public Type getType () {
        return type;
    }

    public Unit getUnits () {
        return units;
    }

    public double getConvertedValue (int type) {
        return value;
    }
}
