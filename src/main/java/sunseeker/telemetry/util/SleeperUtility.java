package sunseeker.telemetry.util;

/**
 * Created by aleccarpenter on 6/11/17.
 */
public class SleeperUtility {
    public void sleep(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) { }
    }
}
