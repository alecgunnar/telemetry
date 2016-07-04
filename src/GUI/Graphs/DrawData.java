/**
* Sunseeker Telemety
*
* Telemetry 2016
*
* @author Kai Gray <kai.a.gray@wmich.edu>
*/

package sunseeker.telemetry;


import javax.swing.JLayeredPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

import java.util.ArrayList;
import java.util.List;

class DrawData extends JLayeredPane {

	protected List<Double> dataPoints;

	private String name;

	final int HEIGHT = 450;
	final int WIDTH  = 950;

	final int PADDING  = 10;
	final int LPADDING = 10;

	final static Stroke GRAPH_STROKE = new BasicStroke(2f);
	final public int RADIUS = 3;
	final public int CONST = 10;

	private int xPoint = 0, yPoint = 0;

	private Color pointColor = new Color(10,10,10,100);
	private Color lineColor = new Color(100, 100, 100, 100);

	private int height;
	private int width;

	//graph scale
	private double xScale;
	private double yScale;

	DrawData () {
		this.name = "Default";
		dataPoints = new ArrayList<Double>();

		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);

		this.setPreferredSize(new Dimension(getWidth(), getHeight()));
	}

	DrawData (String name) {
		this.name = name;
		dataPoints = new ArrayList<Double>();

		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);

		this.setPreferredSize(new Dimension(getWidth(), getHeight()));
	}


	public String getName () {
		return this.name;
	}

	public List<Double> getDataPoints () {
		return this.dataPoints;
	}

	public int getHeight () {
		return this.height;
	}

	public int getWidth () {
		return this.width;
	}

	private void setHeight (int h) {
		this.height = h;
	}

	private void setWidth (int w) {
		this.width = w;
	}

	protected void setLineColor (int a, int b, int c, int d) {
		lineColor = new Color(a,b,c,d);
	}

	public Color getLineColor () {
		return this.lineColor;
	}

	public void addPoint (double point) {
		this.dataPoints.add(point);
		invalidate();
		repaint();
	}

	public double getMax() {
		double m = 0;

        for(int i = 0; i < this.dataPoints.size(); i++) {
            if(this.dataPoints.get(i) > m)
                m = this.dataPoints.get(i);
        }

        return m;
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

	//draw line components
	@Override
	protected void paintComponent (Graphics g) {
		int i = 0;
		int xStr = 0, yStr = 0, xEnd = 0, yEnd = 0;

		super.paintComponent(g);
		Graphics2D graph = (Graphics2D) g;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//transparent background
		graph.setBackground(new Color(0, 0, 0, 0x33));

		List<Point> graphPoints = new ArrayList<Point>();
		for(i = 0; i < dataPoints.size(); i++){
			xEnd = (int) (i * getXScale() + PADDING + LPADDING);
			yEnd = (int) ((this.getMax() - dataPoints.get(i)) * getYScale() + PADDING);
			graphPoints.add(new Point(xEnd, yEnd));
		}

		//re-creates all previous dataPoints and lines during update
		Stroke oldStroke = graph.getStroke();
		graph.setColor(lineColor);
		graph.setStroke(GRAPH_STROKE);
		for (i = 0; i < graphPoints.size() - 1; i++) {
			xStr = graphPoints.get(i).x;
			yStr = graphPoints.get(i).y;
			xEnd = graphPoints.get(i + 1).x;
			yEnd = graphPoints.get(i + 1).y;
			graph.drawLine(xStr, yStr, xEnd, yEnd);
		}

		//creates point
		graph.setStroke(oldStroke);
		graph.setColor(pointColor);
		for (i = 0; i < graphPoints.size(); i++) {
            //point location
			int x = graphPoints.get(i).x - RADIUS / 2;
			int y = graphPoints.get(i).y - RADIUS / 2;
            //point size
			graph.fillOval(x, y, RADIUS, RADIUS);
		}
	}
}