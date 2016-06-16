package main.gui.passenger;

import main.facade.ApplicationError;
import main.facade.PassengerFacade;
import main.gui.PaymentGUI;
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
    private JButton killOrderButton;
    private JTextField cityField;
    private JTextField streetField;
    private JTextField buildingField;
    private JButton createNewOrderButton;
    private JButton showPaymentButton;
    private JTextField driverField;
    private JTextField statusField;
    private JButton rateOrderButton;
    private JTextField ratingField;

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
                driverField.setText("");
                ratingField.setText("");
            }
            else {
                cityField.setText(order.getAddress().getCity());
                streetField.setText(order.getAddress().getStreet());
                buildingField.setText(order.getAddress().getBuilding());
                orderField.setText(order.toString());
                statusField.setText(order.getStatus().getId());
                driverField.setText(facade.getDriverName(order));
                ratingField.setText(facade.getGUIRating(order));
            }
            killOrderButton.setEnabled(facade.canKillOrder(order));
            showPaymentButton.setEnabled(facade.canShowPayment(order));
            rateOrderButton.setEnabled(facade.canRateOrder(order));
        });

        killOrderButton.addActionListener(e -> {
            killOrder();
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

        showPaymentButton.addActionListener(e -> {
            Order order = orderList.getSelectedValue();
            if (facade.canRateOrder(order)) {
                new PaymentGUI(order.getPayment());
            }
        });

        rateOrderButton.addActionListener(e -> {
            Order order = orderList.getSelectedValue();
            if (order == null) {
                JOptionPane.showMessageDialog(null, "Something went wrong",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else {
                new RatingGUI(facade, order);
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

    private void killOrder() {
        String orderType = ((String)orderTypeComboBox.getSelectedItem()).toUpperCase();
        if (!orderType.equals("DEAD") && !orderType.equals("EXECUTED") &&
                JOptionPane.showConfirmDialog(null, "Are you sure you want to decline this order?",
                "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Order order = orderList.getSelectedValue();
            if (order == null) {
                JOptionPane.showMessageDialog(null, "Something went wrong",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    facade.killOrder(passenger, order);
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
