package main.gui.passenger;

import javax.swing.*;

/**
 * Created by oglandx on 6/14/16.
 */
public class PassengerGUI extends JFrame {
    private String title = "Taxi ordering service: passenger";
    private JPanel panel1;
    private JPanel panel;
    private JList orderList;
    private JComboBox orderType;
    private JTextField textField1;
    private JButton declineOrderButton;

    public PassengerGUI() {
        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
