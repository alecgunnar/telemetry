package sunseeker.telemetry;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import sunseeker.telemetry.ui.LiveData;
import sunseeker.telemetry.ui.panel.GraphPanel;

import javax.swing.*;

public class App {
    private LiveData liveDataFrame;

    public static void main(String[] args) {
        XYChart chart = new XYChartBuilder().title("Data")
                .width(920)
                .height(360)
                .xAxisTitle("Time")
                .yAxisTitle("Measurement")
                .build();

        XChartPanel chartPanel = new GraphPanel(chart);
        LiveData liveDataFrame = new LiveData(chartPanel);

        SwingUtilities.invokeLater(() -> {
            new App(liveDataFrame).run();
        });
    }

    public App(LiveData liveDataFrame) {
        this.liveDataFrame = liveDataFrame;
    }

    public void run() {
        liveDataFrame.setVisible(true);
    }
}
