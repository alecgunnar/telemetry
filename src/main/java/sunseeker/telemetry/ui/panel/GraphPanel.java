package sunseeker.telemetry.ui.panel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import sunseeker.telemetry.data.LiveDataSource;
import sunseeker.telemetry.ui.table.SummaryTable;

import javax.swing.*;
import java.util.*;

/**
 * Created by aleccarpenter on 6/18/17.
 */
public class GraphPanel extends XChartPanel implements LiveDataSource.Subscriber, SummaryTable.Configurable {
    private XYChart chart;

    private final int MAX_DATA_POINTS;

    private Map<String, Double> current = new HashMap<>();
    private Map<String, List<Double>> data = new HashMap<>();
    private final List<Double> filler = new ArrayList<>();

    public GraphPanel(XYChart chart, int maxDataPoints) {
        super(chart);

        this.chart = chart;
        MAX_DATA_POINTS = maxDataPoints;

        for (int i = 0; i < maxDataPoints; i++) {
            filler.add(i + 1.0);
        }

        Thread updateChart = new Thread() {
            public void run() {
            while (true) {
                for (String key : data.keySet()) {
                    List<Double> values = data.get(key);

                    values.remove(0);
                    values.add(current.get(key));

                    chart.updateXYSeries(key, filler, values, null);
                }

                SwingUtilities.invokeLater(() -> {
                    revalidate();
                    repaint();
                });

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    break;
                }
            }
            }
        };

        updateChart.start();
    }

    @Override
    public void receiveData(Map<String, Double> values) {
        for (String key : values.keySet()) {
            if (!data.containsKey(key)) {
                List<Double> newDataList = new ArrayList<>();
                newDataList.addAll(filler);

                data.put(key, newDataList);
            }

            current.put(key, values.get(key));

            try {
                chart.addSeries(key, filler, data.get(key));
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
