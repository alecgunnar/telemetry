package sunseeker.telemetry.data;

import sunseeker.telemetry.data.parser.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public class PseudoRandomDataSource extends AbstractDataSource implements LiveDataSource {
    private Parser parser;

    public PseudoRandomDataSource(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void start() {
        Random generator = new Random();

        while (true) {
            Map<String, Double> data = new HashMap<>();

            data.put("venus", Math.floor(100 * generator.nextDouble()));
            data.put("mercury", Math.floor(100 * generator.nextDouble()));
            data.put("mars", Math.floor(100 * generator.nextDouble()));
            data.put("earth", Math.floor(100 * generator.nextDouble()));
            data.put("jupiter", Math.floor(100 * generator.nextDouble()));
            data.put("saturn", Math.floor(100 * generator.nextDouble()));

            emitData(data);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void stop() {

    }
}
