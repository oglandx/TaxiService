package main.gui.passenger;

import main.facade.ApplicationError;
import main.facade.PassengerFacade;
import main.logic.Order;
import main.logic.Passenger;

import javax.swing.*;

/**
 * Created by oglandx on 6/15/16.
 */
public class RatingGUI extends JFrame {
    private String title = "Rating";
    private JPanel panel;
    private JTextField driverField;
    private JRadioButton greatRadioButton;
    private JRadioButton goodRadioButton;
    private JRadioButton normalRadioButton;
    private JRadioButton badRadioButton;
    private JRadioButton awfulRadioButton;
    private JButton rateButton;
    private JTextField orderField;
    private JButton cancelButton;

    public RatingGUI(PassengerFacade facade, Order order) {
        if (order == null || order.isRated() || order.getDriver() == null) {
            JOptionPane.showMessageDialog(null, "Wrong order to rate", "Error!", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setContentPane(panel);
        setLocationRelativeTo(null);
        setTitle(title);

        pack();

        setVisible(true);

        orderField.setText(order.toString());
        driverField.setText(order.getDriver().toString());

        rateButton.addActionListener(e -> {
            int rate = 0;
            if (greatRadioButton.isSelected()) {
                rate = 2;
            }
            else if (goodRadioButton.isSelected()) {
                rate = 1;
            }
            else if (badRadioButton.isSelected()) {
                rate = -1;
            }
            else if(awfulRadioButton.isSelected()) {
                rate = -2;
            }
            else {
                rate = 0;
            }

            try {
                if (!facade.rateOrder(order, rate)) {
                    JOptionPane.showMessageDialog(null, "Cannot rate this order", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch (ApplicationError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while trying to rate the order",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
            setVisible(false);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
    }
}
