package sunseeker.telemetry.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created by aleccarpenter on 6/10/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LiveDataTest {
    private LiveData subject;

    private XChartPanel mockChartPanel;

    @Before
    public void setup() {
        mockChartPanel = new XChartPanel(new XYChart(10, 20));

        subject = new LiveData(mockChartPanel);
    }

    @Test
    public void should_notBeVisible() {
        assertFalse(subject.isVisible());
    }

    @Test
    public void should_beSizedProperlyHorizontally() {
        assertThat(subject.getWidth(), is(960));
    }

    @Test
    public void should_beSizedProperlyVertically() {
        assertThat(subject.getHeight(), is(720));
    }

    @Test
    public void should_notAllowItselfToBeResized() {
        assertFalse(subject.isResizable());
    }

    @Test
    public void should_quitOnClose() {
        assertThat(subject.getDefaultCloseOperation(), is(JFrame.EXIT_ON_CLOSE));
    }

    @Test
    public void should_haveATitle() {
        assertThat(subject.getTitle(), not(is("")));
    }

    @Test
    public void should_addChartPanelToGraphPanel() {
        assertThat(subject.getGraphPanel().getComponent(0), is(mockChartPanel));
    }
}