/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.data;

public class Unit {
    /*
     * Speed
     */
    final public static Unit SPEED_MPH = new Unit("Miles per hour", "MPH");
    final public static Unit SPEED_KPH = new Unit("Kilometers per hour", "KPH");

    /*
     * Voltage
     */
    final public static Unit VOLTAGE_VOLTS = new Unit("Volts", "Volts");

    /*
     * Current
     */
    final public static Unit CURRENT_AMPS = new Unit("Ampres", "Amps");

    protected String name;
    protected String abbrv;

    protected Unit (String name, String abbrv) {
        this.name  = name;
        this.abbrv = abbrv;
    }

    protected Unit (String name) {
        this(name, null);
    }

    public String getName () {
        return name;
    }

    public String getAbbreviation () {
        return abbrv;
    }
}
