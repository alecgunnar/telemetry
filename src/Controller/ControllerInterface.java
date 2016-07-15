/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 14, 2016
*/

package org.wmich.sunseeker.telemetry.controller;

import org.wmich.sunseeker.telemetry.dispatcher.Dispatcher;
import org.wmich.sunseeker.telemetry.dispatcher.DispatchableInterface;

public interface ControllerInterface {
    public void registerEventTypes (Dispatcher dispatcher) throws RuntimeException;

    public void registerEventListeners (Dispatcher dispatcher) throws RuntimeException;
}