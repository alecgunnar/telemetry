/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.data;

public class Type {
    /*
     * Speeds
     */
    public final static Type GEN_SPEED = new Type("Speed");
    public final static Type MC1_SPEED = new Type("Motor Controller 1 Speed");
    public final static Type MC2_SPEED = new Type("Motor Controller 2 Speed");

    /*
     * Voltages
     */
    public final static Type GEN_VOLTAGE = new Type("Voltage");

    protected String name;

    protected Type (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }
}
