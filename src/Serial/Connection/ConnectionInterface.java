/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package org.wmich.sunseeker.telemetry;;

import gnu.io.CommPortIdentifier;
import gnu.io.CommPort;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedWriter;
import java.io.IOException;

public interface ConnectionInterface {
    public CommPort getConnection(CommPortIdentifier port) throws PortInUseException, UnsupportedCommOperationException;

    public void set(BufferedWriter out) throws IOException;

    public void reset(BufferedWriter out) throws IOException;

    public void unset(BufferedWriter out) throws IOException;
}