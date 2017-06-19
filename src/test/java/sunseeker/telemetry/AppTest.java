package sunseeker.telemetry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sunseeker.telemetry.ui.LiveData;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by aleccarpenter on 6/10/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppTest {
    private App subject;

    @Mock
    private LiveData mockLiveDataFrame;

    @Before
    public void setup() {
        subject = new App(mockLiveDataFrame);
    }

    @Test
    public void should_runMakesTheLiveDataFrameVisible() {
        subject.run();

        verify(mockLiveDataFrame, times(1)).setVisible(true);
    }
}