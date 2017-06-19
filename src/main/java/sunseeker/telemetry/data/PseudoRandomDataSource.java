package sunseeker.telemetry.data;

import sunseeker.telemetry.data.parser.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public class PseudoRandomDataSource implements LiveDataSource {
    private Parser parser;

    private Subscriber subscriber;

    public PseudoRandomDataSource(Parser parser) {
        this.parser = parser;
    }

    @Override
    public void start() {
        Random generator = new Random();

        while (true) {
            Map<String, Double> data = new HashMap<>();

            data.put("hello", Math.floor(100 * generator.nextDouble()));
            data.put("goodbye", Math.floor(100 * generator.nextDouble()));

            subscriber.receiveData(data);

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void subscribe(Subscriber sub) {
        subscriber = sub;
    }
}
