package sunseeker.telemetry.data.serial;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.when;

/**
 * Created by aleccarpenter on 6/11/17.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CommPortIdentifier.class)
public class IdentifierFactoryTest {
    private IdentifierFactory subject;

    @Mock
    private CommPortIdentifier mockIdentifier;

    @Before
    public void setup() {
        subject = new IdentifierFactory();

        PowerMockito.mockStatic(CommPortIdentifier.class);
    }

    @Test
    public void should_returnCommPortIdentifier_givenAPortNameOfARealPort() throws NoSuchPortException {
        String portName = "/dev/test_port";

        when(CommPortIdentifier.getPortIdentifier(portName)).thenReturn(mockIdentifier);

        assertThat(subject.createIdentifier(portName), is(mockIdentifier));
    }

    @Test
    public void should_returnNull_givenAPortNameOfAPortThatDoesNotExist() throws NoSuchPortException {
        String portName = "/dev/test_port";

        when(CommPortIdentifier.getPortIdentifier(portName)).thenThrow(NoSuchPortException.class);

        assertThat(subject.createIdentifier(portName), is(isNull()));
    }
}