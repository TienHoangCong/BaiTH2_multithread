package btvn;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ClockFrame extends JFrame implements Runnable {
    private JLabel timeLabel;
    private int timezoneOffset;

    public ClockFrame(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
        setTitle("World Clock");
        setSize(200, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        timeLabel = new JLabel();
        updateTime();

        add(timeLabel);

        Thread updateTimeThread = new Thread(this);
        updateTimeThread.start();
    }

    @Override
    public void run() {
        while (true) {
            updateTime();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        long currentTime = System.currentTimeMillis() + timezoneOffset * 3600 * 1000;
        timeLabel.setText(dateFormat.format(new Date(currentTime)));
    }
}
