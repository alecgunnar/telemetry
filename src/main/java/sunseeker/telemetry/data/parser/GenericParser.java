package sunseeker.telemetry.data.parser;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public class GenericParser implements Parser {
    private List<String> ignore;

    public GenericParser() {
        ignore = Arrays.asList("ABCDEF", "UVWXYZ");
    }

    @Override
    public Map<String, Double> pushData(String data) {
        Map<String, Double> parsed = new HashMap<>();

        String[] lines = data.split("\n");

        for (String line : lines) processLine(line, parsed);

        return parsed;
    }

    private void processLine(String line, Map<String, Double> parsed) {
        String[] parts = line.split(",");

        if (ignore.indexOf(parts[0]) > -1) return;

        parsed.put(parts[0] + "_0", parseFloatFromHex(parts[1]));
        parsed.put(parts[0] + "_1", parseFloatFromHex(parts[2]));
    }

    private double parseFloatFromHex(String hex) {
        String digits = hex.substring(2);
        long value = Long.parseLong(digits, 16);

        byte[] bytes = {
                processByteAtOffset(value, 0),
                processByteAtOffset(value, 8),
                processByteAtOffset(value, 16),
                processByteAtOffset(value, 24)
        };

        return ByteBuffer.wrap(bytes).getFloat();
    }

    protected byte processByteAtOffset (long val, int offset) {
        return (byte) ((val >> offset) & 0xFF);
    }
}
