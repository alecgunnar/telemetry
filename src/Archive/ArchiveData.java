/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

import java.lang.Exception;
import java.io.IOException;

public class ArchiveData extends AbstractArchive {

	protected File file;

	protected FileWriter write;

	protected static int counter = 0;
	protected static String line;

	final String HEAD    = "Counter,";
	final String TIME    = "Time,";
	final String SPEED   = "Speed,";
	final String VOLTAGE = "Voltage,";
	final String CURRENT = "Current,";
	final String ERRORS  = "Errors";


	ArchiveData (String fileName) throws IOException{

		/*
		* create text file with fileName from FileSelect class
		*/		
		file = new File(fileName + ".txt");
		
		try{
			write = new FileWriter(file);
		} catch (IOException e) {
			System.out.println("Failed to open file... ");
		} catch(Exception e) {
			System.out.println("Probably your fault... " + e);
		}

		this.startFile();
	}

	/*
	* add headers to file in csv format;
	*/
	protected void startFile () throws IOException {
		write.append("" + HEAD  + TIME + VOLTAGE + CURRENT + SPEED + ERRORS);
		write.append("\n");
	}

	protected static String packageData (DataTypeInterface data) {
		String line = "";

		/*
		* Test Data
		*/
		String speed = "10",volt = "100",amp = " 500";

		
		/*
		* packs data in order of headers
		*/
		line = (counter + ", " + counter + ", " + volt + ", " + amp + ", " + "00" + "\n");
		counter++;

		return line;
	}

	/*
	* add a line of text to file
	*/
	protected void writeData (String line) throws IOException { 

		try{
			write.append(line);

		}catch (IOException e){
			System.err.println("IOException");
		}catch (Exception e){
			System.err.println("Unknown Error" + e);
		}
			
	}

	/*
	* properly close file(s)
	*/
	public void closeAll () throws IOException{
		try{
			write.close();
		} catch(IOException e) {
			System.out.println("Failed to close file... \n Error:" + e);
		} catch(Exception e){
			System.out.println("Probably your fault... " + e);
		}
	}

}