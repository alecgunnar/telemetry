/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.util.ArrayList;
import java.awt.Color;

class DataCollection implements DataCollectionInterface {
    protected Color[] colors = {
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

    protected double[] data = new double[MAX_DATA_POINTS];
    protected ArrayList<DataCollectionSubscriberInterface> subscribers;

    protected int numValues = 0;

    public DataCollection (String type, String units) {
        this.type  = type;
        this.units = units;

        /*
         * Determine which color we'll be using for this line
         */
        color = colors[colorCount % colors.length];
        colorCount++;

        subscribers = new ArrayList<DataCollectionSubscriberInterface>();
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
        if (enabled && !provided) return;

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
        if (!provided) setEnabled(false);
    }

    public boolean isProvided () {
        return provided;
    }

    public double getMostRecent () {
        return data[0];
    }

    public double[] getData () {
        return data;
    }

    public void putData (double value) {
        int i;
        double hold;

        if (numValues == MAX_DATA_POINTS) {
            numValues--;

            for (i = 0; i < numValues; i++) {
                data[i] = data[i+1];
            }
        }

        data[numValues++] = value;
    }

    public int count () {
        return numValues;
    }

    public void receive (String channel, Object data) {
        if (channel.equals(type + "_update"))
            putData((double) data);
    }
}
