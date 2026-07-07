-- =============================================
-- Sample Data for Library Management System
-- =============================================

USE university_library;

-- Insert Authors
INSERT INTO Authors (name, bio) VALUES
('J.K. Rowling', 'British author, best known for the Harry Potter series.'),
('George R.R. Martin', 'American novelist known for A Song of Ice and Fire.'),
('Harper Lee', 'American novelist known for To Kill a Mockingbird.'),
('Chetan Bhagat', 'Indian author known for Five Point Someone.'),
('R.K. Narayan', 'Indian author known for Malgudi Days.');

-- Insert Books
INSERT INTO Books (title, author_id, genre, isbn, total_copies, available_copies) VALUES
('Harry Potter and the Philosophers Stone', 1, 'Fantasy', '978-0-7475-3269-9', 5, 3),
('Harry Potter and the Chamber of Secrets', 1, 'Fantasy', '978-0-7475-3849-3', 3, 2),
('A Game of Thrones', 2, 'Fantasy', '978-0-553-10354-0', 4, 4),
('To Kill a Mockingbird', 3, 'Fiction', '978-0-06-112008-4', 2, 1),
('Five Point Someone', 4, 'Fiction', '978-81-291-0459-1', 3, 0),
('The Guide', 5, 'Fiction', '978-0-14-010731-7', 2, 2),
('Malgudi Days', 5, 'Fiction', '978-0-14-010732-4', 2, 1);

-- Insert Members
INSERT INTO Members (name, email, phone) VALUES
('Amit Sharma', 'amit.sharma@email.com', '9876543210'),
('Priya Patel', 'priya.patel@email.com', '9876543211'),
('Ravi Kumar', 'ravi.kumar@email.com', '9876543212'),
('Sneha Reddy', 'sneha.reddy@email.com', '9876543213'),
('Vikram Singh', 'vikram.singh@email.com', '9876543214');

-- Insert Transactions (some issued, some returned)
INSERT INTO Transactions (book_id, member_id, issue_date, due_date, return_date, status) VALUES
(1, 1, '2026-06-01', '2026-06-15', '2026-06-14', 'returned'),
(2, 2, '2026-06-05', '2026-06-19', NULL, 'issued'),
(4, 3, '2026-06-10', '2026-06-24', NULL, 'issued'),
(5, 1, '2026-06-01', '2026-06-15', NULL, 'issued'), -- This one is overdue
(7, 4, '2026-06-12', '2026-06-26', '2026-06-25', 'returned'),
(3, 5, '2026-06-15', '2026-06-29', NULL, 'issued');
