package sunseeker.telemetry.ui.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by aleccarpenter on 6/23/17.
 */
public class ConfigTable extends JTable {
    public ConfigTable() {
        super(new DefaultTableModel(new String[] {
                "Test"
        }, 0));
    }
}
