/**
 * Sunseeker Telemety
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

abstract class AbstractMainView extends AbstractView {
    final public static int VIEW_WIDTH   = 1000;
    final public static int VIEW_HEIGHT  = 700;
    final public static int PADDING      = 10;
    final public static int AXIS_PADDING = PADDING * 2;

    abstract public void useGraphPanel(AbstractGraphPanel panel);

    abstract public void useDataSelectPanel(AbstractDataSelectPanel panel);

    abstract public void useLiveDataPanel(AbstractLiveDataPanel panel);

    abstract public void useLinePanels(AbstractLinePanel[] panels);
}