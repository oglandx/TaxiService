package main.gui;

import main.facade.ApplicationError;
import main.facade.ApplicationFacade;

import javax.swing.*;

/**
 * Created by oglandx on 6/13/16.
 */
public class GUIMain extends JFrame {
    private String title = "Taxi ordering service";

    private JPanel panel;
    private JButton signInButton;
    private JButton signUpButton;

    private ApplicationFacade facade;

    public GUIMain() {
        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        try {
            facade = ApplicationFacade.getInstance();
        }
        catch (ApplicationError e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this, "Error while establishing a connection", "Error!", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        signInButton.addActionListener(e -> {
            setVisible(false);
            dispose();
            if (facade != null) {
                new Login(facade);
            }
            else {
                JOptionPane.showMessageDialog(this, "Cannot log in without established connection to database",
                        "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        });

        signUpButton.addActionListener(e -> {
            setVisible(false);
            dispose();
            if (facade != null) {
                new Registration(facade);
            }
            else {
                JOptionPane.showMessageDialog(this, "Cannot register without established connection to database",
                        "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
