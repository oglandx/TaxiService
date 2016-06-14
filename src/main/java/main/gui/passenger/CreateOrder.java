package main.gui.passenger;

import main.facade.ApplicationError;
import main.facade.PassengerFacade;
import main.logic.Address;
import main.logic.Passenger;

import javax.swing.*;

/**
 * Created by oglandx on 6/14/16.
 */
public class CreateOrder extends JFrame {
    private String title = "Create new order";
    private JTextField cityField;
    private JTextField streetField;
    private JTextField buildingField;
    private JButton createOrderButton;
    private JPanel panel;

    private PassengerFacade facade;
    private Passenger passenger;

    public CreateOrder(PassengerFacade facade, Passenger passenger) {
        this.facade = facade;
        this.passenger = passenger;

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        createOrderButton.addActionListener(e -> {
            try {
                Address address = new Address(
                    cityField.getText(),
                    streetField.getText(),
                    buildingField.getText()
                );
                facade.createOrder(passenger, address);
            }
            catch (ApplicationError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while trying to create order",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
            setVisible(false);
            dispose();
        });
    }
}
