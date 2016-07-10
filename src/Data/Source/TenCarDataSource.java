/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import javax.swing.JFrame;

class TenCarDataSource extends AbstractSerialDataSource {
    public TenCarDataSource (AbstractDataTypeCollection types, JFrame parent) {
        super(types, parent);

        providedTypes = new String[] {
            "speed"
        };
    }

    public String getName () {
        return "2010 Car Data Source";
    }

    protected Client getClient () {
        return new Client(new ModemConnection(), new TenCarListener());
    }

    protected void process (String data) {

    }
}
