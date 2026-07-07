package com.expensetracker.view;

import com.expensetracker.controller.ExpenseManager;
import com.expensetracker.model.Expense;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// OOP Concept: Inheritance - Extending JFrame to create main application window
public class MainFrame extends JFrame {
    private ExpenseManager manager;
    private JTable expenseTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel, foodLabel, travelLabel, otherLabel;
    private JTextField amountField, categoryField, descField, filterCategoryField, startDateField, endDateField;

    public MainFrame() {
        manager = new ExpenseManager();
        setTitle("Expense Tracker");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        refreshTable();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Panel - Add Expense Form
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBorder(BorderFactory.createTitledBorder("Add New Expense"));
        addPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);

        g.gridy = 0; g.gridx = 0;
        addPanel.add(new JLabel("Amount:"), g);
        amountField = new JTextField(8);
        g.gridx = 1;
        addPanel.add(amountField, g);

        g.gridx = 2;
        addPanel.add(new JLabel("Category:"), g);
        String[] cats = {"Food", "Travel", "Other"};
        categoryField = new JTextField(8);
        g.gridx = 3;
        addPanel.add(categoryField, g);

        g.gridx = 4;
        addPanel.add(new JLabel("Date (yyyy-MM-dd):"), g);
        g.gridx = 5;
        JTextField dateField = new JTextField(10);
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        addPanel.add(dateField, g);

        g.gridx = 0; g.gridy = 1;
        addPanel.add(new JLabel("Description:"), g);
        descField = new JTextField(20);
        g.gridx = 1; g.gridwidth = 3;
        addPanel.add(descField, g);

        JButton addButton = new JButton("Add Expense");
        addButton.setBackground(new Color(60, 179, 113));
        addButton.setForeground(Color.WHITE);
        g.gridx = 4; g.gridwidth = 2;
        addPanel.add(addButton, g);

        addButton.addActionListener(e -> addExpense(dateField));

        // Center Panel - Table
        String[] columns = {"#", "Amount", "Category", "Date", "Description"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        expenseTable = new JTable(tableModel);
        expenseTable.getTableHeader().setBackground(new Color(70, 130, 180));
        expenseTable.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(expenseTable);

        // Bottom Panel - Filters and Summary
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
        filterPanel.add(new JLabel("Category:"));
        filterCategoryField = new JTextField(8);
        filterPanel.add(filterCategoryField);
        JButton filterCatBtn = new JButton("Filter");
        filterCatBtn.addActionListener(e -> filterByCategory());
        filterPanel.add(filterCatBtn);

        filterPanel.add(new JLabel("  Date From:"));
        startDateField = new JTextField(10);
        startDateField.setText(LocalDate.now().minusMonths(1).toString());
        filterPanel.add(startDateField);
        filterPanel.add(new JLabel("To:"));
        endDateField = new JTextField(10);
        endDateField.setText(LocalDate.now().toString());
        filterPanel.add(endDateField);
        JButton filterDateBtn = new JButton("Filter Date");
        filterDateBtn.addActionListener(e -> filterByDate());
        filterPanel.add(filterDateBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> refreshTable());
        filterPanel.add(resetBtn);

        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBackground(new Color(220, 80, 80));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.addActionListener(e -> deleteExpense());
        filterPanel.add(deleteBtn);

        // Summary Panel
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        foodLabel = new JLabel("Food: $0.00");
        travelLabel = new JLabel("Travel: $0.00");
        otherLabel = new JLabel("Other: $0.00");

        summaryPanel.add(totalLabel);
        summaryPanel.add(Box.createHorizontalStrut(20));
        summaryPanel.add(foodLabel);
        summaryPanel.add(Box.createHorizontalStrut(20));
        summaryPanel.add(travelLabel);
        summaryPanel.add(Box.createHorizontalStrut(20));
        summaryPanel.add(otherLabel);

        bottomPanel.add(filterPanel, BorderLayout.NORTH);
        bottomPanel.add(summaryPanel, BorderLayout.SOUTH);

        mainPanel.add(addPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addExpense(JTextField dateField) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryField.getText();
            String date = dateField.getText();
            String desc = descField.getText();

            if (category.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter category"); return; }

            manager.addExpense(new Expense(amount, category, date, desc));
            refreshTable();
            amountField.setText(""); descField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount!");
        }
    }

    private void deleteExpense() {
        int row = expenseTable.getSelectedRow();
        if (row >= 0) {
            manager.deleteExpense(row);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
        }
    }

    private void filterByCategory() {
        String cat = filterCategoryField.getText();
        if (cat.isEmpty()) return;
        tableModel.setRowCount(0);
        int i = 1;
        for (Expense e : manager.filterByCategory(cat)) {
            tableModel.addRow(new Object[]{i++, e.getAmount(), e.getCategory(), e.getDate(), e.getDescription()});
        }
    }

    private void filterByDate() {
        String start = startDateField.getText();
        String end = endDateField.getText();
        tableModel.setRowCount(0);
        int i = 1;
        for (Expense e : manager.filterByDateRange(start, end)) {
            tableModel.addRow(new Object[]{i++, e.getAmount(), e.getCategory(), e.getDate(), e.getDescription()});
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        int i = 1;
        for (Expense e : manager.getAllExpenses()) {
            tableModel.addRow(new Object[]{i++, e.getAmount(), e.getCategory(), e.getDate(), e.getDescription()});
        }
        updateSummary();
    }

    private void updateSummary() {
        totalLabel.setText(String.format("Total: $%.2f", manager.getTotalExpenses()));
        foodLabel.setText(String.format("Food: $%.2f", manager.getCategoryTotal("Food")));
        travelLabel.setText(String.format("Travel: $%.2f", manager.getCategoryTotal("Travel")));
        otherLabel.setText(String.format("Other: $%.2f", manager.getCategoryTotal("Other")));
    }
}
