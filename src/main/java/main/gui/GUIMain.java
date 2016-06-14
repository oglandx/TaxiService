package main.gui;

import javax.swing.*;

/**
 * Created by oglandx on 6/13/16.
 */
public class GUIMain extends JFrame {
    private String title = "Taxi ordering service";

    private JPanel panel;
    private JButton signInButton;
    private JButton signUpButton;


    public GUIMain() {
        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        signInButton.addActionListener(e -> {
            setVisible(false);
            dispose();
            new Login();
        });

        signUpButton.addActionListener(e -> {
            setVisible(false);
            dispose();
            new Registration();
        });
    }
}
