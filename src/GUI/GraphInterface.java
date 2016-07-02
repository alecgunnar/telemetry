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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import java.util.ArrayList;
import java.util.List;


class GraphInterface extends JPanel {

	private GraphPanel panel;
	private DrawData volt;
	private DrawData current;

	GraphInterface () {

		panel = new GraphPanel();

		volt = new DrawData("Voltage");
		current = new DrawData("Current");

	}
}