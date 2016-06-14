package main.gui.driver;

import main.facade.DriverFacade;
import main.logic.Driver;

import javax.swing.*;

/**
 * Created by oglandx on 6/14/16.
 */
public class DriverGUI extends JFrame {
    private String title = "Taxi ordering service: driver";

    private DriverFacade facade;
    private Driver driver;
    private JPanel panel;

    public DriverGUI(DriverFacade facade, Driver driver) {
        this.facade = facade;
        this.driver = driver;

        setContentPane(panel);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setTitle(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
