package sunseeker.telemetry.ui.panel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import sunseeker.telemetry.data.LiveData;

import java.util.Map;

/**
 * Created by aleccarpenter on 6/18/17.
 */
public class GraphPanel extends XChartPanel implements LiveData.Subscriber {
    public GraphPanel(XYChart chart) {
        super(chart);
    }

    @Override
    public void receiveData(Map<String, Double> values) {

    }

    @Override
    public void receiveError(String msg) {

    }
}
