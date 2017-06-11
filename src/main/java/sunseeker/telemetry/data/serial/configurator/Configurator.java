package sunseeker.telemetry.data.serial.configurator;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public interface Configurator {
    void configure(InputStream rx, OutputStream tx) throws CannotConfigureException;

    class CannotConfigureException extends Exception {
        public CannotConfigureException(String msg) {
            super(msg);
        }
    }
}
