package sunseeker.telemetry;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * Created by aleccarpenter on 6/10/17.
 */
public class AppTest {
    private App subject;

    @Before
    public void setup() {
        subject = new App();
    }

    @Test
    public void should_createALiveDataFrame() {
        assertThat(subject.getLiveDataFrameFrame(), not(nullValue()));
    }

    @Test
    public void should_runMakesTheLiveDataFrameVisible() {
        subject.run();
        assertTrue(subject.getLiveDataFrameFrame().isVisible());
    }
}