/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package org.wmich.sunseeker.telemetry;;

import javax.swing.JFrame;

public class TenCarDataSource extends AbstractSerialDataSource {
    public TenCarDataSource () {
        providedTypes = new String[][] {
            {"speed", "mph"}
        };
    }

    public String getName () {
        return "2010 Sunseeker Solar Car";
    }

    protected Client getClient () {
        return new Client(new ModemConnection(), new TenCarListener());
    }

    protected void process (String data) {

    }
}
