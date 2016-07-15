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

    public void registerEventTypes (Dispatcher dispatcher) throws RuntimeException {
        registerEventTypes();
    }

    public void registerEventListeners (Dispatcher dispatcher) throws RuntimeException {
        registerEventListeners();
    }

    protected void registerEventTypes () {

    }

    protected void registerEventListeners () {

    }

    public void dispatch (int eventType, Object data) {
        /*
         * Not listening to anything
         */
    }

    protected void emit (int eventType, Object data) {
        try {
            dispatcher.trigger(eventType, data);
        } catch (RuntimeException e) {
            System.out.println("Cannot emit event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void track (int eventType) {
        try {
            dispatcher.listen(eventType, this);
        } catch (RuntimeException e) {
            System.out.println("Cannot listen to event: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void emit (int eventType) {
        emit(eventType, null);
    }
}