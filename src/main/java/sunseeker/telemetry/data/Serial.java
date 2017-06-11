package sunseeker.telemetry.data;

import gnu.io.*;
import sunseeker.telemetry.data.serial.IdentifierFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public class Serial implements LiveData, SerialPortEventListener {
    private String portName;
    private IdentifierFactory idFactory;
    private Subscriber subscribed;

    private CommPortIdentifier commPortId;
    private SerialPort serialPort;

    private InputStream rx;
    private OutputStream tx;

    public Serial(String portName) {
        this(portName, new IdentifierFactory());
    }

    public Serial(String portName, IdentifierFactory idFactory) {
        this.portName = portName;
        this.idFactory = idFactory;
    }

    @Override
    public void start() throws CannotStartException {
        bootstrap();
    }

    @Override
    public void stop() {

    }

    @Override
    public void subscribe(Subscriber sub) {
        subscribed = sub;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

    }

    private void bootstrap() throws CannotStartException {
        if (subscribed == null) {
            throw new NoSubscriberException("You must subscribe before you start.");
        }

        getSerialPort();

        openRxTxStreams();

        listenToSerialPort();
    }

    private void getSerialPort() throws CannotStartException {
        getCommPortId();

        CommPort commPort;

        try {
            commPort = commPortId.open(this.getClass().getName(), 3000);
        } catch (PortInUseException e) {
            throw new CannotStartException("Serial port already in use.");
        }

        if (!(commPort instanceof SerialPort)) {
            throw new CannotStartException("Given port is not a serial port.");
        }

        serialPort = (SerialPort) commPort;

        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
            throw new CannotStartException("Serial port operation not supported.");
        }
    }

    private void getCommPortId() throws CannotStartException {
        commPortId = idFactory.createIdentifier(portName);

        if (commPortId == null) {
            throw new CannotStartException("Serial port does not exist.");
        }

        if (commPortId.isCurrentlyOwned()) {
            throw new CannotStartException("Serial port already in use.");
        }
    }

    private void openRxTxStreams() throws CannotStartException {
        try {
            rx = serialPort.getInputStream();
        } catch (IOException e) {
            throw new CannotStartException("Cannot open input stream.");
        }

        try {
            tx = serialPort.getOutputStream();
        } catch (IOException e) {
            throw new CannotStartException("Cannot open output stream.");
        }
    }

    private void listenToSerialPort() throws CannotStartException {
        try {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (TooManyListenersException e) {
            throw new CannotStartException("Cannot listen to serial port.");
        }
    }
}
