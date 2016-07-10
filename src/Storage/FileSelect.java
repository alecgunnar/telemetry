/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;



class FileSelect {

	public String fileName = "telemetryData";

	FileSelect () {
		
	}



	/*
	* auto create name, check for previous files
	*/
	/*
	public static boolean setFileName () {
		boolean open = false;
		int counter = 0;

		do{
			try{
				read = new BufferedReader(new FileReader(fileName + ".txt"));
				counter++;
				fileName = (filename + "(" + counter + ")");
				open = false;
				if(counter == 150)
					return false;
			}
			catch (Exception e) {
				open = true;
			}
		}while(!open);

		return open;
		
	}
	*/
}