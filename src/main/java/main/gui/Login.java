package main.gui;

import main.facade.*;
import main.gui.driver.DriverGUI;
import main.gui.driver.DriverGUIDrive;
import main.gui.operator.OperatorGUI;
import main.gui.passenger.PassengerGUI;
import main.logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

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

    private final ApplicationFacade facade;

    public Login(ApplicationFacade facade) {
        this.facade = facade;

        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        logInButton.addActionListener(this::login);
        emailField.addActionListener(this::login);
        passwordField.addActionListener(this::login);
    }

    private void login(ActionEvent e) {
        {
            Pattern emailPattern = Pattern.compile("^\\w[\\.\\w]*@\\w[\\.\\w]*$");
            if (!emailPattern.matcher(emailField.getText()).matches()) {
                JOptionPane.showMessageDialog(null, "Wrong email format", "Warning!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Too short field 'Password'",
                        "Warning!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            AuthData authData = new AuthData(
                emailField.getText(),
                new String(passwordField.getPassword())
            );

            try {
                if (passengerRadioButton.isSelected()) {
                    PassengerFacade passengerFacade = facade.getPassengerFacade();
                    Passenger passenger = passengerFacade.auth(authData);
                    if (passenger == null) {
                        JOptionPane.showMessageDialog(null, "Wrong auth data for passenger",
                                "Warning!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Authentication is successful",
                                "Success!", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        dispose();
                        new PassengerGUI(passengerFacade, passenger);
                    }
                } else if (operatorRadioButton.isSelected()) {
                    OperatorFacade operatorFacade = facade.getOperatorFacade();
                    Operator operator = operatorFacade.auth(authData);
                    if (operator == null) {
                        JOptionPane.showMessageDialog(null, "Wrong auth data for operator",
                                "Warning!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Authentication is successful",
                                "Success!", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        dispose();
                        new OperatorGUI(operatorFacade, operator);
                    }
                } else if (driverRadioButton.isSelected()) {
                    DriverFacade driverFacade = facade.getDriverFacade();
                    Driver driver = driverFacade.auth(authData);
                    if (driver == null) {
                        JOptionPane.showMessageDialog(null, "Wrong auth data for driver",
                                "Warning!", JOptionPane.WARNING_MESSAGE);
                    } else {
                        Order currentOrder = driverFacade.getCurrentOrder(driver);
                        JOptionPane.showMessageDialog(this, "Authentication is successful",
                                "Success!", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        dispose();
                        if (currentOrder == null) {
                            new DriverGUI(driverFacade, driver);
                        }
                        else {
                            new DriverGUIDrive(driverFacade, driver, currentOrder);
                        }
                    }
                }
            }
            catch (ApplicationError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while login",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
