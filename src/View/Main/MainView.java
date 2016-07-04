/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLayeredPane;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Component;

class MainView extends AbstractMainView {
    protected SpringLayout layout;

    protected Container contentPane;
    protected JLayeredPane layeredPane;

    protected AbstractGraphPanel graphPanel;
    protected AbstractDataSelectPanel dataSelectPanel;
    protected AbstractLiveDataPanel liveDataPanel;

    protected int depth = 1;

    public MainView () {
        /*
         * Only need to build once
         */
        if (layout != null) return;

        /*
         * The app should not quit when this view is closed
         */
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /*
         * The minimum size of the window
         */
        setSize(new Dimension(VIEW_WIDTH, VIEW_HEIGHT));

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
         * Just some other things...
         */
        contentPane = getContentPane();

        configureLayeredPane();
    }

    public void showView () {
        /*
         * When the window is opened draw the graph
         */
        if (graphPanel != null)
            graphPanel.repaint();

        super.showView();
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
            VIEW_WIDTH,
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
        panel.setBounds(
            (VIEW_WIDTH / 2) + (PADDING / 2),
            AbstractGraphPanel.PANEL_HEIGHT + PADDING,
            (VIEW_WIDTH / 2)  - (PADDING / 2),
            VIEW_HEIGHT - (AbstractGraphPanel.PANEL_HEIGHT + AXIS_PADDING)
        );

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
        panel.setBounds(
            0,
            AbstractGraphPanel.PANEL_HEIGHT + PADDING,
            (VIEW_WIDTH / 2) - (PADDING / 2),
            VIEW_HEIGHT - (AbstractGraphPanel.PANEL_HEIGHT + AXIS_PADDING) + 1
        );

        /*
         * Add the panel to the view
         */
        layeredPane.add(liveDataPanel, new Integer(depth++));
    }

    public void useLinePanels (AbstractLinePanel[] panels) {
        for (AbstractLinePanel panel : panels) {
            panel.setBounds(
                AbstractGraphPanel.AXIS_INSET, 0,
                VIEW_WIDTH - AbstractGraphPanel.FULL_INSET,
                AbstractGraphPanel.PANEL_HEIGHT
            );

            layeredPane.add(panel, new Integer(depth++));
        }
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
}