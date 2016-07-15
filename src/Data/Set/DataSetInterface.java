/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 14, 2016
*/

package org.wmich.sunseeker.telemetry.data;

import java.util.Map;

public interface DataSetInterface extends Map<String, Double> {
    public Double getValue (String name);
}