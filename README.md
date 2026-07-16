# 📚 Library Management System

A **console-based monolithic Library Management System** developed to manage library operations for both **Administrators** and **Borrowers**. The system provides authentication, inventory management, book borrowing, fine management, and reporting features while following real-world library regulations.

---

## 📖 Project Overview

This application allows:

- Secure authentication for **Administrators** and **Borrowers**
- Complete book inventory management
- Book borrowing and return management
- Security deposit and fine calculation
- Library reports and analytics
- Borrowing history and fine history tracking

---

## 🚀 Features

### 🔐 Module A - Authentication

- Login using **Email ID** and **Password**
- Role-based authentication
  - Administrator
  - Borrower
- Display role-specific dashboard after successful login

---

### 📚 Module B - Book Inventory Management (Administrator)

#### Book Management

- Add a new book
- Modify book details
  - Available quantity
- Delete a book

#### User Management

- Add new Administrator
- Add new Borrower

#### Book Search & Listing

- View all books
- Sort books by
  - Name
  - Available Quantity
- Search books by
  - Book Name
  - ISBN

#### Fine Management

- Manage borrower fine limits

---

### 📕 Module C - Borrowing Management (Borrower)

Borrowers can:

- View all available books
- Search books using
  - Name
  - ISBN
- Add books to checkout cart
- Remove books from checkout cart
- Checkout books

#### Borrowing Rules

- Maximum **3 books** can be borrowed simultaneously.
- Same book cannot be borrowed twice.
- Minimum **₹500 security deposit** must remain after transactions.
- All borrowed books must be different.

---

### 💰 Module D - Fine Management & Regulations

## Security Deposit

- Every borrower deposits **₹1500** during account creation.
- Deposit is refundable when the account is closed.
- Deposit is reduced if fines are unpaid.

### Fine Rules

#### Late Return

- First **15 days**: No fine.
- After 15 days:
  - ₹2 per day.
  - Fine increases exponentially for every additional 10-day delay.
  - Maximum fine is **80% of the book cost** (whichever is lower).

> Return date is entered by the user in **DD/MM/YYYY** format.

#### Lost Book

- Fine = **50% of the actual book price**

#### Lost Membership Card

- Flat fine of **₹10**

#### Fine Payment

Fine can be paid by:

- Cash
- Deducting from Security Deposit

---

## Borrowing Regulations

A borrower can:

- Extend borrowing period **up to 2 consecutive times**
- Exchange a borrowed book
- Report membership card as lost
- Report a borrowed book as lost

---

### 📊 Module E - Reports

### Administrator Reports

- Books with low available quantity
- Books never borrowed
- Frequently borrowed books
- Students with overdue books on a given date
- Book status using ISBN including:
  - Borrower details
  - Expected return date

### Borrower Reports

- Previous borrowed books
- Previous fines with reasons

---

# 🧩 Functional Requirements

## Administrator

### Book Management

- Add Book
- Modify Book Quantity
- Delete Book

### User Management

- Add Administrator
- Add Borrower

### Inventory

- View all books
- Sort books by:
  - Name
  - Quantity
- Search books by:
  - Name
  - ISBN

### Reports

- Low stock books
- Books never borrowed
- Frequently borrowed books
- Overdue borrowers
- Book borrowing status

### Fine Management

- Manage borrower fine limits

---

## Borrower

### Book Operations

- View available books
- Search books
- Add to checkout cart
- Remove from checkout cart
- Borrow books

### History

- View borrowing history
- View fine history with reasons

### Regulations

- Maximum 3 borrowed books
- Cannot borrow duplicate books
- Maintain minimum ₹500 security deposit
- Extend borrowing period (maximum twice)
- Exchange books
- Report lost books
- Report lost membership card

---

# 📂 Suggested Project Structure

```
LibraryManagementSystem/
│
├── Main.java
├── Authentication/
├── Models/
│   ├── Book.java
│   ├── Borrower.java
│   ├── Admin.java
│   ├── Transaction.java
│   └── Fine.java
│
├── Services/
│   ├── BookService.java
│   ├── BorrowService.java
│   ├── FineService.java
│   ├── ReportService.java
│   └── AuthenticationService.java
│
├── Utils/
│   ├── DateUtil.java
│   └── Validation.java
│
└── README.md
```

---

# 📌 Business Rules

- Initial Security Deposit: **₹1500**
- Minimum Deposit Required: **₹500**
- Maximum Borrow Limit: **3 books**
- Borrow Duration: **15 days**
- Late Fine: **₹2/day**
- Fine increases exponentially every additional **10 days**
- Maximum Late Fine: **80% of book price**
- Lost Book Fine: **50% of book price**
- Lost Card Fine: **₹10**
- Borrow extension allowed: **2 consecutive times**

---

# 🛠️ Technologies

- Java
- Object-Oriented Programming (OOP)
- Collections Framework
- File Handling (optional)
- Date & Time API
- Console-Based User Interface

---

# 🔮 Future Enhancements

- Database integration (MySQL/PostgreSQL)
- Spring Boot REST API
- Web-based UI
- Email notifications
- Barcode/QR code support
- Online reservation system
- Role-based authorization using JWT
- Admin dashboard with analytics

---

# 📄 License

This project is developed for educational purposes and can be extended into a full-fledged Library Management System.
