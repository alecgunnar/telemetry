/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.data.source;

import org.wmich.sunseeker.telemetry.data.Type;
import org.wmich.sunseeker.telemetry.data.Unit;
import org.wmich.sunseeker.telemetry.data.value.GeneralValue;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map;
import java.lang.Thread;
import java.lang.Runnable;

public class PseudoRandom extends AbstractDataSource {
    final protected long DELAY = 250;

    protected Random random;

    protected Thread thread;

    public PseudoRandom () {
        super();

        random = new Random();
        thread = new Thread(new ThreadRunner());

        addProvidedType(Type.GEN_SPEED, Unit.SPEED_MPH);
        addProvidedType(Type.GEN_VOLTAGE, Unit.VOLTAGE_VOLTS);
    }

    public String getName () {
        return "Pseudo Random Number Generator";
    }

    public void start () {
        if (thread.getState() != Thread.State.TERMINATED)
            stop();

        thread.start();
    }

    public void stop () {
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Could not stop the pseudo random data source.");
        }
    }

    private class ThreadRunner implements Runnable {
        protected Timer timer;

        public ThreadRunner () {
            timer = new Timer();
        }

        public void run () {
            timer.purge();
            timer.scheduleAtFixedRate(new TimerRunner(), 0, PseudoRandom.this.DELAY);
        }

        private class TimerRunner extends TimerTask {
            public void run() {
                for (Map.Entry<Type, Unit> value : PseudoRandom.this.provided.entrySet())
                    PseudoRandom.this.sendNewData(new GeneralValue(200 * (PseudoRandom.this.random.nextDouble() - 0.5), value.getKey(), value.getValue()));
            }
        }
    }
}
