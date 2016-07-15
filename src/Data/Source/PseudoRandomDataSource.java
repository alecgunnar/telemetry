/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.data.DataSetInterface;
import org.wmich.sunseeker.telemetry.data.DataSet;

import java.util.Random;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PseudoRandomDataSource extends AbstractDataSource {
    protected Timer scheduler;

    protected Random randGen;

    protected boolean scheduled;

    public PseudoRandomDataSource () {
        scheduler = new Timer();
        randGen   = new Random();

        providedTypes = new String[][] {
            {"speed", "mph"},
            {"voltage", "volts"},
            {"current", "amps"},
            {"array", "watts"}
        };
    }

    public String getName () {
        return "Pseudo Random Number Generator";
    }

    public void run () {
        DataSetInterface data = new DataSet();

        for (String[] type : providedTypes)
            data.put(type[0], 500 * ((randGen.nextDouble() * 2) - 1));

        sendData(data);

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
