package main.gui.driver;

import main.facade.DriverFacade;
import main.logic.Driver;
import main.logic.Order;

import javax.swing.*;

/**
 * Created by oglandx on 6/14/16.
 */
public class DriverGUIDrive extends JFrame {

    private String title = "Taxi ordering service: driver drive mode";
    private DriverFacade facade;
    private Driver driver;

    private JTextField cityField;
    private JTextField streetField;
    private JTextField orderField;
    private JTextField statusField;
    private JTextField buildingField;
    private JPanel panel;

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
    }

}
