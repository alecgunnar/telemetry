/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import javax.swing.JFileChooser;

//import java.io.IOException;

public class FileSelect extends AbstractArchive {

	protected String fileName = "telemetryData";
	protected JFileChooser select;

	protected ArchiveData archive;



	FileSelect () {
		select = new JFileChooser();
	}


	protected String chooseFile() {
		int returnVal = select.showOpenDialog(getParent());

		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("Writing to: " + select.getSelectedFile().getName());
			System.out.println(select.getSelectedFile());
		}

		return select.getSelectedFile().toString();
	}

/*
	protected void archive () throws IOException {
		try{
			archive = new ArchiveData(select.getSelectedFile().toString());
		}
		catch(IOException e) {
			System.out.println("Falure to properly write to file...");
		}
	}
	*/
}