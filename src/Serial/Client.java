/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import gnu.io.CommPortIdentifier;
import gnu.io.CommPort;
import gnu.io.SerialPort;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

class Client {
    protected ConnectionInterface connection;
    protected ListenerInterface listener;

    protected CommPort connected;

    public Client (ConnectionInterface conn, ListenerInterface list) {
        connection = conn;
        listener = list;
    }

    public boolean connect (CommPortIdentifier port) {
        try {
            connected = connection.getConnection(port);

            SerialPort serial = (SerialPort) connected;
            serial.enableReceiveThreshold(1);
            serial.enableReceiveTimeout(10);
            serial.addEventListener(listener);

            listener.setInputStream(serial.getInputStream());

            connection.set(getWriter());

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public void disconnect () {
        if (connected != null) {
            try {
                connection.unset(getWriter());
            } catch (IOException e) { }

            connected.close();

            SerialPort serial = (SerialPort) connected;
            serial.removeEventListener();
        }
    }

    protected BufferedWriter getWriter() throws IOException {
        return new BufferedWriter(
            new OutputStreamWriter(connected.getOutputStream())
        );
    }
}