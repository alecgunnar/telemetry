/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 3, 2016
 */

package sunseeker.telemetry;

interface NetworkInterface {
    public void request(String channel, NetworkSubscriberInterface subscriber);

    public void transmit(String channel, Object data);
}
