package main.gui.driver;

import main.facade.ApplicationError;
import main.facade.DriverFacade;
import main.logic.Driver;
import main.logic.DriverStatus;
import main.logic.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by oglandx on 6/14/16.
 */
public class DriverGUI extends JFrame {
    private String title = "Taxi ordering service: driver";

    private DriverFacade facade;
    private Driver driver;
    private List<Order> fullOrderList = null;
    private JPanel panel;
    private JButton declineOrderButton;
    private JTextField cityField;
    private JTextField streetField;
    private JTextField buildingField;
    private JTextField orderField;
    private JButton acceptOrderButton;
    private JTextField statusField;
    private JList<Order> orderList;
    private JButton updateOrderListButton;

    public DriverGUI(DriverFacade facade, Driver driver) {
        this.facade = facade;
        this.driver = driver;

        setContentPane(panel);
        setSize(550, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        reloadOrderList();
        acceptOrderButton.addActionListener(this::acceptOrder);
        declineOrderButton.addActionListener(this::declineOrder);

        orderList.addListSelectionListener(e -> {
            Order order = orderList.getSelectedValue();
            if (order == null) {
                cityField.setText("");
                streetField.setText("");
                buildingField.setText("");
                orderField.setText("");
                statusField.setText("");
                acceptOrderButton.setEnabled(false);
                declineOrderButton.setEnabled(false);
            }
            else {
                cityField.setText(order.getAddress().getCity());
                streetField.setText(order.getAddress().getStreet());
                buildingField.setText(order.getAddress().getBuilding());
                orderField.setText(order.toString());
                statusField.setText(order.getStatus().getId());
                acceptOrderButton.setEnabled(true);
                declineOrderButton.setEnabled(true);
            }
        });

        updateOrderListButton.addActionListener(e -> {
            reloadOrderList();
        });
    }

    private void reloadOrderList() {
        try {
            final DefaultListModel<Order> orderListModel = new DefaultListModel<>();
            fullOrderList = facade.getOrderList(driver);
            orderList.setModel(orderListModel);
            fullOrderList.forEach(orderListModel::addElement);
        }
        catch (ApplicationError e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while loading orders list",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acceptOrder(ActionEvent e) {
        Order selectedOrder = orderList.getSelectedValue();
        if (selectedOrder == null) {
            JOptionPane.showMessageDialog(null, "Something went wrong",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            try {
                facade.selectOrder(driver, selectedOrder);
                for (Order order: fullOrderList) {
                    if (order != selectedOrder) {
                        facade.declineOrder(driver, order);
                    }
                }
                facade.setStatus(driver, DriverStatus.BUSY);
            } catch (ApplicationError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while trying to accept order and decline others",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
            setVisible(false);
            dispose();
            new DriverGUIDrive(facade, driver, selectedOrder);
        }
    }

    private void declineOrder(ActionEvent e) {
        Order selectedOrder = orderList.getSelectedValue();
        if (selectedOrder == null) {
            JOptionPane.showMessageDialog(null, "Something went wrong",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            try {
                if (!facade.declineOrder(driver, selectedOrder)) {
                    JOptionPane.showMessageDialog(null, "Cannot decline this order",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
                else if (facade.getOrderList(driver).isEmpty()) {
                    facade.setStatus(driver, DriverStatus.FREE);
                }
            } catch (ApplicationError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while trying to decline order",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        reloadOrderList();
    }
}
