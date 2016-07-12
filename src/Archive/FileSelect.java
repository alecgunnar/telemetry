/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.lang.Exception;

public class FileSelect extends AbstractArchive {

	protected FileFilter filter;

	protected int counter = 1;

	FileSelect () {
		
		/*
		* only show txt and csv files
		*/
		filter = new FileNameExtensionFilter(
			"text and csv",
			"txt",
			"csv"
		);

		this.setFileFilter(filter);

	}

	/*
	* select a file to save to
	*/
	protected String chooseSaveFile() {
		int returnVal = this.showOpenDialog(getParent());

		if(returnVal == JFileChooser.APPROVE_OPTION &&  this.approved(this.getSelectedFile().toString())) {
			System.out.println("File Name: " + this.getSelectedFile().getName());
			System.out.println("Writing to: " + this.getSelectedFile() + ".txt");
		}

		return this.getSelectedFile().toString();
	}

	/*
	* checks that file name is open
	*/
	protected boolean approved (String filePath){
		//create editable copy
		String fileName = filePath;
		//create file to check for existing file
		File file = new File(filePath + ".txt");

		try{

			//check if file exists
			while(file.isFile()){

				//copy original file path
				//add (#) to file name
				fileName = (filePath + "(" + counter + ")");

				//create file to search
				file = new File(fileName + ".txt");

				this.counter++;
			}

			//set file name to approved file
			this.setFile(fileName);

			return true;

		} catch(Exception e) {
			System.out.println("failed to make file...");

			return false;
		}
		
	}

	/*
	* sets file to approved file name
	*/
	protected void setFile (String fileName) {
		File file = new File(fileName);

		//only accepts type File
		this.setSelectedFile(file);

	}

}