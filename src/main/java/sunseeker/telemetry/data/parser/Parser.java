package sunseeker.telemetry.data.parser;

import java.util.Map;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public interface Parser {
    Map<String, Double> pushData(String data);
}
