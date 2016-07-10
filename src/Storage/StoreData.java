/**
 * Sunseeker Telemetry
 *
 * @author Kai Gray <kai.a.gray@wmich.edu>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.lang.Exception;
import java.io.IOException;

public class StoreData extends FileSelect {
	
	protected File file;

	//protected BufferedReader read;

	protected PrintWriter create;
	protected FileWriter write;

	protected static int counter = 0;
	protected static String line;

	final String HEAD = "Counter: ";

	final String TIME    = "Time,";
	final String SPEED   = "Speed,";
	final String VOLTAGE = "Voltage,";
	final String CURRENT = "Current,";
	final String ERRORS  = "Errors";


	StoreData () throws IOException{
		
		try{

			file = new File(fileName + ".txt");
			create = new PrintWriter("telemetryData11569.txt","ASCII");
		}
		catch(Exception e) {
			System.out.println("the file is shit");
		}
		write = new FileWriter(file, true);

		startFile();


	}

	protected void startFile () throws IOException {
		write.write(HEAD + "\t" + TIME + "\t" + VOLTAGE + "\t" + CURRENT + "\t" + SPEED + "\t" + ERRORS);
		write.append("\n");
	}

	protected static String packageData (DataCollectionInterface data) {
		String line = "";
		String speed = "10",volt = "100",amp = " 500";

		

		line = (counter + ", " + counter + ", " + volt + ", " + amp + ", " + "00" + "\n");

		return line;
	}


	protected void writeData (String line) throws IOException { 

		try{
			write.append(line);

		}catch (IOException e){
			System.err.println("IOException");
		}catch (Exception e){
			System.err.println("Unknown Error" + e);
		}
			
	}

	public void closeAll () throws IOException{
		try{
			create.close();
			write.close();
		}
		catch(Exception e){
			System.out.println("write was not closed \n Error:" + e);
		}
		//read.close();
	}

	

}