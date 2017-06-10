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
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.Thread;

class ModemConnection implements ConnectionInterface {
    final protected static int TIMEOUT   = 3000;
    final protected static int BAUD_RATE = 9600;
    final protected static int CHANNEL   = 786;

    private BufferedWriter output;

    public CommPort getConnection(CommPortIdentifier port) throws PortInUseException, UnsupportedCommOperationException {
        CommPort comm;

        comm = port.open("SunseekerTelemetryModemPort", TIMEOUT);

        SerialPort serial = (SerialPort) comm;

        serial.setSerialPortParams(
            BAUD_RATE,
            SerialPort.DATABITS_8,
            SerialPort.STOPBITS_2,
            SerialPort.PARITY_NONE
        );

        serial.notifyOnDataAvailable(true);

        return comm;
    }

    public void set (BufferedWriter out) throws IOException {
        output = out;

        send("+++");
        sendCommand("ATAM");
        sendCommand("ATMY " + CHANNEL);
        sendCommand("ATDT " + CHANNEL);
        sendCommand("ATCN");
    }

    public void reset (BufferedWriter out) throws IOException {

    }

    public void unset (BufferedWriter out) throws IOException {

    }

    private void send(String msg) throws IOException {
        System.out.print(msg);

        output.write(msg);
        output.flush();

        waitASecond();
    }

    private void sendCommand(String command) throws IOException {
        send(command + "\n\r");
    }

    private void waitASecond() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) { }
    }
}
