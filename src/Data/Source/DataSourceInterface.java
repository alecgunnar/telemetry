/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.data.DataSourceObserverInterface;

public interface DataSourceInterface extends Runnable {
    public String getName();

    public String[][] getTypes();

    public boolean provides(String type);

    public void pause();

    public void stop();

    public void watch(DataSourceObserverInterface observer);

    public void unwatch(DataSourceObserverInterface observer);
}
