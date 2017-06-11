package sunseeker.telemetry.ui;

import org.junit.Before;
import org.junit.Test;
import sunseeker.telemetry.ui.frame.LiveDataFrame;

import javax.swing.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created by aleccarpenter on 6/10/17.
 */
public class LiveDataFrameTest {
    private LiveDataFrame subject;

    @Before
    public void setup() {
        subject = new LiveDataFrame();
    }

    @Test
    public void should_notBeVisible() {
        assertFalse(subject.isVisible());
    }

    @Test
    public void should_beFullscreen() {
        assertThat(subject.getExtendedState(), is(JFrame.MAXIMIZED_BOTH));
    }

    @Test
    public void should_quitOnClose() {
        assertThat(subject.getDefaultCloseOperation(), is(JFrame.EXIT_ON_CLOSE));
    }

    @Test
    public void should_haveATitle() {
        assertThat(subject.getTitle(), not(is("")));
    }
}