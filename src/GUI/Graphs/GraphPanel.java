/**
* Sunseeker Telemety
*
* Telemetry 2016
*
* @author Kai Gray <kai.a.gray@wmich.edu>
*/

package sunseeker.telemetry;


import javax.swing.JPanel;
import javax.swing.JLayeredPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

import java.util.ArrayList;
import java.util.List;

class GraphPanel extends JLayeredPane {

	private List<Double> dataPoints;

	final int HEIGHT = 450;
	final int WIDTH  = 950;

	final int PADDING  = 10;
	final int LPADDING = 10;

	final private static Stroke GRAPH_STROKE = new BasicStroke(2f);
	final public int RADIUS = 3;
	final public int CONST = 10;

	final int numberYDivisions = 20;
	private int width = 0, height = 0;
	private String str = new String();

	final Color gridColor = new Color(200, 200, 200, 200);

	//graph scale
	private double xScale;
	private double yScale;


	GraphPanel () {
		dataPoints = new ArrayList<Double>();
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);

		this.setPreferredSize(new Dimension(getWidth(), getHeight()));
	}


	public List<Double> getDataPoints () {
		return this.dataPoints;
	}

	protected void setDataPoints (List<Double> dataPoints) {
		this.dataPoints = dataPoints;
	}

	private double getMax () {
		double m = 0;

        for(int i = 0; i < this.dataPoints.size(); i++) {
            if(this.dataPoints.get(i) > m)
                m= this.dataPoints.get(i);
        }

        return m;
    }

	protected void setHeight (int height) {
		if(height > HEIGHT) {
			this.height = (height - PADDING - LPADDING);
		}
		else{
			this.height = (HEIGHT - PADDING - LPADDING);
		}
			
	}

	protected void setWidth (int width) {
		if(height > HEIGHT) {
			this.width = (width - PADDING - LPADDING);
		}
		else{
			this.width = (WIDTH - PADDING - LPADDING);
		}
	}

	public int getHeight () {
		return this.height;
	}

	public int getWidth () {
		return this.width;
	}

	protected void setXScale (double x) {
		this.xScale = x;
	}

	public double getXScale () {
		return this.xScale;
	}

	protected void setYScale (double y) {
		this.yScale = y;
	}

	public double getYScale () {
		return this.yScale;
	}


	//Draw Graph Background
	@Override
	protected void paintComponent (Graphics g) {
		int i = 0;
		int xStr = 0, yStr = 0, xEnd = 0, yEnd = 0;

		super.paintComponent(g);
		Graphics2D graph = (Graphics2D) g;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		setXScale((width - PADDING - LPADDING) / (dataPoints.size() -1));
		setYScale(((getHeight() - PADDING - LPADDING) / this.getMax())/2);

        //fill white background
		graph.setColor(Color.WHITE);
		graph.fillRect((PADDING + LPADDING), PADDING, getWidth() - (2 * PADDING) - LPADDING, getHeight()  - (2 * PADDING) - LPADDING);
        //switch to black for axis
		graph.setColor(Color.BLACK);

        //draw y hash and grid
		for (i = 0; i < numberYDivisions + 1; i++) {

			xStr = PADDING + LPADDING;
			xEnd = RADIUS + xStr;
			yStr = getHeight() - ((i * (getHeight() - PADDING * 2 - LPADDING)) / numberYDivisions + xStr);
            //yEnd = yStr;
			if (this.dataPoints.size() > 0) {
				graph.setColor(gridColor);
				graph.drawLine(PADDING + LPADDING + 1 + RADIUS, yStr, width - PADDING, yStr);
				graph.setColor(Color.BLACK);
				String yLabel = ((((int) (this.getMax() * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "");
				FontMetrics metrics = graph.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				graph.drawString(yLabel, xStr - labelWidth - 5, yStr + (metrics.getHeight() / 2) - 3);
			}
			graph.drawLine(xStr, yStr, xEnd, yStr);
		}

        //draw x hash and grid
		for (i = 0; i < this.dataPoints.size(); i++) {
			if (this.dataPoints.size() > 1) {
				xStr = (i * (width - PADDING * 2 - LPADDING)) / (this.dataPoints.size() - 1) + PADDING + LPADDING;
                    //int xEnd = xStr;
				yStr = height - PADDING - LPADDING;
				yEnd = yStr - RADIUS;
				if ((i % ((int) ((dataPoints.size() / 20.0)) + 1)) == 0) {
					graph.setColor(gridColor);
					graph.drawLine(xStr, getHeight() - PADDING - LPADDING - 1 - RADIUS, xStr, PADDING);
					graph.setColor(Color.BLACK);
					String xLabel = i + "";
					FontMetrics metrics = graph.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					graph.drawString(xLabel, xStr - labelWidth / 2, yStr + metrics.getHeight() + 3);
				}
				graph.drawLine(xStr, yStr, xStr, yEnd);
			}
		}

        // create x and y axes 
		graph.drawLine(PADDING + LPADDING, getHeight() - PADDING - LPADDING, PADDING + LPADDING, PADDING);
		graph.drawLine(PADDING + LPADDING, getHeight() - PADDING - LPADDING, getWidth() - PADDING, getHeight() - PADDING - LPADDING);
	}

}