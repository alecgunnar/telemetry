/**
* Sunseeker Telemety
*
* Telemetry 2016
*
* @author Kai Gray <kai.a.gray@wmich.edu>
*/

package sunseeker.telemetry;


import javax.swing.JLayeredPane;

import java.awt.Graphics;
import java.awt.Graphics2D;

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

	private Color pointColor = new Color(10,10,10,100);
	private Color lineColor = new Color(100, 100, 100, 100);

	private int height;
	private int width;

	DrawData () {
		dataPoints = new ArrayList<Double>();
	}

	DrawData (String name) {
		this.name = name;
		dataPoints = new ArrayList<Double>();
	}


	public String getName () {
		return this.name;
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

	public void addPoint (double point) {
		this.dataPoints.add(point);
		invalidate();
		repaint();
	}

	@Override
	protected void paintComponent () {
		int i = 0;
		int xStr = 0, yStr = 0, xEnd = 0, yEnd = 0;

		super.paintComponent(g);
		Graphics2D graph = (Graphics2D) g;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		List<Point> graphPoints = new ArrayList<>();
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