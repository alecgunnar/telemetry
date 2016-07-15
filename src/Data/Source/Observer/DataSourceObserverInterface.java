/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 14, 2016
*/

package org.wmich.sunseeker.telemetry.data;

public interface DataSourceObserverInterface {
    public void putData(DataSetInterface dataSet);
}
