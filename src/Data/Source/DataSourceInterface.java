/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

interface DataSourceInterface extends Runnable {
    final public static String SPEED_UPDATE = "speed_update";

    public String[] getTypes();

    public boolean provides(String type);
}
