/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 14, 2016
 */

package org.wmich.sunseeker.telemetry.dispatcher;

public interface DispatchableInterface {
    public void dispatch (int eventType, Object data);
}
