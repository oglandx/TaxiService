package main.gui.passenger;

import main.facade.ApplicationError;
import main.facade.PassengerFacade;
import main.logic.Order;
import main.logic.OrderStatus;
import main.logic.Passenger;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;

/**
 * Created by oglandx on 6/14/16.
 */
public class PassengerGUI extends JFrame {
    private String title = "Taxi ordering service: passenger";
    private JPanel panel1;
    private JPanel panel;
    private JList<Order> orderList;
    private JComboBox orderTypeComboBox;
    private JTextField orderField;
    private JButton declineOrderButton;
    private JTextField cityField;
    private JTextField streetField;
    private JTextField buildingField;
    private JButton createNewOrderButton;
    private JButton showPaymentButton;
    private JTextField driverField;
    private JTextField statusField;

    private PassengerFacade facade;
    private Passenger passenger;

    public PassengerGUI(PassengerFacade facade, Passenger passenger) {
        this.facade = facade;
        this.passenger = passenger;
        setContentPane(panel);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        orderTypeComboBox.addActionListener(e -> {
            reloadOrderList();
        });

        orderList.addListSelectionListener(e -> {
            Order order = orderList.getSelectedValue();
            if (order == null) {
                cityField.setText("");
                streetField.setText("");
                buildingField.setText("");
                orderField.setText("");
                statusField.setText("");
            }
            else {
                cityField.setText(order.getAddress().getCity());
                streetField.setText(order.getAddress().getStreet());
                buildingField.setText(order.getAddress().getBuilding());
                orderField.setText(order.toString());
                statusField.setText(order.getStatus().getId());
            }
            declineOrderButton.setEnabled(order != null && !order.getStatus().eq(OrderStatus.DECLINED));
            showPaymentButton.setEnabled(order != null && order.getPayment() != null);
        });

        declineOrderButton.addActionListener(e -> {
            declineOrder();
        });

        createNewOrderButton.addActionListener(e -> {
            new CreateOrder(facade, passenger);
        });

        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent windowEvent) {
                reloadOrderList();
            }

            @Override
            public void windowLostFocus(WindowEvent windowEvent) {

            }
        });
    }

    private void reloadOrderList() {
        String selected = ((String)orderTypeComboBox.getSelectedItem()).toUpperCase();
        reloadOrderList(OrderStatus.getValue(selected.equals("ALL") ? null : selected));
    }

    private void reloadOrderList(OrderStatus status) {
        try {
            final DefaultListModel<Order> orderListModel = new DefaultListModel<>();
            List<Order> orders = facade.getOrderList(passenger, status);
            orderList.setModel(orderListModel);
            orders.forEach(orderListModel::addElement);
        }
        catch (ApplicationError e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while loading data",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void declineOrder() {
        String orderType = ((String)orderTypeComboBox.getSelectedItem()).toUpperCase();
        if (!orderType.equals("DECLINED") &&
                JOptionPane.showConfirmDialog(null, "Are you sure you want to decline this order?",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Order order = orderList.getSelectedValue();
            if (order == null) {
                JOptionPane.showMessageDialog(null, "Something went wrong",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    facade.declineOrder(passenger, order);
                } catch (ApplicationError ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred while trying to decline the order",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null, "The order has successfully declined",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        reloadOrderList();
    }
}
