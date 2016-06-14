package main.gui.driver;

import main.facade.ApplicationError;
import main.facade.DriverFacade;
import main.gui.PaymentGUI;
import main.logic.Driver;
import main.logic.Order;
import main.logic.Payment;
import main.logic.Rate;

import javax.swing.*;
import java.util.List;

/**
 * Created by oglandx on 6/14/16.
 */
public class DriverGUIDrive extends JFrame {

    private String title = "Taxi ordering service: driver drive mode";
    private DriverFacade facade;
    private Driver driver;
    private Payment payment = null;

    private JTextField cityField;
    private JTextField streetField;
    private JTextField orderField;
    private JTextField statusField;
    private JTextField buildingField;
    private JPanel panel;
    private JButton stopWaitingButton;
    private JButton leaveWaitingButton;
    private JButton startWaitingButton;
    private JButton showPaymentButton;
    private JSpinner distanceSpinner;

    public DriverGUIDrive(DriverFacade facade, Driver driver, Order order) {
        this.facade = facade;
        this.driver = driver;

        setContentPane(panel);
        setSize(550, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        cityField.setText(order.getAddress().getCity());
        streetField.setText(order.getAddress().getStreet());
        buildingField.setText(order.getAddress().getBuilding());
        orderField.setText(order.toString());
        statusField.setText(order.getStatus().getId());

        startWaitingButton.addActionListener(e -> {
            facade.startWaiting(driver);
            startWaitingButton.setEnabled(false);
            stopWaitingButton.setEnabled(true);
            leaveWaitingButton.setEnabled(true);
        });

        stopWaitingButton.addActionListener(e -> {
            int waitingTime = facade.endWaiting(driver);
            JOptionPane.showMessageDialog(null, "Waiting time is " + waitingTime + " minutes",
                    "Waiting time", JOptionPane.INFORMATION_MESSAGE);

            stopWaitingButton.setEnabled(false);
            leaveWaitingButton.setEnabled(false);
            distanceSpinner.setEnabled(true);
            showPaymentButton.setEnabled(true);
        });

        leaveWaitingButton.addActionListener(e -> {
            try {
                facade.leaveWaiting(driver);
            }
            catch (ApplicationError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while trying to decline order",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
            setVisible(false);
            dispose();
            new DriverGUI(facade, driver);
        });

        showPaymentButton.addActionListener(e -> {
            if (payment == null) {
                try {
                    List<Rate> rates = facade.getAvailableRates();
                    if (!rates.isEmpty()) {
                        facade.setCurrentRate(driver, rates.get(rates.size() - 1));
                        payment = facade.getPayment(driver, (int) distanceSpinner.getValue());
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "No rates found. Cannot calculate cost.",
                                "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ApplicationError ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while attaching payment to order",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
                distanceSpinner.setEnabled(false);
            }
            new PaymentGUI(payment);
        });
    }

}
