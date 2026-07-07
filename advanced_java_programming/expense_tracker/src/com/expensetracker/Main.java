package com.expensetracker;

import com.expensetracker.view.LoginScreen;
import javax.swing.SwingUtilities;

// Entry point - Runnable class that launches the application
public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures GUI creation happens on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
