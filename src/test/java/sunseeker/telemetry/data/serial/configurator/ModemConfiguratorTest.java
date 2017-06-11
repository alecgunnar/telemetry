package sunseeker.telemetry.data.serial.configurator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import sunseeker.telemetry.util.SleeperUtility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by aleccarpenter on 6/11/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Thread.class)
public class ModemConfiguratorTest {
    private ModemConfigurator subject;

    @Mock
    private SleeperUtility mockSleeper;

    @Mock
    private InputStream mockInputStream;

    @Mock
    private OutputStream mockOutputStream;

    @Before
    public void setup() {
        subject = new ModemConfigurator(mockSleeper);
    }

    @Test
    public void configure_shouldThrowException_ifWriteThrowsException() throws IOException {
        doThrow(IOException.class).when(mockOutputStream).write(any());

        try {
            subject.configure(mockInputStream, mockOutputStream);
            fail("Expected cannot configure exception");
        } catch (Configurator.CannotConfigureException e) {
            assertThat(e.getMessage(), is("Cannot write to output stream."));
        }
    }

    @Test
    public void configure_shouldSwitchToCommandMode_thenSleepForOneSecond_thenRunAttentionCommands() throws IOException, Configurator.CannotConfigureException, InterruptedException {
        mockStatic(Thread.class);

        subject.configure(mockInputStream, mockOutputStream);

        InOrder verifyOrder = inOrder(mockOutputStream, mockSleeper);

        verifyOrder.verify(mockOutputStream).write("+++".getBytes());
        verifyOrder.verify(mockSleeper).sleep(1000);
        verifyOrder.verify(mockOutputStream).write("ATAM\n\r".getBytes());
        verifyOrder.verify(mockOutputStream).write("ATMY 786\n\r".getBytes());
        verifyOrder.verify(mockOutputStream).write("ATDT 786\n\r".getBytes());
        verifyOrder.verify(mockOutputStream).write("ATCN\n\r".getBytes());
    }
}