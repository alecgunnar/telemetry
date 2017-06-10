/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import gnu.io.SerialPortEvent;
import java.io.InputStream;
import java.nio.ByteBuffer;

class GenericListener extends AbstractListener {
    final protected int STATE_HEADER_SEARCH = 0xA;
    final protected int STATE_HEADER_FOUND  = 0xB;

    final protected String MESSAGE_HEAD = "ABCDEF";
    final protected String MESSAGE_FOOT = "UVWXYZ";

    protected InputStream input;
    protected String buffer;
    protected int bufferState;

    public GenericListener () {
        bufferState = STATE_HEADER_SEARCH;
    }

    public void setInputStream (InputStream stream) {
        input = stream;
    }

    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            String data = "";

            try {
                while (input.available() > 0)
                    data += (char) ((int) input.read());
            } catch (Exception e) {
                System.out.println("Could not read data: " + e.getMessage());
            }

            putData(data);
        }
    }

    protected void putData (String data) {
        int offset;

        System.out.println(data);

        buffer += data;

        switch (bufferState) {
            case STATE_HEADER_SEARCH:
                offset = buffer.indexOf(MESSAGE_HEAD);

                if (offset >= 0) {
                    bufferState = STATE_HEADER_FOUND;
                    buffer      = buffer.substring(offset + MESSAGE_HEAD.length());
                }
                break;

            case STATE_HEADER_FOUND:
                offset = buffer.indexOf(MESSAGE_FOOT);

                if (offset >= 0) {
                    bufferState = STATE_HEADER_SEARCH;
                    sendData(buffer.substring(0, offset));
                    buffer = buffer.substring(offset + MESSAGE_FOOT.length());
                }
                break;
        }
    }
}
