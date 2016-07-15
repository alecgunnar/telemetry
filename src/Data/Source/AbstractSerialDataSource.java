/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package org.wmich.sunseeker.telemetry;;

import javax.swing.JOptionPane;
import java.io.IOException;

public abstract class AbstractSerialDataSource extends AbstractDataSource {
    protected Helper helper;
    protected Client client; 

    public AbstractSerialDataSource () {
        super();

        client = getClient();
        helper = new Helper();
    }

    public void run () {
        promptForSerialPort();
    }

    protected boolean promptForSerialPort () {
        String port = (String) JOptionPane.showInputDialog(
            null,
            "Choose which serial port you would like to connect to:",
            "Choose a Serial Port",
            JOptionPane.PLAIN_MESSAGE,
            null,
            helper.getPortNames(),
            null
        );

        try {
            client.connect(helper.getIdentifier(port));

            return true;
        } catch (IOException e) { }

        return false;
    }

    public void stop () {
        client.disconnect();
    }

    public void pause () {
        
    }

    abstract protected Client getClient();

    abstract protected void process(String data);
}
