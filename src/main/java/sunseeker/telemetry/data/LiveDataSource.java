package sunseeker.telemetry.data;

import java.util.Map;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public interface LiveDataSource {
    void start() throws CannotStartException;

    void stop();

    void subscribe(Subscriber sub);

    interface Subscriber {
        void receiveData(Map<String, Double> values);

        void receiveError(String msg);
    }

    class CannotStartException extends Exception {
        public CannotStartException(String msg) { super(msg); }
    }

    class NoSubscriberException extends CannotStartException {
        public NoSubscriberException(String msg) {
            super(msg);
        }
    }
}
