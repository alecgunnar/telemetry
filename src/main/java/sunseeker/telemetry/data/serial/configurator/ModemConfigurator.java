package sunseeker.telemetry.data.serial.configurator;

import sunseeker.telemetry.util.SleeperUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public class ModemConfigurator implements Configurator {
    private SleeperUtility sleeper;

    public ModemConfigurator(SleeperUtility sleeper) {
        this.sleeper = sleeper;
    }

    @Override
    public void configure(InputStream rx, OutputStream tx) throws CannotConfigureException {
        try {
            tx.write("+++".getBytes());
            sleeper.sleep(1000);
            tx.write("ATAM\n\r".getBytes());
            tx.write("ATMY 786\n\r".getBytes());
            tx.write("ATDT 786\n\r".getBytes());
            tx.write("ATCN\n\r".getBytes());
        } catch (IOException e) {
            throw new CannotConfigureException("Cannot write to output stream.");
        }
    }
}
