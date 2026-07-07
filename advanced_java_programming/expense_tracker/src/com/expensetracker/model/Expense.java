package com.expensetracker.model;

import java.io.Serializable;

// OOP Concept: Encapsulation - Data hiding using private fields with public getters/setters
// Serializable interface allows object serialization for file I/O storage
public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    private double amount;
    private String category;
    private String date;
    private String description;

    public Expense(double amount, String category, String date, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters - Encapsulation in action
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Expense{" + "amount=" + amount + ", category='" + category + "', date='" + date + "', description='" + description + "'}";
    }
}
