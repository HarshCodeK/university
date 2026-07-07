# Expense Tracker - Advanced Java Programming

A simple Java Swing-based Expense Tracker application demonstrating core OOP concepts.

## Features

- Login screen (hardcoded: admin / 1234)
- Add new expense (amount, category, date, description)
- View all expenses in JTable
- Filter by category or date range
- Category-wise and total expense summary
- Delete selected expense
- File I/O serialization for data persistence

## OOP Concepts Demonstrated

- **Encapsulation**: Private fields with public getters/setters in `Expense.java`
- **Inheritance**: `LoginScreen` and `MainFrame` extend `JFrame`
- **Abstraction**: `ExpenseManager` hides file I/O and filtering logic
- **Polymorphism**: List interface with ArrayList implementation

## How to Run

```bash
# Compile
javac -d . src/com/expensetracker/**/*.java src/com/expensetracker/Main.java

# Run
java com.expensetracker.Main
```

Or import into Eclipse/IntelliJ as a Java project and run `Main.java`.

## Screenshots

(Add screenshots here: Login screen, Main window with expenses table, Filter view)

## Project Structure

```
expense_tracker/
├── src/com/expensetracker/
│   ├── Main.java                  # Entry point
│   ├── model/
│   │   └── Expense.java           # Expense model (Encapsulation)
│   ├── view/
│   │   ├── LoginScreen.java       # Login window
│   │   └── MainFrame.java         # Main application window
│   └── controller/
│       └── ExpenseManager.java    # Business logic & file I/O
├── expenses.dat                   # Serialized data file (auto-created)
└── README.md
```
