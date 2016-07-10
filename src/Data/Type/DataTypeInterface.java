/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.awt.Color;
import java.util.List;

interface DataTypeInterface {
    final public static int MAX_DATA_POINTS = 50;
    
    public String getType();

    public String getUnits();

    public Color getColor();

    public void setEnabled(boolean enabled);

    public boolean isEnabled();

    public void setProvided(boolean provided);

    public boolean isProvided();

    public void putValue(double value);

    public List<Double> getData();

    public double getMinimumValue();

    public double getCurrentValue();

    public double getMaximumValue();
}
