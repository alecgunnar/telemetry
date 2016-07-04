/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 3, 2016
 */

package sunseeker.telemetry;

interface NetworkSubscriberInterface {
    public void receive(String channel, Object data);
}
