package main.gui.operator;

import main.facade.OperatorFacade;
import main.logic.Operator;

import javax.swing.*;

/**
 * Created by oglandx on 6/14/16.
 */
public class OperatorGUI extends JFrame {
    private String title = "Taxi ordering service: operator";

    private OperatorFacade facade;
    private Operator operator;
    private JPanel panel1;
    private JPanel panel;

    public OperatorGUI(OperatorFacade facade, Operator operator) {
        this.facade = facade;
        this.operator = operator;

        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
