package sunseeker.telemetry;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import sunseeker.telemetry.ui.LiveData;

import javax.swing.*;

import gnu.io.*;

public class App implements Runnable {
    private LiveData liveDataFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());
    }

    public App() {
        liveDataFrame = new LiveData();
    }

    @Override
    public void run() {
        liveDataFrame.setVisible(true);

        java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
        while ( portEnum.hasMoreElements() )
        {
            CommPortIdentifier portIdentifier = portEnum.nextElement();
            System.out.println(portIdentifier.getName()  +  " - " +  getPortTypeName(portIdentifier.getPortType()) );
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

    public LiveData getLiveDataFrame() {
        return liveDataFrame;
    }
}
