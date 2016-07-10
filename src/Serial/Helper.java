/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import java.util.HashMap;
import java.util.Enumeration;
import java.io.IOException;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;

class Helper {
    protected HashMap<String, CommPortIdentifier> ports;

    public Helper () {
        ports = new HashMap<String, CommPortIdentifier>();
    }

    public HashMap getPorts () {
        Enumeration allPorts = CommPortIdentifier.getPortIdentifiers();
        CommPortIdentifier p;

        ports.clear();

        while (allPorts.hasMoreElements()) {
            p = (CommPortIdentifier) allPorts.nextElement();

            if (p.getPortType() == CommPortIdentifier.PORT_SERIAL)
                ports.put(p.getName(), p);
        }

        return ports;
    }

    public Object[] getPortNames () {
        return getPorts().keySet().toArray();
    }

    public CommPortIdentifier getIdentifier (String port) throws IOException {
        try {
            return CommPortIdentifier.getPortIdentifier(port);
        } catch (NoSuchPortException e) {
            throw new IOException("No such port: " + port);
        }
    }
}