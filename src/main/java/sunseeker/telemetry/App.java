package sunseeker.telemetry;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import sunseeker.telemetry.data.LiveDataSource;
import sunseeker.telemetry.data.PseudoRandomDataSource;
import sunseeker.telemetry.data.parser.Parser;
import sunseeker.telemetry.data.parser.SixteenCarParser;
import sunseeker.telemetry.ui.LiveDataFrame;
import sunseeker.telemetry.ui.panel.GraphPanel;

import javax.swing.SwingUtilities;

public class App {
    public static final int MAX_DATA_POINTS = 20;

    private LiveDataFrame liveDataFrameFrame;

    public static void main(String[] args) {
        XYChart chart = new XYChartBuilder().title("Live Data")
                .width(920)
                .height(360)
                .xAxisTitle("Time")
                .yAxisTitle("Measurement")
                .build();

        chart.getStyler().setXAxisTicksVisible(false);

        GraphPanel chartPanel = new GraphPanel(chart, MAX_DATA_POINTS);
        LiveDataFrame liveDataFrameFrame = new LiveDataFrame(chartPanel);

        Parser parser = new SixteenCarParser();
        LiveDataSource liveDataSourceSource = new PseudoRandomDataSource(parser);
        liveDataSourceSource.subscribe(chartPanel);

        Thread dataThread = new Thread() {
            public void run() {
                try {
                    liveDataSourceSource.start();
                } catch (LiveDataSource.CannotStartException e) {
                    e.printStackTrace();
                }
            }
        };

        dataThread.start();

        SwingUtilities.invokeLater(() -> {
            new App(liveDataFrameFrame).run();
        });
    }

    public App(LiveDataFrame liveDataFrameFrame) {
        this.liveDataFrameFrame = liveDataFrameFrame;
    }

    public void run() {
        liveDataFrameFrame.setVisible(true);
    }
}
