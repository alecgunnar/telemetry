/**
* Sunseeker Telemety
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 2, 2016
*/

package sunseeker.telemetry;

class MainController {
    protected AbstractMainView mainView;

    public MainController (AbstractMainView main) {
        mainView = main;
    }

    public void run () {
        mainView.showView();
    }

    public void useGraphPanel (AbstractGraphPanel panel) {
        mainView.useGraphPanel(panel);
    }

    public void useDataSelectPanel (AbstractDataSelectPanel panel) {
        mainView.useDataSelectPanel(panel);
    }

    public void useLiveDataPanel (AbstractLiveDataPanel panel) {
        mainView.useLiveDataPanel(panel);
    }

    public void useLinePanels(AbstractLinePanel[] panels) {
        mainView.useLinePanels(panels);
    }
}