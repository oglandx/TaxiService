package main.gui;

import main.logic.Payment;

import javax.swing.*;

/**
 * Created by oglandx on 6/14/16.
 */
public class PaymentGUI extends JFrame {
    private String title = "Payment";
    private JTextField rateCostPerKm;
    private JTextField rateCostPerMin;
    private JTextField rateFreeMins;
    private JTextField driveCost;
    private JTextField waitCost;
    private JTextField fullCost;
    private JButton okButton;
    private JPanel panel;
    private JTextField distance;
    private JTextField waitingTime;

    public PaymentGUI(Payment payment){
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        if (payment != null) {
            rateCostPerKm.setText(payment.getRate().getCostPerKm().toString());
            rateCostPerMin.setText(payment.getRate().getCostPerMin().toString());
            rateFreeMins.setText(String.valueOf(payment.getRate().getFreeMinutes()));
            distance.setText(String.valueOf(payment.getDistance()));
            waitingTime.setText(String.valueOf(payment.getWaitMin()));
            driveCost.setText(payment.getDriveCost().toString());
            waitCost.setText(payment.getWaitCost().toString());
            fullCost.setText(payment.getFullCost().toString());
        }
        else {
            JOptionPane.showMessageDialog(null, "Invalid payment!", "Error!", JOptionPane.ERROR_MESSAGE);
            setVisible(false);
            dispose();
        }

        okButton.addActionListener(e -> {
            setVisible(false);
            dispose();
        });
    }
}
