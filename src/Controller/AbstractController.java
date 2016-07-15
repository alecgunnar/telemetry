/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 14, 2016
*/

package org.wmich.sunseeker.telemetry.controller;

import org.wmich.sunseeker.telemetry.dispatcher.Dispatcher;
import org.wmich.sunseeker.telemetry.dispatcher.DispatchableInterface;

public abstract class AbstractController implements ControllerInterface, DispatchableInterface {
    protected Dispatcher dispatcher;

    public AbstractController (Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void registerEventTypes (Dispatcher dispatcher) throws Exception {
        /*
         * Nothing to emit
         */
    }

    public void registerEventListeners (Dispatcher dispatcher) throws Exception {
        /*
         * Nothing to listen for
         */
    }

    public void dispatch (int eventType, Object data) {
        /*
         * Not listening to anything
         */
    }

    protected void emit (int eventType, Object data) {
        try {
            dispatcher.trigger(eventType, data);
        } catch (Exception e) {
            System.out.println("Cannot emit event: " + e.getMessage());
        }
    }

    protected void track (int eventType) {
        try {
            dispatcher.listen(eventType, this);
        } catch (Exception e) {
            System.out.println("Cannot listen to event: " + e.getMessage());
        }
    }

    protected void emit (int eventType) {
        emit(eventType, null);
    }
}