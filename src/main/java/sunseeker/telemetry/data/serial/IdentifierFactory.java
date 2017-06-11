package sunseeker.telemetry.data.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public class IdentifierFactory {
    public CommPortIdentifier createIdentifier(String portName) {
        try {
            return CommPortIdentifier.getPortIdentifier(portName);
        } catch (NoSuchPortException e) {
            return null;
        }
    }
}
