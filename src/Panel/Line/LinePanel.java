/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

class LinePanel extends AbstractLinePanel {
    protected int width = 0;
    protected int height = 0;

    protected Graphics2D artist;

    protected boolean active = true;

    protected DataCollectionInterface data;

    public LinePanel (DataCollectionInterface data) {
        /*
         * This is where we will be getting the data from
         */
        this.data = data;

        /*
         * Need to see the other lines and graph
         */
        setOpaque(false);
    }

    public void paintComponent (Graphics g) {
        width = getWidth();
        height = getHeight();

        super.paintComponent(g);

        artist = (Graphics2D) g;

        /*
         * Only when this line is active should it be drawn
         */
        if (data.isEnabled())
            drawSegments();
    }

    protected void drawSegments () {
        /*
         * We need a colored line with the required thickness
         */
        artist.setStroke(new BasicStroke(
            LINE_WIDTH,
            BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_MITER
        ));

        artist.setColor(data.getColor());

        /*
         * Get the data to be displayed
         */
        double[] dataPoints = data.getData();

        /*
         * Only render if data is present
         */
        if (data.count() > 0) {
            /*
             * Start rendering segments
             */
            double prev = 0;
            int i, point;

            for (i = 0; i < data.count() && i < AbstractGraphPanel.MAX_POINTS; i++) {
                point = GraphPanel.getYPos(dataPoints[i]);

                drawSegment(
                    (i - 1) * AbstractGraphPanel.X_AXIS_SCALE,
                    (int) prev,
                    i * AbstractGraphPanel.X_AXIS_SCALE,
                    point
                );

                prev = point;
            }
        }
    }

    protected void drawSegment (int x1, int y1, int x2, int y2) {
        artist.drawLine(
            x1, y1,
            x2, y2
        );
    }

    public void receive (String channel, Object dat) {
        if (channel.equals(data.getType() + "_update"))
            this.repaint();
    }
}
