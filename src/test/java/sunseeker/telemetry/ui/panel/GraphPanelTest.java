package sunseeker.telemetry.ui.panel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchart.XYChart;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by aleccarpenter on 6/18/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class GraphPanelTest {
    private GraphPanel subject;

    @Mock
    private XYChart mockChart;

    private final int MAX_NUM_DATA_POINTS = 3;

    @Before
    public void setup() {
        subject = new GraphPanel(mockChart, MAX_NUM_DATA_POINTS);
    }

    @Test
    public void should_addDataToSeries_withIncrementalCounter_andExistingDataRetained_whenDataIsReceived() {
        Map<String, Double> data = new HashMap<>();

        data.put("test", 1.23);
        subject.receiveData(data);
        verify(mockChart, times(1)).updateXYSeries("test", Arrays.asList(1.0), Arrays.asList(1.23), null);

        data.put("test", 2.34);
        subject.receiveData(data);
        verify(mockChart, times(2)).updateXYSeries("test", Arrays.asList(1.0, 2.0), Arrays.asList(1.23, 2.34), null);
    }

    @Test
    public void should_addDataToSeries_withSameCounterValue_whenDataIsReceived() {
        Map<String, Double> data = new HashMap<>();

        data.put("hello", 1.23);
        data.put("world", 2.34);

        subject.receiveData(data);

        verify(mockChart, times(1)).updateXYSeries("hello", Arrays.asList(1.0), Arrays.asList(1.23), null);
        verify(mockChart, times(1)).updateXYSeries("world", Arrays.asList(1.0), Arrays.asList(2.34), null);
    }

    @Test
    public void should_addSeriesIfItDoesNotExist_whenDataIsReceived() {
        Map<String, Double> data = new HashMap<>();

        when(mockChart.updateXYSeries("hello", Arrays.asList(1.0), Arrays.asList(1.23), null)).thenThrow(new IllegalArgumentException());

        data.put("hello", 1.23);
        subject.receiveData(data);

        verify(mockChart, times(1)).addSeries("hello", Arrays.asList(1.0), Arrays.asList(1.23));
    }

    @Test
    public void should_dropOldData_ifMaxNumberOfDataPointsIsReached_whenDataIsReceived() {
        Map<String, Double> data = new HashMap<>();

        data.put("hello", 1.0);
        subject.receiveData(data);

        data.put("hello", 2.0);
        subject.receiveData(data);

        data.put("hello", 3.0);
        subject.receiveData(data);

        data.put("hello", 4.0);
        subject.receiveData(data);

        verify(mockChart, times(4)).updateXYSeries("hello", Arrays.asList(2.0, 3.0, 4.0), Arrays.asList(2.0, 3.0, 4.0), null);
    }

    @Test
    public void should_revalidateTheChart_whenDataIsReceived() {
        subject.receiveData(new HashMap<>());

        // TODO: Figure out how to test this...
    }
}