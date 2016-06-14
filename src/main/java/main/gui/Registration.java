package main.gui;

import javax.swing.*;

/**
 * Created by oglandx on 6/14/16.
 */
public class Registration extends JFrame {
    private String title = "Registration";
    private JPanel panel;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JFormattedTextField birthDateField;
    private JFormattedTextField emailField;
    private JPasswordField passwordField;
    private JRadioButton passengerRadioButton;
    private JRadioButton operatorRadioButton;
    private JRadioButton driverRadioButton;
    private JButton registerButton;

    public Registration() {
        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
