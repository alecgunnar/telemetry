/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 12, 2016
 */

package sunseeker.telemetry;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

abstract class AbstractMenu extends JMenuBar {

	protected final String SOURCE = "Data Source";

	protected final String ACTION_SOURCE = "Select Data Source";

	protected final String FILE = "File";

	protected final String ACTION_FILE_SELECT = "New File";
	protected final String ACTION_FILE_SAVE   = "Save File";
	protected final String ACTION_FILE_CLOSE  = "Close File";

	protected final String DATA = "Data";

	protected final String ACTION_DATA_START   = "Start Session";
	protected final String ACTION_DATA_END    = "End Session";
	protected final String ACTION_DATA_RESTART = "Restart Data";

	protected final int HEIGHT = 10;
	protected final int WIDTH  = 1000;

}