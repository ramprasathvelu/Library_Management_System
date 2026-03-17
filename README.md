Library Management System

Design and Develop a Monolith Console based Library Management system by
understanding various use cases from the Borrower and Administrator point of view.

You are allowed to improvise and add more real-time use relevant cases to to each task
if possible.

Module A : Authentication and Welcome menu
Application must authenticate both Borrower and Administrator taking EmailID and
password and display a welcome menu based on their role.

Module B : Book Inventory Management
Admin can Add a Book,Modify Book details such as Avaialble Quantity and Delete a
Book. Admin will add other Admins and Borrowers into the System
a) Admin can view list of all Book sorted by Name or Available Quantity
b) Admin can search a Book by name or ISBN
c) Admin can manage Borrowers fine limit

Module C : Borrowing Book
Borrowers can view a list of all Books available, Select the Book by Name or ISBN and
checkout . Borrower can borrow maximum of 3 books at a time
a) Add a book into checkout cart or remove them
b) Cannot borrow same book twice
c) Can borrow a book only if a minimum security deposit of rupees 500 is maintained

Module D : Fine and Regulations
Each Borrower has deposited 1500 rupees Initially into the Book Bank as a Caution
deposit . The Amount will be refunded on closure of account . The Amount will be
reduced if there is any delay in return or loss of book
1. Books borrowed more than 15days will carry fine amount of 2Rs perday and
increases exponentially for every 10 days delay or 80% of book cost whichever is
lowest. For Simplicity get Return date from user in DD/MM/YYYY format
2. If book is lost then 50% of actual cost of book is considered as fine
3. If Member ship card is lost 10 Rs is collected as fine amount
Fine amount can either by paid by cash or reduced in security deposit
Regulations:
a) Borrower can extend the tenure up to two times only consecutively for a book.
b) All three book that can be borrowed parallelly must be different
c) Borrowers can either extend the tenure, exchange the book, Inform card lost or mark
a book as lost during every transaction

Module E : Reports
Admin can view below reports
1. Books with less quantity so that they can refill
2. Books that are not borrowed so far by any students
3. Books thats heavily borrowed so that they can check the condition of book
4. Students who has outstanding not returned book as on given date
5. Status of current book searched by ISBN number , display the details of student
who borred that book and when it can be expected to be returned in rack
Students can view:
1. List of previous fines (if any) and its reason
2. List of previous books borrow




Library Management

admin:
-add book (name,isbn,quantity,borrow count)
-modify book details (quantity)
-delete book
-add admin, borrower
-view list of all book (sort by name, quantity)
-search book (by name, isbn) AND status whether it is borrowed, details of student, when would he return
-manages fine limit
-view book with less quantity(ascending order)
-books not borrowed by students AND books borrowed heavily
-students who didn't returned book on given date

borrower:
-view all books
-view previous fines and its reason
-view previous borrows
-select book (by name,isbn) - borrow max 3
-adding in checkout cart or remove, cannot add same book, min sec dep 500

fine:
-1500 dep in account creation, refund on closure
-limit 15 days, more than that (rs 2 per day and exponential for every 10 days OR 80% of book cost) - min
-book lost - 50% book cost fine
-membership card lost, 10 rs fine
-paid in cash, sec dep
-borrower extend tenure upto 2 consec times