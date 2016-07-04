/**
* Sunseeker Telemety
*
* Telemetry 2016
*
* @author Kai Gray <kai.a.gray@wmich.edu>
*/

package sunseeker.telemetry;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.SpringLayout;
import javax.swing.JCheckBox;

import java.util.ArrayList;
import java.util.List;


class GraphInterface extends JPanel {

	private static GraphPanel panel;

	private static DrawData volt;
	private static DrawData current;

	//private JCheckBox vSelect;
	//private JCheckBox cSelect;

	private boolean on = true;

	public static double dumbyVal = 0;

	private SpringLayout layout;

	GraphInterface () {

		layout = new SpringLayout();

		this.setPreferredSize(new Dimension(950, 450));

		panel = new GraphPanel();
		panel.setLayout(layout);

		volt = new DrawData("Voltage");
		volt.setLineColor(100,10,10,100);
		panel.add(volt);

		layout.putConstraint(SpringLayout.NORTH, volt, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, volt, 0, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, volt, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, volt, 0, SpringLayout.WEST, panel);

		current = new DrawData("Current");
		volt.setLineColor(10,100,10,100);
		panel.add(current);

		layout.putConstraint(SpringLayout.NORTH, current, 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.SOUTH, current, 0, SpringLayout.SOUTH, panel);
		layout.putConstraint(SpringLayout.EAST, current, 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, current, 0, SpringLayout.WEST, panel);
	}

	public void update () {
		panel.setDataPoints(volt.getDataPoints());

		volt.addPoint(dumbyVal);
		current.addPoint(dumbyVal - 5);

		cycleData();

	}

	public static void cycleData () {
		dumbyVal += 10;
	}


}