/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.awt.Color;

interface DataCollectionInterface extends NetworkSubscriberInterface {
    final public static int MAX_DATA_POINTS = 50;
    
    public String getType();

    public String getUnits();

    public Color getColor();

    public void setEnabled(boolean enabled);

    public boolean isEnabled();

    public void setProvided(boolean provided);

    public boolean isProvided();

    public double getMostRecent();

    public double[] getData();

    public void putData(double value);

    public int count();
}
