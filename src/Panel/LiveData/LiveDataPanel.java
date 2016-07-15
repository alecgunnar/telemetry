/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Container;

public class LiveDataPanel extends AbstractLiveDataPanel {
    final public static int ROW_HEIGHT = 30;

    protected AbstractDataTypeCollection types;

    protected JTable table;
    protected DefaultTableModel model;

    public LiveDataPanel () {
        /*
         * Create the titled border for the panel
         */
        TitledBorder border = BorderFactory.createTitledBorder(" Live Data ");

        setBorder(border);

        /*
         * Need a layout for the tables to show up
         */
        setLayout(new BorderLayout());

        /*
         * Create the table to display the data
         */
        createTable();
    }

    public void refresh () {
        int row = model.getRowCount() - 1;

        for (; row >= 0; row--)
            model.removeRow(row);

        addRows();

        model.fireTableDataChanged();
    }

    protected void createTable () {
        /*
         * Remove any existing tables
         */
        removeAll();

        /*
         * Create the table will headers
         */
        model = new DefaultTableModel(new String[] {
            "Data", "Minimum", "Current", "Maximum"
        }, 0);

        table = new JTable(model);

        table.setRowHeight(ROW_HEIGHT);

        /*
         * Each available data type will have its own row
         */
        addRows();

        /*
         * Create the scroll pane without a border
         */
        JScrollPane scrollable = new JScrollPane(table);

        scrollable.setBorder(BorderFactory.createEmptyBorder());

        /*
         * Add the scroll pane with the table to the panel
         */
        add(scrollable, BorderLayout.CENTER);
    }

    protected void addRows () {
        if (types != null) {
            for (DataTypeInterface type : types) {
                if (type.isEnabled()) {
                    model.addRow(new Object[] {
                        type.getType() + " (" + type.getUnits() + ")",
                        (float) type.getMinimumValue(),
                        (float) type.getCurrentValue(),
                        (float) type.getMaximumValue()
                    });
                }
            }
        }
    }
}
