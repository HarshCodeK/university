package com.expensetracker.view;

import javax.swing.*;
import java.awt.*;

// OOP Concept: Inheritance - JFrame is extended to create our login window
public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    // Hardcoded credentials for demo purposes
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";

    public LoginScreen() {
        setTitle("Expense Tracker - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title label
        JLabel titleLabel = new JLabel("Expense Tracker Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        // Password
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(e -> attemptLogin());
        add(panel);
    }

    private void attemptLogin() {
        String user = usernameField.getText();
        String pass = new String(passwordField.getPassword());

        if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose(); // close login window
            SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
