/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.data.source.DataSource;
import org.wmich.sunseeker.telemetry.data.source.PseudoRandom;
import org.wmich.sunseeker.telemetry.data.source.listener.NewDataListener;
import org.wmich.sunseeker.telemetry.data.value.DataValue;

public class Telemetry {
    public static void main (String[] args) {
        DataSource source = new PseudoRandom();

        source.addListener(new NewDataListener() {
            public void receiveData (DataValue data) {
                System.out.println(data.getType().getName() + ": " + data.getValue() + " " + data.getUnits().getAbbreviation());
            }
        });

        source.start();
    }
}
