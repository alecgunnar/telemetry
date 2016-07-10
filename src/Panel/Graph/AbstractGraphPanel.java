/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.awt.Color;

abstract class AbstractGraphPanel extends AbstractPanel {
    final public static int AXIS_WIDTH   = 2;
    final public static int AXIS_INSET   = 30;
    final public static int FULL_INSET   = AXIS_WIDTH + AXIS_INSET;
    final public static int PANEL_HEIGHT = 400;
    final public static int PANEL_WIDTH  = AbstractMainFrame.FRAME_WIDTH - AbstractMainFrame.AXIS_PADDING;

    final public static int Y_AXIS_MIN   = -500; // Must be <= 0
    final public static int Y_AXIS_MAX   = 500;
    final public static int Y_AXIS_RANGE = Math.abs(Y_AXIS_MIN) + Y_AXIS_MAX;
    final public static int Y_AXIS_SCALE = 10;
    final public static int Y_AXIS_INSET = AXIS_INSET;

    final public static int X_AXIS_SCALE = (PANEL_WIDTH - AXIS_INSET) / DataTypeInterface.MAX_DATA_POINTS;
    final public static int X_AXIS_INSET = PANEL_HEIGHT - AXIS_INSET;

    final public static int MAX_POINTS   = PANEL_WIDTH / X_AXIS_SCALE;

    protected static int xAxisInset;
    protected static double ratio;

    public AbstractGraphPanel () {
        setBackground(Color.WHITE);

        /*
         * Calculate the x-axis inset
         */
        xAxisInset = (int) ((float) PANEL_HEIGHT * (Y_AXIS_MAX / (float) Y_AXIS_RANGE));

        /*
         * Calculate the y axis ratio
         */
        ratio = PANEL_HEIGHT / (double) Y_AXIS_RANGE;
    }

    public static int getYPos (double value) {
        double pos = 0;

        if (value > 0)
            pos = Y_AXIS_MAX * (value / Y_AXIS_MAX);

        if (value < 0)
            pos = Y_AXIS_MIN * (value / Y_AXIS_MIN);

        return xAxisInset + (int) (pos * ratio);
    }
}
