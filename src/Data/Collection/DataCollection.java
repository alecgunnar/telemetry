/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.util.ArrayList;
import java.awt.Color;
import java.util.concurrent.ConcurrentLinkedQueue;

class DataCollection<E> extends ConcurrentLinkedQueue<E> implements DataCollectionInterface<E> {
    protected Color[] colors = {
        Color.GREEN,
        Color.BLUE,
        Color.RED,
        Color.ORANGE,
        Color.MAGENTA,
        Color.YELLOW,
        Color.CYAN
    };

    protected String type;
    protected String units;

    protected static int colorCount = 0;
    protected Color color;

    protected boolean enabled  = false;
    protected boolean provided = false;

    protected int numValues = 0;

    public DataCollection (String type, String units) {
        this.type  = type;
        this.units = units;

        /*
         * Determine which color we'll be using for this line
         */
        color = colors[colorCount % colors.length];
        colorCount++;
    }

    public String getType () {
        return type;
    }

    public String getUnits () {
        return units;
    }

    public Color getColor() {
        return color;
    }

    public void setEnabled (boolean enabled) {
        /*
         * We cannot enable this type it is not provided
         */
        if (enabled && !provided)
            return;

        this.enabled = enabled;
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void setProvided (boolean provided) {
        this.provided = provided;

        /*
         * If this value is not provided, we cannot show it
         */
        if (!provided)
            setEnabled(false);
    }

    public boolean isProvided () {
        return provided;
    }
}
