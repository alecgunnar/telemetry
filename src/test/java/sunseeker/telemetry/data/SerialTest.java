package sunseeker.telemetry.data;

import gnu.io.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sunseeker.telemetry.data.serial.IdentifierFactory;

import java.io.IOException;

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
    private CommPortIdentifier mockIdentifier;

    @Mock
    private SerialPort mockSerialPort;

    private Serial subject;

    @Before
    public void setup() {
        mockPortName = "/dev/sample_port";

        subject = new Serial(mockPortName, mockIdFactory);

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

        try {
            subject.start();
            fail("Expected cannot start exception");
        } catch (LiveData.CannotStartException e) {
            assertThat(e.getMessage(), is("Serial port does not exist."));
        }
    }

    @Test
    public void start_shouldEmitPortAlreadyOwnedError_ifSerialPortIsAlreadyOwned() throws Exception {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(true);

        try {
            subject.start();
            fail("Expected cannot start exception");
        } catch (LiveData.CannotStartException e) {
            assertThat(e.getMessage(), is("Serial port already in use."));
        }
    }

    @Test
    public void start_shouldEmitPortAlreadyInUse_ifPortIsAlreadyInUse() throws PortInUseException, LiveData.NoSubscriberException {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(false);
        when(mockIdentifier.open(anyString(), anyInt())).thenThrow(PortInUseException.class);

        try {
            subject.start();
            fail("Expected cannot start exception");
        } catch (LiveData.CannotStartException e) {
            assertThat(e.getMessage(), is("Serial port already in use."));
        }
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingNotASerialPort_ifPortIsNotASerialPort() throws PortInUseException, LiveData.NoSubscriberException {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(false);
        when(mockIdentifier.open(anyString(), anyInt())).thenReturn(mock(NotASerialPort.class));

        try {
            subject.start();
            fail("Expected cannot start exception");
        } catch (LiveData.CannotStartException e) {
            assertThat(e.getMessage(), is("Given port is not a serial port."));
        }
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingOperationNotSupported_ifCommOperationIsInvalid() throws PortInUseException, UnsupportedCommOperationException {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(false);
        when(mockIdentifier.open(anyString(), anyInt())).thenReturn(mockSerialPort);
        doThrow(UnsupportedCommOperationException.class).when(mockSerialPort).setSerialPortParams(anyInt(), anyInt(), anyInt(), anyInt());

        try {
            subject.start();
            fail("Expected cannot start exception");
        } catch (LiveData.CannotStartException e) {
            assertThat(e.getMessage(), is("Serial port operation not supported."));
        }
    }


    @Test
    public void start_shouldOpenThePort_ifPortIsNotAlreadyInUse() throws PortInUseException, LiveData.CannotStartException {
        setupValidScenario();

        subject.start();

        verify(mockIdentifier, times(1)).open(anyString(), eq(3000));
    }

    @Test
    public void start_shouldSetSerialPortParams() throws PortInUseException, LiveData.CannotStartException, UnsupportedCommOperationException {
        setupValidScenario();

        subject.start();

        verify(mockSerialPort, times(1)).setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingCannotOpenInputStream_ifInputStreamCannotBeOpened() throws PortInUseException, IOException {
        setupValidScenario();

        when(mockSerialPort.getInputStream()).thenThrow(IOException.class);

        try {
            subject.start();
            fail("Expected cannot start exception");
        } catch (LiveData.CannotStartException e) {
            assertThat(e.getMessage(), is("Cannot open input stream."));
        }
    }

    @Test
    public void start_shouldThrowCannotStartException_sayingCannotOpenOutputStream_ifOutputStreamCannotBeOpened() throws PortInUseException, IOException {
        setupValidScenario();

        when(mockSerialPort.getOutputStream()).thenThrow(IOException.class);

        try {
            subject.start();
            fail("Expected cannot start exception");
        } catch (LiveData.CannotStartException e) {
            assertThat(e.getMessage(), is("Cannot open output stream."));
        }
    }

    private void setupValidScenario() throws PortInUseException {
        when(mockIdFactory.createIdentifier(mockPortName)).thenReturn(mockIdentifier);
        when(mockIdentifier.isCurrentlyOwned()).thenReturn(false);
        when(mockIdentifier.open(anyString(), anyInt())).thenReturn(mockSerialPort);
    }

    private abstract class NotASerialPort extends CommPort { }
}