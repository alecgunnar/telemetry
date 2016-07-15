/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.util.List;
import java.util.ArrayList;

public class LinePanel extends AbstractLinePanel {
    /*
     * The data type being displayed
     */
    protected DataTypeInterface data;

    /*
     * Our artist
     */
    protected Graphics2D artist;

    /*
     * List of points currently being displayed
     */
    protected ArrayList<Integer> points;

    public LinePanel (DataTypeInterface data) {
        this.data = data;

        /*
         * Need to see the other lines and graph
         */
        setOpaque(false);

        /*
         * Initialize the collection of points
         */
        points = new ArrayList<Integer>();

        /*
         * To draw the segments, we need a point to start from
         */
        points.add(AbstractGraphPanel.getYPos(0));
    }

    public void putValue (double value) {
        /*
         * If we have too many points, remove one
         */
        if (points.size() > AbstractGraphPanel.MAX_POINTS)
            points.remove(0);

        points.add(AbstractGraphPanel.getYPos(value));
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        artist = (Graphics2D) g;

        /*
         * Only when this line is active should it be drawn
         */
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
         * Run through the data to be displayed
         */
        int index = 0,
            prev  = 0;

        for (Integer point : points) {
            if (index > 0) {
                artist.drawLine(
                    (index - 1) * AbstractGraphPanel.X_AXIS_SCALE,
                    prev,
                    index * AbstractGraphPanel.X_AXIS_SCALE,
                    point
                );
            }

            prev = point;

            index++;
        }
    }
}
