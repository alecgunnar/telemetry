/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 2, 2016
*/

package sunseeker.telemetry;

import java.lang.Runnable;
import java.lang.Thread;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import javax.swing.Timer;

class MainController implements Runnable {
    final public static int LINE_REFRESH_INTERVAL = 250;

    protected AbstractMainFrame mainFrame;

    protected AbstractGraphPanel graphPanel;

    protected boolean paused = false;

    protected Timer lineUpdater;

    public MainController (AbstractMainFrame main) {
        mainFrame = main;
    }

    public void useGraphPanel (AbstractGraphPanel panel) {
        mainFrame.useGraphPanel(graphPanel = panel);
    }

    public void useDataSelectPanel (AbstractDataSelectPanel panel) {
        mainFrame.useDataSelectPanel(panel);
    }

    public void useLiveDataPanel (AbstractLiveDataPanel panel) {
        mainFrame.useLiveDataPanel(panel);
    }

    public void useLinePanels(AbstractLinePanel[] panels) {
        mainFrame.useLinePanels(panels);

        createLineUpdater();
    }

    public void start () {
        mainFrame.showFrame();

        EventQueue.invokeLater(this);
    }

    public void run () {
        lineUpdater.start();
    }

    protected void createLineUpdater () {
        lineUpdater = new Timer(LINE_REFRESH_INTERVAL, (ActionListener) graphPanel);
    }
}