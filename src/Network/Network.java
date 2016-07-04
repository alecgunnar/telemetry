/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 3, 2016
 */

package sunseeker.telemetry;

import java.util.HashMap;
import java.util.ArrayList;

class Network implements NetworkInterface {
    protected HashMap<String, ArrayList<NetworkSubscriberInterface>> subscribers;

    public Network () {
        subscribers = new HashMap<String, ArrayList<NetworkSubscriberInterface>>();
    }

    public void request (String channel, NetworkSubscriberInterface subscriber) {
        if (!subscribers.containsKey(channel)) {
            subscribers.put(channel, new ArrayList<NetworkSubscriberInterface>());
        }

        subscribers.get(channel).add(subscriber);
    }

    public void transmit (String channel, Object data) {
        if (subscribers.containsKey(channel)) {
            for (NetworkSubscriberInterface subscriber : subscribers.get(channel)) {
                subscriber.receive(channel, data);
            }
        }
    }
}
