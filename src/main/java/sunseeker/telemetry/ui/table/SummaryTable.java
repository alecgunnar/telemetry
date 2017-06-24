package sunseeker.telemetry.ui.table;

import sunseeker.telemetry.data.LiveDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aleccarpenter on 6/23/17.
 */
public class SummaryTable extends JTable implements LiveDataSource.Subscriber {
    private Map<String, Model> models;

    private Configurable configurable;

    public SummaryTable() {
        super(new DefaultTableModel(new String[] {
                "Field", "Current", "Minimum", "Maximum"
        }, 0));

        models = new HashMap<>();

        setRowHeight(30);
    }

    public void configure(Configurable configurable) {
        this.configurable = configurable;
    }

    @Override
    public void receiveData(Map<String, Double> values) {
        for (String key : values.keySet()) {
            if (!models.containsKey(key)) {
                models.put(key, new Model(key));
            }

            updateModel(key, values.get(key));
        }

        refresh();
    }

    @Override
    public void receiveError(String msg) { }

    private void updateModel(String key, double value) {
        Model model = models.get(key);
        model.setCurrentValue(value);
    }

    private void refresh() {
        DefaultTableModel model = (DefaultTableModel) getModel();

        model.setNumRows(0);

        for (Model dataModel : models.values())
            model.addRow(dataModel.getAsArray());
    }

    public interface Configurable {
        void toggleField(String name);
    }

    private class Model {
        private String name;

        private double currentValue;

        private double minimumValue = Double.MAX_VALUE;

        private double maximumValue = Double.MIN_VALUE;

        Model(String name) {
            this.name = name;
        }

        void setCurrentValue(double value) {
            currentValue = value;

            if (value > maximumValue)
                maximumValue = value;

            if (value < minimumValue)
                minimumValue = value;
        }

        Object[] getAsArray() {
            return new Object[] {
                name, currentValue, minimumValue, maximumValue
            };
        }
    }
}
