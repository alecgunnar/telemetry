/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.event;

public interface EventDispatcher<E> {
    public void addListener(E listener);
    public void removeListener(E listener);
}
