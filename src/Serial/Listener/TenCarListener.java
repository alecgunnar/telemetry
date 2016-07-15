/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package org.wmich.sunseeker.telemetry;;

import gnu.io.SerialPortEvent;
import java.io.InputStream;

public class TenCarListener implements ListenerInterface {
    protected InputStream input;

    public void setInputStream (InputStream stream) {
        input = stream;
    }

    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                while (input.available() > 0)
                    System.out.print((char) ((int) input.read()));
            } catch (Exception e) {
                System.out.println("Could not read data: " + e.getMessage());
            }
        }
    }
}