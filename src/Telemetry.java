/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.controller.AbstractController;
import org.wmich.sunseeker.telemetry.controller.MainController;
import org.wmich.sunseeker.telemetry.controller.DataController;
import org.wmich.sunseeker.telemetry.dispatcher.Dispatcher;
import org.wmich.sunseeker.telemetry.dispatcher.DispatchableInterface;

import java.util.ArrayList;

public class Telemetry extends AbstractController implements DispatchableInterface {
    /*
     * Events triggered at the application level
     */
    final public static int APP_START_EVENT = 2;
    final public static int APP_TERM_EVENT  = 0;

    final public static int MAIN_FRAME_CREATED_EVENT = 1;

    /*
     * The controllers to manage each part of the application
     */
    protected ArrayList<AbstractController> controllers;

	public static void main (String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        Telemetry telemetry   = new Telemetry(dispatcher);

        telemetry.run();
	}

    public Telemetry (Dispatcher dispatcher) {
        super(dispatcher);

        controllers = new ArrayList<AbstractController>();
    }

    public void run () {
        /*
         * Initialize the application
         */
        registerControllers();
        registerEventTypes();
        registerEventListeners();

        /*
         * Create the main frame
         */
        emit(MAIN_FRAME_CREATED_EVENT, new MainFrame());

        /*
         * Get the application rolling
         */
        emit(APP_START_EVENT);
    }

    public void dispatch (int eventType, Object data) {
        switch (eventType) {
            case MainController.USER_CLOSE_APP_EVENT:
                terminate();
                break;
        }
    }

    protected void registerControllers () {
        controllers.add(new MainController(dispatcher));
        controllers.add(new DataController(dispatcher));
    }

    protected void registerEventTypes () {
        try {
            /*
             * Triggered when all initialization is complete
             */
            dispatcher.register(APP_START_EVENT);

            /*
             * Triggered once the user has chosen to quit
             */
            dispatcher.register(APP_TERM_EVENT);

            /*
             * Triggered when the main frame has been created
             */
            dispatcher.register(MAIN_FRAME_CREATED_EVENT);

            for (AbstractController controller : controllers)
                controller.registerEventTypes(dispatcher);
        } catch (Exception e) {
            System.out.println("Dispatcher error: " + e.getMessage());
        }
    }

    protected void registerEventListeners () {
        try {
            track(MainController.USER_CLOSE_APP_EVENT);

            for (AbstractController controller : controllers)
                controller.registerEventListeners(dispatcher);
        } catch (Exception e) {
            System.out.println("Dispatcher error: " + e.getMessage());
        }
    }

    protected void terminate () {
        emit(APP_TERM_EVENT);
    }
}
