/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 2, 2016
*/

package sunseeker.telemetry;

import javax.swing.JFrame;
import java.lang.Runnable;
import java.lang.Thread;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

class MainController implements Runnable, ActionListener{
    final public static int LINE_REFRESH_INTERVAL = 250;

    protected AbstractMainFrame mainFrame;

    protected AbstractGraphPanel graphPanel;

    protected AbstractLiveDataPanel liveDataPanel;

    protected boolean paused = false;

    protected Timer lineUpdater;

    public MainController (AbstractMainFrame frame) {
        mainFrame = frame;
    }

    public void useGraphPanel (AbstractGraphPanel panel) {
        mainFrame.useGraphPanel(graphPanel = panel);
    }

    public void useDataSelectPanel (AbstractDataSelectPanel panel) {
        mainFrame.useDataSelectPanel(panel);
    }

    public void useLiveDataPanel (AbstractLiveDataPanel panel) {
        mainFrame.useLiveDataPanel(liveDataPanel = panel);
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

    public void actionPerformed (ActionEvent evt) {
        graphPanel.repaint();
        liveDataPanel.refresh();
    }

    protected void createLineUpdater () {
        lineUpdater = new Timer(LINE_REFRESH_INTERVAL, this);
    }
}