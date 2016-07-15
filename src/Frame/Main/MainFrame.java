/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.data.DataSetInterface;
import org.wmich.sunseeker.telemetry.*;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLayeredPane;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map.Entry;

public class MainFrame extends AbstractMainFrame implements ActionListener {
    /*
     * Delay between data view updates (in milliseconds)
     */
    final protected int DATA_UPDATE_DELAY = 125;

    /*
     * Layout of the frame
     */
    protected SpringLayout layout;

    /*
     * Containers for the various panels
     */
    protected Container contentPane;
    protected JLayeredPane layeredPane;

    /*
     * The panels
     */
    protected AbstractGraphPanel graphPanel;
    protected AbstractDataSelectPanel dataSelectPanel;
    protected AbstractLiveDataPanel liveDataPanel;
    protected HashMap<String, AbstractLinePanel> linePanels;

    /*
     * To track the depth of the panels in the layered pane
     */
    protected int depth = 1;

    /*
     * Width used by the lower panels (live data and data selection)
     */
    protected int dataPanelsWidth = 0;

    /*
     * Used to update the data view periodically
     */
    protected Timer timer;

    /*
     * The most recent data set to have been posted
     */
    protected DataSetInterface dataSet;

    public MainFrame () {
        /*
         * Only need to build once
         */
        if (layout != null)
            return;

        /*
         * The app should not quit when this view is closed
         */
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        /*
         * The minimum size of the window
         */
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        /*
         * This is a fixed size window
         */
        setResizable(false);

        /*
         * Setup the layout
         */
        layout = new SpringLayout();
        setLayout(layout);

        /*
         * Create the map for the line panels
         */
        linePanels = new HashMap<String, AbstractLinePanel>();

        /*
         * Create the timer for the data updates
         */
        timer = new Timer(DATA_UPDATE_DELAY, this);

        /*
         * Set up the panes
         */
        contentPane = getContentPane();

        configureLayeredPane();
    }

    public void showFrame () {
        /*
         * When the window is opened draw the graph
         */
        if (graphPanel != null)
            graphPanel.repaint();

        super.showFrame();
    }

    public void useGraphPanel (AbstractGraphPanel panel) {
        /*
         * Remove the existing panel
         */
        if (graphPanel != null)
            layeredPane.remove(
                layeredPane.getIndexOf((Component) graphPanel)
            );

        graphPanel = panel;

        /*
         * Set the size of the graph
         */
        graphPanel.setBounds(
            0, 0,
            FRAME_WIDTH,
            AbstractGraphPanel.PANEL_HEIGHT
        );

        /*
         * Add the panel to the view
         */
        layeredPane.add(graphPanel, new Integer(depth++));
    }

    public void useDataSelectPanel (AbstractDataSelectPanel panel) {
        /*
         * Remove the existing panel
         */
        if (dataSelectPanel != null)
            layeredPane.remove(
                layeredPane.getIndexOf((Component) dataSelectPanel)
            );

        dataSelectPanel = panel;

        /*
         * Set the size and position of the panel
         */
        positionDataPanel(panel, (int) (FRAME_WIDTH * .3));

        /*
         * Add the panel to the view
         */
        layeredPane.add(dataSelectPanel, new Integer(depth++));
    }

    public void useLiveDataPanel (AbstractLiveDataPanel panel) {
        /*
         * Remove the existing panel
         */
        if (liveDataPanel != null)
            layeredPane.remove(
                layeredPane.getIndexOf((Component) liveDataPanel)
            );

        liveDataPanel = panel;

        /*
         * Set the size and position of the panel
         */
        positionDataPanel(panel, (int) (FRAME_WIDTH * .7));

        /*
         * Add the panel to the view
         */
        layeredPane.add(liveDataPanel, new Integer(depth++));
    }

    public void useDataSource (DataSourceInterface dataSource) {
        /*
         * We're going to be making some UI updates, lets not worry about data right now
         */
        timer.stop();

        /*
         * Remove the old line panels from the graph
         */
        removeLinePanels();

        /*
         * Create a list of the new panels to add
         */
        processLinePanels(dataSource.getTypes());

        /*
         * Add the new panels
         */
        addLinePanelsOverGraph();

        /*
         * All set, start loading in data
         */
        timer.start();
    }

    public void putData (DataSetInterface dataSet) {
        this.dataSet = dataSet;
    }

    public void actionPerformed (ActionEvent e) {
        Double value;
        int index;

        /*
         * Add the most recent data point for each line panel
         */
        for (Entry<String, AbstractLinePanel> entry : linePanels.entrySet()) {
            if ((value = dataSet.getValue(entry.getKey())) == null)
                value = new Double(0);

            entry.getValue().putValue(value);
        }

        graphPanel.repaint();
    }

    protected void configureLayeredPane () {
        layeredPane = new JLayeredPane();

        contentPane.add(layeredPane);

        /*
         * The layeredPane will appear "PADDING" pixels from the
         * top side of the view
         */
        layout.putConstraint(
            SpringLayout.NORTH, layeredPane,
            PADDING,
            SpringLayout.NORTH, contentPane
        );

        /*
         * The layeredPane will appear 500 pixels down the view
         */
        layout.putConstraint(
            SpringLayout.SOUTH, contentPane,
            PADDING,
            SpringLayout.SOUTH, layeredPane
        );

        /*
         * The layeredPane will appear "PADDING" pixels from the
         * left side of the view
         */
        layout.putConstraint(
            SpringLayout.WEST, layeredPane,
            PADDING,
            SpringLayout.WEST, contentPane
        );

        /*
         * The layeredPane will appear "PADDING" pixels from the
         * right side of the view
         */
        layout.putConstraint(
            SpringLayout.EAST, contentPane,
            PADDING,
            SpringLayout.EAST, layeredPane
        );
    }

    protected void positionDataPanel (AbstractPanel panel, int width) {
        width -= PADDING;

        int posX  = dataPanelsWidth;
        int posY  = AbstractGraphPanel.PANEL_HEIGHT + PADDING;

        /*
         * The precision required here is somewhat annoying...
         */
        panel.setBounds(
            posX, posY, width,
            (FRAME_HEIGHT - AXIS_PADDING) - (posY + AXIS_PADDING)
        );

        dataPanelsWidth += width;
    }

    protected void processLinePanels (String[][] types) {
        /*
         * Go through each type and create a line panel for it, then
         * add the panel to the map
         */
        for (String[] type : types)
            linePanels.put(type[0], new LinePanel(new DataType(type[0], type[1])));
    }

    protected void addLinePanelsOverGraph () {
        /*
         * For each of the line panels, position it on the graph
         */
        for (AbstractLinePanel panel : linePanels.values()) {
            panel.setBounds(
                AbstractGraphPanel.AXIS_INSET, 0,
                FRAME_WIDTH - AbstractGraphPanel.FULL_INSET,
                AbstractGraphPanel.PANEL_HEIGHT
            );

            layeredPane.add(panel, new Integer(depth++));
        }
    }

    protected void removeLinePanels () {
        /*
         * If some line panels exist, remove them
         */
        if (linePanels.size() > 0) {
            /*
             * Be sure to decrement the depth!
             */
            depth -= linePanels.size();

            linePanels.clear();
        }
    }
}