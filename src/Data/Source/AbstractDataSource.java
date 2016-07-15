/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package org.wmich.sunseeker.telemetry;;

import java.util.Arrays;
import java.util.HashMap;

public abstract class AbstractDataSource implements DataSourceInterface {
    protected String[] providedTypes;

    protected HashMap<String, DataTypeInterface> types;

    public AbstractDataSource (AbstractDataTypeCollection dataTypes) {
        types = new HashMap<String, DataTypeInterface>();

        for (DataTypeInterface type : dataTypes)
            types.put(type.getType(), type);
    }

    public String[] getTypes () {
        return providedTypes;
    }

    public boolean provides (String type) {
        Arrays.sort(providedTypes);

        return Arrays.binarySearch(providedTypes, type) >= 0;
    }
}
