/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.gui.event;

import org.wmich.sunseeker.telemetry.gui.frame.MainFrame;

import java.lang.Runnable;

public class MainFrameInitializer implements Runnable {
    protected MainFrame frame;

    public MainFrameInitializer (MainFrame frame) {
        this.frame = frame;
    }

    public void run () {
        frame.setVisible(true);
    }
}