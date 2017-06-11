package sunseeker.telemetry.data;

import gnu.io.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sunseeker.telemetry.data.serial.IdentifierFactory;
import sunseeker.telemetry.data.serial.configurator.Configurator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by aleccarpenter on 6/11/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SerialTest {
    private String mockPortName;

    @Mock
    private LiveData.Subscriber mockSubscriber;

    @Mock
    private IdentifierFactory mockIdFactory;

    @Mock
    private Configurator mockConfigurator;

    @Mock
    private CommPortIdentifier mockIdentifier;

    @Mock
    private SerialPort mockSerialPort;

    @Mock
    private InputStream mockInputStream;

    @Mock
    private OutputStream mockOutputStream;

    private Serial subject;

    @Before
    public void setup() {
        mockPortName = "/dev/sample_port";

        subject = new Serial(mockPortName, mockIdFactory, mockConfigurator);

            subject.subscribe(mockSubscriber);
    }

    @Test
    public void start_shouldThrowException_ifNoSubscriber() {
        subject.subscribe(null);

        try {
            subject.start();
            fail("Should have thrown exception.");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("You must subscribe before you start."));
        }
    }

    @Test
    public void start_shouldEmitPortDoesNotExistError_ifNamedPortDoesNotExist() throws Exception {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(null);

        assertCannotStart("Serial port does not exist.");
    }

    @Test
    public void start_shouldEmitPortAlreadyOwnedError_ifSerialPortIsAlreadyOwned() throws Exception {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(true);

        assertCannotStart("Serial port already in use.");
    }

    @Test
    public void start_shouldEmitPortAlreadyInUse_ifPortIsAlreadyInUse() throws PortInUseException, LiveData.NoSubscriberException {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(false);
        when(mockIdentifier.open(anyString(), anyInt())).thenThrow(PortInUseException.class);

        assertCannotStart("Serial port already in use.");
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingNotASerialPort_ifPortIsNotASerialPort() throws PortInUseException, LiveData.NoSubscriberException {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(false);
        when(mockIdentifier.open(anyString(), anyInt())).thenReturn(mock(NotASerialPort.class));

        assertCannotStart("Given port is not a serial port.");
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingOperationNotSupported_ifCommOperationIsInvalid() throws PortInUseException, UnsupportedCommOperationException {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(false);
        when(mockIdentifier.open(anyString(), anyInt())).thenReturn(mockSerialPort);
        doThrow(UnsupportedCommOperationException.class).when(mockSerialPort).setSerialPortParams(anyInt(), anyInt(), anyInt(), anyInt());

        assertCannotStart("Serial port operation not supported.");
    }


    @Test
    public void start_shouldOpenThePort_ifPortIsNotAlreadyInUse() throws LiveData.CannotStartException, PortInUseException {
        setupValidScenario();

        subject.start();

        verify(mockIdentifier, times(1)).open(anyString(), eq(3000));
    }

    @Test
    public void start_shouldSetSerialPortParams() throws LiveData.CannotStartException, UnsupportedCommOperationException {
        setupValidScenario();

        subject.start();

        verify(mockSerialPort, times(1)).setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingCannotOpenInputStream_ifInputStreamCannotBeOpened() throws IOException {
        setupValidScenario();

        when(mockSerialPort.getInputStream()).thenThrow(IOException.class);

        assertCannotStart("Cannot open input stream.");
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingCannotOpenOutputStream_ifOutputStreamCannotBeOpened() throws IOException {
        setupValidScenario();

        when(mockSerialPort.getOutputStream()).thenThrow(IOException.class);

        assertCannotStart("Cannot open output stream.");
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingCannotListenToPort_ifTooManyListenersArePresent() throws LiveData.CannotStartException, TooManyListenersException {
        setupValidScenario();

        doThrow(TooManyListenersException.class).when(mockSerialPort).addEventListener(any(SerialPortEventListener.class));

        assertCannotStart("Cannot listen to serial port.");
    }

    @Test
    public void start_shouldListenToSerialPort() throws LiveData.CannotStartException, TooManyListenersException {
        setupValidScenario();

        subject.start();

        verify(mockSerialPort, times(1)).addEventListener(subject);
        verify(mockSerialPort, times(1)).notifyOnDataAvailable(true);
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingCannotConfigure_ifConfiguratorThrowsException() throws Configurator.CannotConfigureException {
        setupValidScenario();

        doThrow(Configurator.CannotConfigureException.class).when(mockConfigurator).configure(any(InputStream.class), any(OutputStream.class));

        assertCannotStart("Cannot configure serial connection.");
    }

    public void start_shouldConfigureTheSerialConnection() throws LiveData.CannotStartException, Configurator.CannotConfigureException {
        setupValidScenario();

        subject.start();

        verify(mockConfigurator, times(1)).configure(mockInputStream, mockOutputStream);
    }

    private void setupValidScenario() {
        try {
            when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);

            when(mockIdentifier.isCurrentlyOwned()).thenReturn(false);
            when(mockIdentifier.open(anyString(), anyInt())).thenReturn(mockSerialPort);

            when(mockSerialPort.getInputStream()).thenReturn(mockInputStream);
            when(mockSerialPort.getOutputStream()).thenReturn(mockOutputStream);
        } catch (Exception e) { }
    }

    private void assertCannotStart(String expectedMessage) {
        try {
            subject.start();
            fail("Expected cannot start exception");
        } catch (LiveData.CannotStartException e) {
            assertThat(e.getMessage(), is(expectedMessage));
        }
    }

    private abstract class NotASerialPort extends CommPort { }
}