# Library Management System (LMS)

A MySQL-based Library Management System for managing books, members, and transactions.

## Database: `university_library`

## Tables

| Table        | Description                                  |
|-------------|----------------------------------------------|
| `Authors`   | Author details (name, bio)                   |
| `Books`     | Book information (title, genre, ISBN, copies)|
| `Members`   | Library members (name, email, phone)         |
| `Transactions` | Issue/return records with dates and status |

## ER Diagram (Text)

```
Authors ────< Books ────< Transactions >──── Members
    |            |                              |
  author_id    book_id                       member_id
```

- One Author can write many Books (1:N)
- One Book can have many Transactions (1:N)
- One Member can have many Transactions (1:N)

## How to Use

```sql
-- 1. Create database and tables
SOURCE schema.sql;

-- 2. Insert sample data
SOURCE sample_data.sql;

-- 3. Run example queries
SOURCE queries.sql;
```

Or run commands in MySQL CLI:

```bash
mysql -u root -p < schema.sql
mysql -u root -p < sample_data.sql
mysql -u root -p < queries.sql
```

## Features Demonstrated

- Book CRUD operations
- Member registration
- Issue and Return books
- Search by title, author, genre
- Available books listing
- Overdue transaction detection
- Stock management (available_copies tracking)

## Sample Data

- 5 Authors (Indian + International)
- 7 Books across Fantasy, Fiction genres
- 5 Members
- 6 Sample Transactions (mix of issued, returned, overdue)
