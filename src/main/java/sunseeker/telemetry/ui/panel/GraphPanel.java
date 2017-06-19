package sunseeker.telemetry.ui.panel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import sunseeker.telemetry.data.LiveDataSource;

import javax.swing.*;
import java.util.*;

/**
 * Created by aleccarpenter on 6/18/17.
 */
public class GraphPanel extends XChartPanel implements LiveDataSource.Subscriber {
    private XYChart chart;

    private final int MAX_DATA_POINTS;

    private double counter = 1.0;

    private Map<String, List<Double>> data = new HashMap<>();
    private List<Double> times = new ArrayList<>();

    public GraphPanel(XYChart chart, int maxDataPoints) {
        super(chart);

        this.chart = chart;
        MAX_DATA_POINTS = maxDataPoints;
    }

    @Override
    public void receiveData(Map<String, Double> values) {
        times.add(counter++);

        if (times.size() > MAX_DATA_POINTS) {
            times.remove(0);
        }

        for (String key : values.keySet()) {
            if (!data.containsKey(key)) {
                data.put(key, new ArrayList<>());
            }

            List<Double> data = this.data.get(key);
            data.add(values.get(key));

            if (data.size() > MAX_DATA_POINTS) {
                data.remove(0);
            }

            try {
                chart.updateXYSeries(key, times, data, null);
            } catch (IllegalArgumentException e) {
                chart.addSeries(key, times, data);
            }
        }

        SwingUtilities.invokeLater(() -> {
            revalidate();
            repaint();
        });
    }

    @Override
    public void receiveError(String msg) {
        // Not doing anything with this right now...
    }
}
