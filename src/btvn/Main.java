package btvn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends JFrame {
    private JTextField textField;
    private JLabel timeLabel;

    public Main() {
        setTitle("World Clock");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel label = new JLabel("Timezone:");
        textField = new JTextField(10);
        JButton addButton = new JButton("Add Clock");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClock();
            }
        });

        JPanel panel1 = new JPanel();
        panel1.add(timeLabel);

        JPanel panel2 = new JPanel();
        panel2.add(label);
        panel2.add(textField);
        panel2.add(addButton);

        add(panel1);
        add(panel2);
        setVisible(true);

        Thread updateTimeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    updateTime();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        updateTimeThread.start();
    }

    private void addClock() {
        String timezoneStr = textField.getText().trim();
        try {
            int timezone = Integer.parseInt(timezoneStr);
            if (timezone < -12 || timezone > 12) {
                throw new NumberFormatException();
            }
            ClockFrame clockFrame = new ClockFrame(timezone);
            clockFrame.setVisible(true);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid timezone. Please enter a number between -12 and 12.");
        }
    }

    private void updateTime() {
        ZonedDateTime currentTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText("Current time is: " + currentTime.format(formatter));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
