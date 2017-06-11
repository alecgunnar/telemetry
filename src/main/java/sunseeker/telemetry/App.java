package sunseeker.telemetry;

import sunseeker.telemetry.data.LiveData;
import sunseeker.telemetry.data.Serial;
import sunseeker.telemetry.data.parser.SixteenCarParser;
import sunseeker.telemetry.data.serial.IdentifierFactory;
import sunseeker.telemetry.data.serial.configurator.ModemConfigurator;
import sunseeker.telemetry.ui.frame.LiveDataFrame;

import javax.swing.*;

import gnu.io.*;
import sunseeker.telemetry.util.SleeperUtility;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

public class App implements Runnable, LiveData.Subscriber {
    private LiveDataFrame liveDataFrameFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());
    }

    public App() {
        liveDataFrameFrame = new LiveDataFrame();
    }

    @Override
    public void run() {
        liveDataFrameFrame.setVisible(true);

//        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
//
//        while (portEnum.hasMoreElements()) {
//            CommPortIdentifier portIdentifier = portEnum.nextElement();
//            System.out.println("\t" + portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
//        }

        String portName = "/dev/cu.usbserial-FTDGRXEA";

        LiveData data = new Serial(portName, new IdentifierFactory(), new ModemConfigurator(new SleeperUtility()), new SixteenCarParser());

        data.subscribe(this);

        try {
            data.start();
        } catch (LiveData.CannotStartException e) {
            e.printStackTrace();
        }
    }

    String getPortTypeName ( int portType )
    {
        switch ( portType )
        {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }

    public LiveDataFrame getLiveDataFrameFrame() {
        return liveDataFrameFrame;
    }

    @Override
    public void receiveData(Map<String, Double> values) {
        for (String key : values.keySet()) {
            System.out.println(key + ": "  + values.get(key));
        }
    }

    @Override
    public void receiveError(String msg) {
        System.out.println(msg);
    }
}
