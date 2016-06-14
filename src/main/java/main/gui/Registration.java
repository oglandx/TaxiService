package main.gui;

import main.facade.*;
import main.gui.driver.DriverGUI;
import main.gui.operator.OperatorGUI;
import main.gui.passenger.PassengerGUI;
import main.logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private ApplicationFacade facade;

    public Registration(ApplicationFacade facade) {
        this.facade = facade;
        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        registerButton.addActionListener(this::register);
    }

    private void register(ActionEvent e) {
        if (lastNameField.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Too short field 'Last name'",
                    "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (firstNameField.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "Too short field 'First name'",
                    "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (middleNameField.getText().length() == 0) {
            // valid if is absent
        }

        Pattern datePattern = Pattern.compile("^\\d{4}\\-\\d{2}\\-\\d{2}$");
        if (!datePattern.matcher(birthDateField.getText()).matches()) {
            JOptionPane.showMessageDialog(null, "Wrong date format. Must be 'yyyy-mm-dd'. For example, '2000-12-28'",
                    "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = null;
        try {
            birthDate = new Date(dateFormat.parse(birthDateField.getText()).getTime());
        }
        catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Wrong date format. Must be 'yyyy-mm-dd'. For example, '2000-12-28'",
                    "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

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

        RegisterData regData = new RegisterData(
                lastNameField.getText(),
                firstNameField.getText(),
                middleNameField.getText(),
                birthDate,
                emailField.getText(),
                new String(passwordField.getPassword())
        );

        try {
            if (passengerRadioButton.isSelected()) {
                PassengerFacade passengerFacade = facade.getPassengerFacade();
                Passenger passenger = passengerFacade.registerNew(regData);
                if (passenger == null) {
                    JOptionPane.showMessageDialog(null, "Wrong register data for passenger",
                            "Warning!", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Registered successful!",
                            "Success!", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    dispose();
                    new PassengerGUI(passengerFacade, passenger);
                }
            } else if (operatorRadioButton.isSelected()) {
                OperatorFacade operatorFacade = facade.getOperatorFacade();
                Operator operator = operatorFacade.registerNew(regData);
                if (operator == null) {
                    JOptionPane.showMessageDialog(null, "Wrong register data for operator",
                            "Warning!", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Registered successful!",
                            "Success!", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    dispose();
                    new OperatorGUI(operatorFacade, operator);
                }
            } else if (driverRadioButton.isSelected()) {
                DriverFacade driverFacade = facade.getDriverFacade();
                Driver driver = driverFacade.registerNew(regData);
                if (driver == null) {
                    JOptionPane.showMessageDialog(null, "Wrong register data for driver",
                            "Warning!", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Registered successful!",
                            "Success!", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    dispose();
                    new DriverGUI(driverFacade, driver);
                }
            }
        }
        catch (ApplicationError ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while registering",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
