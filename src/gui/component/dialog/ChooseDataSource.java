/**
 * Sunseeker Telemetry Application
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 15, 2016
 */

package org.wmich.sunseeker.telemetry.gui.component.dialog;

import org.wmich.sunseeker.telemetry.data.source.DataSource;

import java.util.List;

public interface ChooseDataSource {
    public DataSource prompt(List<DataSource> sources);
}
