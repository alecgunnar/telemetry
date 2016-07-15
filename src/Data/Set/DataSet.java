/**
* Sunseeker Telemetry
*
* @author Alec Carpenter <alecgunnar@gmail.com>
* @date July 14, 2016
*/

package org.wmich.sunseeker.telemetry.data;

import java.util.HashMap;

public class DataSet extends HashMap<String, Double> implements DataSetInterface {
    public Double getValue (String name) {
        if (containsKey(name))
            return get(name);

        return null;
    }
}