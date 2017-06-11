package sunseeker.telemetry;

import sunseeker.telemetry.ui.LiveData;

import javax.swing.*;

public class App implements Runnable {
    private LiveData liveDataFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());
    }

    public App() {
        liveDataFrame = new LiveData();
    }

    @Override
    public void run() {
        liveDataFrame.setVisible(true);
    }

    public LiveData getLiveDataFrame() {
        return liveDataFrame;
    }
}
