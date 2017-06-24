package sunseeker.telemetry.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aleccarpenter on 6/24/17.
 */
public abstract class AbstractDataSource implements LiveDataSource {
    private List<Subscriber> subscribers = new ArrayList<>();

    @Override
    public void subscribe(Subscriber sub) {
        subscribers.add(sub);
    }

    protected void emitData(Map<String, Double> data) {
        for (Subscriber sub : subscribers)
            sub.receiveData(data);
    }
}
