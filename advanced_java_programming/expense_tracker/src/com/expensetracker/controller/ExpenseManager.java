package com.expensetracker.controller;

import com.expensetracker.model.Expense;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// OOP Concept: Abstraction - Hides complex file I/O and filtering logic behind simple method calls
public class ExpenseManager {
    private List<Expense> expenses;
    private static final String FILE_NAME = "expenses.dat";

    public ExpenseManager() {
        expenses = new ArrayList<>();
        loadExpenses();
    }

    // OOP Concept: Polymorphism - Uses List interface reference with ArrayList implementation
    public void addExpense(Expense e) {
        expenses.add(e);
        saveExpenses();
    }

    public void deleteExpense(int index) {
        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            saveExpenses();
        }
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public List<Expense> filterByCategory(String category) {
        return expenses.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<Expense> filterByDateRange(String start, String end) {
        return expenses.stream()
                .filter(e -> e.getDate().compareTo(start) >= 0 && e.getDate().compareTo(end) <= 0)
                .collect(Collectors.toList());
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public double getCategoryTotal(String category) {
        return expenses.stream()
                .filter(e -> e.getCategory().equalsIgnoreCase(category))
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    // File I/O: Serialization - Saving objects to file
    private void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            System.err.println("Error saving expenses: " + e.getMessage());
        }
    }

    // File I/O: Deserialization - Loading objects from file
    @SuppressWarnings("unchecked")
    private void loadExpenses() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                expenses = (List<Expense>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading expenses: " + e.getMessage());
            }
        }
    }

    public int getSize() {
        return expenses.size();
    }
}
