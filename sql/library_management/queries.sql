-- =============================================
-- Library Management System - Query Examples
-- =============================================

USE university_library;

-- 1. Show all books with author names
SELECT b.book_id, b.title, a.name AS author, b.genre, b.available_copies
FROM Books b
LEFT JOIN Authors a ON b.author_id = a.author_id
ORDER BY b.title;

-- 2. Search books by title
SELECT * FROM Books WHERE title LIKE '%Harry%';

-- 3. Search books by author
SELECT b.title, a.name
FROM Books b
JOIN Authors a ON b.author_id = a.author_id
WHERE a.name LIKE '%Rowling%';

-- 4. Search books by genre
SELECT * FROM Books WHERE genre = 'Fiction';

-- 5. Show available books (copies > 0)
SELECT title, available_copies FROM Books WHERE available_copies > 0;

-- 6. Show currently issued books
SELECT t.transaction_id, b.title, m.name AS member, t.issue_date, t.due_date
FROM Transactions t
JOIN Books b ON t.book_id = b.book_id
JOIN Members m ON t.member_id = m.member_id
WHERE t.status = 'issued';

-- 7. Show overdue transactions (past due date and not returned)
SELECT t.transaction_id, b.title, m.name AS member, t.due_date,
       DATEDIFF(CURDATE(), t.due_date) AS days_overdue
FROM Transactions t
JOIN Books b ON t.book_id = b.book_id
JOIN Members m ON t.member_id = m.member_id
WHERE t.status = 'issued' AND t.due_date < CURDATE();

-- 8. Issue a book (decrease available copies)
-- Run this when a book is issued:
UPDATE Books SET available_copies = available_copies - 1 WHERE book_id = 1;
INSERT INTO Transactions (book_id, member_id, due_date)
VALUES (1, 2, DATE_ADD(CURDATE(), INTERVAL 14 DAY));

-- 9. Return a book (increase available copies + update transaction)
-- Run this when a book is returned:
UPDATE Books SET available_copies = available_copies + 1 WHERE book_id = 1;
UPDATE Transactions
SET return_date = CURDATE(), status = 'returned'
WHERE transaction_id = 2;

-- 10. Add a new book
INSERT INTO Books (title, author_id, genre, isbn, total_copies, available_copies)
VALUES ('The Alchemist', 2, 'Fiction', '978-0-06-250217-4', 3, 3);

-- 11. Update a book
UPDATE Books SET available_copies = 5 WHERE book_id = 1;

-- 12. Delete a book
-- DELETE FROM Books WHERE book_id = 99;

-- 13. Register a new member
INSERT INTO Members (name, email, phone) VALUES ('Neha Gupta', 'neha.gupta@email.com', '9876543215');

-- 14. Show member transaction history
SELECT m.name, b.title, t.issue_date, t.due_date, t.return_date, t.status
FROM Members m
JOIN Transactions t ON m.member_id = t.member_id
JOIN Books b ON t.book_id = b.book_id
WHERE m.member_id = 1;
