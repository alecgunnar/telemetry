/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;;

import java.util.Random;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PseudoRandomDataSource extends AbstractDataSource {
    protected Timer scheduler;

    protected Random randGen;

    protected boolean scheduled;

    public PseudoRandomDataSource (AbstractDataTypeCollection dataTypes) {
        super(dataTypes);

        scheduler = new Timer();
        randGen   = new Random();

        providedTypes = new String[] {
            "speed", "voltage", "current", "array"
        };
    }

    public String getName () {
        return "Pseudo Random Data Source";
    }

    public void run () {
        List<Double> data;
        double val;

        for (String type : providedTypes) {
            if (!types.containsKey(type))
                continue;

            data = types.get(type).getData();

            val = 500 * ((randGen.nextDouble() * 2) - 1);

            data.clear();
            types.get(type).putValue(val);
        }

        if (!scheduled)
            scheduleTask();
    }

    public void stop () {
        scheduler.cancel();
        scheduler.purge();

        scheduled = false;
    }

    public void pause () {
        stop();
    }

    protected void scheduleTask () {
        long delay = 250;

        final DataSourceInterface data = this;

        scheduler.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                data.run();
            }
        }, delay, delay);

        scheduled = true;
    }
}
