/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.util.Arrays;
import java.util.Random;
import java.lang.Runnable;
import java.lang.Thread;

class PseudoRandomDataSource implements DataSourceInterface, Runnable {
    protected String[] types = {
        "speed"
    };

    protected NetworkInterface network;

    public PseudoRandomDataSource (NetworkInterface network) {
        this.network = network;
    }

    public String[] getTypes () {
        return types;
    }

    public boolean provides (String type) {
        Arrays.sort(types);

        return Arrays.binarySearch(types, type) >= 0;
    }

    public void run () {
        Random rand = new Random();

        while (true) {
            double val = 200 * ((rand.nextDouble() * 2) - 1);

            network.transmit(SPEED_UPDATE, val);

            try {
                Thread.sleep(250);
            } catch (Exception e) {

            }
        }
    }
}
