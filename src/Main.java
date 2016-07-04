/**
* Sunseeker Telemety
*
* Telemetry 2016
*
* @author Kai Gray <kai.a.gray@wmich.edu>
*/


package sunseeker.telemetry;


class Main {

	public static void main(String[] args) {

		//Serial serialComm      = new Serial();
		MainInterface mainIntf = new MainInterface();

		//SerialHandler serialHandler   = new SerialHandler(serialComm, mainIntf, portIntf);
		

		mainIntf.setSize(1000,750);
		mainIntf.setVisible(true);
	}
}