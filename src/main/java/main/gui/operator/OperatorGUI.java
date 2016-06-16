package main.gui.operator;

import main.facade.ApplicationError;
import main.facade.OperatorFacade;
import main.gui.passenger.CreateOrder;
import main.logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.List;
import java.util.jar.Pack200;

/**
 * Created by oglandx on 6/14/16.
 */
public class OperatorGUI extends JFrame {
    private String title = "Taxi ordering service: operator";

    private OperatorFacade facade;
    private Operator operator;
    private Driver bestDriver = null;
    List<Driver> freeDrivers = null;

    private JPanel panel1;
    private JPanel panel;
    private JComboBox orderTypeComboBox;
    private JButton declineOrderButton;
    private JTextField cityField;
    private JTextField streetField;
    private JTextField buildingField;
    private JTextField orderField;
    private JButton createNewOrderButton;
    private JButton showPaymentButton;
    private JTextField statusField;
    private JList<Order> orderList;
    private JButton findOptimalDriverButton;
    private JComboBox chooseDriverComboBox;
    private JTextField driverField;
    private JButton acceptFoundDriverButton;
    private JList<Driver> freeDriversList;
    private JButton updateListsButton;
    private JButton killOrderButton;


    public OperatorGUI(OperatorFacade facade, Operator operator) {
        this.facade = facade;
        this.operator = operator;

        setContentPane(panel);
        setSize(850, 400);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        orderList.addListSelectionListener(e -> {
            Order order = orderList.getSelectedValue();
            freeDriversList.clearSelection();
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
            driverField.setText("");
            findOptimalDriverButton.setEnabled(facade.canFindOptimalDriver(order));
            acceptFoundDriverButton.setEnabled(false);
            killOrderButton.setEnabled(facade.canKillOrder(order));
        });

        freeDriversList.addListSelectionListener(e -> {
            Driver selectedDriver = freeDriversList.getSelectedValue();
            Order selectedOrder = orderList.getSelectedValue();
            acceptFoundDriverButton.setEnabled(selectedDriver != null && selectedOrder != null);
            selectBestDriver(selectedDriver);
        });

        reloadOrderList();
        reloadDriverList();
        findOptimalDriverButton.addActionListener(this::findBestDriver);

        updateListsButton.addActionListener(e -> {
            reloadOrderList();
            reloadDriverList();
        });

        acceptFoundDriverButton.addActionListener(this::acceptFoundDriver);
        killOrderButton.addActionListener(this::killOrder);
    }

    private void reloadOrderList() {
        try {
            final DefaultListModel<Order> orderListModel = new DefaultListModel<>();
            List<Order> orders = facade.getOrderList();
            orderList.setModel(orderListModel);
            orders.forEach(orderListModel::addElement);
        }
        catch (ApplicationError e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while loading orders list",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reloadDriverList() {
        try {
            final DefaultListModel<Driver> driverListModel = new DefaultListModel<>();
            freeDrivers = facade.getFreeDrivers();
            freeDriversList.setModel(driverListModel);
            freeDrivers.forEach(driverListModel::addElement);
            driverField.setText("");
        }
        catch (ApplicationError e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while loading drivers list",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selectBestDriver(Driver driver) {
        if (freeDrivers != null && driver != null && orderList.getSelectedValue() != null) {
            freeDriversList.setSelectedIndex(freeDrivers.indexOf(driver));
            driverField.setText(driver.toString());
        }
        else {
            driverField.setText("");
        }
    }

    private void findBestDriver(ActionEvent e) {
        try {
            Order order = orderList.getSelectedValue();
            if (order == null) {
                JOptionPane.showMessageDialog(null, "Something went wrong",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
            else {
                ChooseDriverStrategy strategy = null;
                switch (chooseDriverComboBox.getSelectedIndex()) {
                    case 0:
                        strategy = new ChooseDriverStrategyBestKarma();
                        break;
                    case 1:
                        strategy = new ChooseDriverStrategyWorstKarma();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Unexpected selection in choose driver combo box",
                                "Warning!", JOptionPane.WARNING_MESSAGE);
                }
                if (freeDrivers != null) {
                    bestDriver = facade.findBestDriver(operator, order, freeDrivers, strategy);
                    selectBestDriver(bestDriver);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Cannot get free drivers",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        catch (ApplicationError ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred choose driver",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acceptFoundDriver(ActionEvent e) {
        Order order = orderList.getSelectedValue();
        Driver driver = freeDriversList.getSelectedValue();
        if (order == null || driver == null) {
            JOptionPane.showMessageDialog(null, "Something went wrong",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            try {
                if (!facade.attachOrder(operator, order, driver)) {
                    JOptionPane.showMessageDialog(null, "Cannot accept this order",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ApplicationError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while accepting driver",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        reloadDriverList();
        reloadOrderList();
    }

    private void killOrder(ActionEvent e) {
        Order order = orderList.getSelectedValue();
        if (order == null || !order.getStatus().eq(OrderStatus.DECLINED)) {
            JOptionPane.showMessageDialog(null, "Something went wrong",
                    "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            try {
                if (!facade.killOrder(operator, order)) {
                    JOptionPane.showMessageDialog(null, "Cannot kill order",
                            "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ApplicationError ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while trying to make order DEAD",
                        "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        reloadDriverList();
        reloadOrderList();
    }
}
