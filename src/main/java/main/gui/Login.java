package main.gui;

import javax.swing.*;

/**
 * Created by oglandx on 6/14/16.
 */
public class Login extends JFrame {
    private String title = "Login";
    private JFormattedTextField emailField;
    private JPasswordField passwordField;
    private JRadioButton passengerRadioButton;
    private JRadioButton operatorRadioButton;
    private JRadioButton driverRadioButton;
    private JButton logInButton;
    private JPanel panel;

    public Login() {
        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
