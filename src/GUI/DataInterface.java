/**
* Sunseeker Telemety
*
* Telemetry 2016
*
* @author Kai Gray <kai.a.gray@wmich.edu>
*/


package sunseeker.telemetry;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.BorderFactory;

import java.awt.Component;

class DataInterface extends JPanel{
/*
	private ParseData dataIn;

	//Data Fields
	private JLabel voltage;
	private JLabel current;
	private JLabel batTemp;
	private JLabel m_1Temp;
	private JLabel m_2Temp;
	private JLabel avgVolt;
	private JLabel avgCurr;

	//Data Labels
	final String VOLTAGE = "Voltage:";
	final String CURRENT = "Bus Current:";
	final String BATTEMP = "Battery Temperature";
	final String M_1TEMP = "Motor 1 Temperature";
	final String M_2TEMP = "Motor 2 Temperature";
	final String AVGVOLT = "Average Voltage";
	final String AVGCURR = "Average Current";

	//Layout
    private SpringLayout layout;
    private Component prevLabel = this;
    private Component prevValue = this;


	DataInterface () {
		dataIn = new ParseData();

		layout = new SpringLayout();

        setLayout(layout);

		voltage	= new JLabel(VOLTAGE);
		current = new JLabel(CURRENT);
		batTemp = new JLabel(BATTEMP);
		m_1Temp = new JLabel(M_1TEMP);
		m_2Temp = new JLabel(M_2TEMP);
		avgVolt = new JLabel(AVGVOLT);
		avgCurr = new JLabel(AVGCURR);

		addDataField(voltage, new JLabel(20));
		addDataField(current, new JLabel(20));
		addDataField(batTemp, new JLabel(20));
		addDataField(m_1Temp, new JLabel(20));
		addDataField(m_2Temp, new JLabel(20));
		addDataField(avgVolt, new JLabel(20));
		addDataField(avgCurr, new JLabel(20));
	}


	private void addDataField (JLabel label, JLabel value) {
		add(label);
		add(value);

        labelConstraint(label, prevLabel);
        valueConstraint(value, prevValue);

        prevLabel = label;
        prevValue = value;
    }

    private void labelConstraint (Component comp, Component above) {
        int paddTop = paddingTop(above);

        layout.putConstraint(SpringLayout.NORTH, comp, paddTop, SpringLayout.NORTH, above);
        layout.putConstraint(SpringLayout.WEST, comp, 10, SpringLayout.WEST, this);
    }

    private void valueConstraint (Component comp, Component above) {
        int paddTop = paddingTop(above);

        layout.putConstraint(SpringLayout.NORTH, comp, paddTop, SpringLayout.NORTH, above);
        layout.putConstraint(SpringLayout.EAST, comp, -10, SpringLayout.EAST, this);
    }

    private int paddingTop (Component comp) {
        int paddTop = 20;

        if (comp != this)
            paddTop *= 2;

        return paddTop;
    }
    
    private String reduce (double val) {
        return String.format("%.2f",val);
    }
*/
}