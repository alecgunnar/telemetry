package sunseeker.telemetry.ui.panel;

import com.google.common.collect.Lists;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import sunseeker.telemetry.data.LiveDataSource;
import sunseeker.telemetry.ui.table.SummaryTable;

import javax.swing.*;
import java.util.*;
import java.util.List;

/**
 * Created by aleccarpenter on 6/18/17.
 */
public class GraphPanel extends XChartPanel implements LiveDataSource.Subscriber, SummaryTable.Configurable {
    private XYChart chart;

    private final int MAX_DATA_POINTS;

    private Map<String, Double> lastValueReceived = new HashMap<>();
    private Map<String, List<Double>> valuesDisplayedOnGraph = new HashMap<>();
    private final List<Double> timeScaleDataValues = new ArrayList<>();

    public GraphPanel(XYChart chart, int maxDataPoints) {
        super(chart);

        this.chart = chart;

        MAX_DATA_POINTS = maxDataPoints;

        for (int i = 0; i < maxDataPoints; i++) {
            timeScaleDataValues.add(i + 1.0);
        }

        Thread updateChart = new Thread() {
            public void run() {
            while (true) {
                for (String key : valuesDisplayedOnGraph.keySet()) {
                    List<Double> values = valuesDisplayedOnGraph.get(key);

                    values.add(lastValueReceived.get(key));

                    if (values.size() > MAX_DATA_POINTS)
                        values.remove(0);

                    chart.updateXYSeries(key, timeScaleDataValues, Lists.reverse(values), null);
                }

                SwingUtilities.invokeLater(() -> {
                    revalidate();
                    repaint();
                });

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) { break; }
            }
            }
        };

        updateChart.start();
    }

    @Override
    public void receiveData(Map<String, Double> values) {
        for (String key : values.keySet()) {
            if (!valuesDisplayedOnGraph.containsKey(key))
                valuesDisplayedOnGraph.put(key, new ArrayList<>(timeScaleDataValues));

            lastValueReceived.put(key, values.get(key));

            try {
                chart.addSeries(key, timeScaleDataValues, valuesDisplayedOnGraph.get(key));
            } catch (IllegalArgumentException e) { }
        }
    }

    @Override
    public void receiveError(String msg) {
        // Not doing anything with this right now...
    }

    @Override
    public void toggleField(String name) {

    }
}
