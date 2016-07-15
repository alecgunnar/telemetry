/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package org.wmich.sunseeker.telemetry;

import org.wmich.sunseeker.telemetry.data.DataSetInterface;
import org.wmich.sunseeker.telemetry.*;

public abstract class AbstractMainFrame extends AbstractFrame {
    final public static int FRAME_WIDTH   = 1000;
    final public static int FRAME_HEIGHT  = 700;
    final public static int PADDING      = 10;
    final public static int AXIS_PADDING = PADDING * 2;

    abstract public void useGraphPanel(AbstractGraphPanel panel);

    abstract public void useDataSelectPanel(AbstractDataSelectPanel panel);

    abstract public void useLiveDataPanel(AbstractLiveDataPanel panel);

    abstract public void useDataSource(DataSourceInterface dataSource);

    abstract public void putData(DataSetInterface dataSet);
}