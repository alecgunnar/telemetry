/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 12, 2016
 */

package sunseeker.telemetry;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Dimension;

class CreateMenu extends AbstractMenu {

	protected JMenu source;
	protected JMenu file;
	protected JMenu session;

	protected JMenuItem selectSource;

	protected JMenuItem selectFile;
	protected JMenuItem saveFile;
	protected JMenuItem closeFile;

	protected JMenuItem startSess;
	protected JMenuItem pause;
	protected JMenuItem stop;
	protected JMenuItem endSess;
	


	CreateMenu () {
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));

		this.menus();
		this.menuItems();


		source.add(selectSource);
		addMenu(source);
		
		file.add(selectFile);
		file.add(closeFile);
		file.add(saveFile);
		addMenu(file);
		
		session.add(startSess);
		session.add(pause);
		session.add(stop);
		session.add(endSess);
		addMenu(session);

	}

	protected void addMenu (JMenu menu) {
		this.add(menu);
	}

	protected void menus () {
		source = new JMenu(SOURCE, true);

		file = new JMenu(FILE, true);

		session = new JMenu(DATA, true);
	}

	protected void menuItems () {
		selectSource = new JMenuItem(ACTION_SOURCE);

		selectFile   = new JMenuItem(ACTION_FILE_SELECT);
		saveFile     = new JMenuItem(ACTION_FILE_SAVE);
		closeFile    = new JMenuItem(ACTION_FILE_CLOSE);

		startSess    = new JMenuItem(ACTION_DATA_START);
		pause        = new JMenuItem(ACTION_DATA_PAUSE);
		stop         = new JMenuItem(ACTION_DATA_STOP);
		endSess      = new JMenuItem(ACTION_DATA_END);
	}
 
}