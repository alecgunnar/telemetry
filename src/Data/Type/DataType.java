/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

class DataType implements DataTypeInterface {
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

    protected double min = 0;
    protected double cur = 0;
    protected double max = 0;

    protected List<Double> data;

    public DataType (String type, String units) {
        this.type  = type;
        this.units = units;

        /*
         * Determine which color we'll be using for this line
         */
        color = colors[colorCount % colors.length];
        colorCount++;

        /*
         * Create a synconized data list
         */
        data = Collections.synchronizedList(new ArrayList<Double>());
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

    public void putValue (double value) {
        data.add(value);

        cur = value;

        if (data.size() == 0) {
            min = value;
            max = value;
        } else {
            if (value < min)
                min = value;

            if (value > max)
                max = value;
        }
    }

    public List<Double> getData () {
        return data;
    }

    public double getMinimumValue () {
        return min;
    }

    public double getCurrentValue () {
        return cur;
    }

    public double getMaximumValue () {
        return max;
    }
}
