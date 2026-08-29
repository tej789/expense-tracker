Expense Tracker

The application allows users to manage spending categories, record  expenses, 
set monthly budgets, and view monthly spending summaries. 
Write operations are protected using JWT authentication.

Technologies Used
Spring Boot - 3.4.0
Java - 21
Spring Web
Spring Data JPA
Spring Security
JWT (JSON Web Token)
MySQL
Maven
Lombok
Postman

User Management
User Registration
User Login
Hashed Password Using BCrypt
JWT Authentication

Budget
Add Monthly Budget For Each Category
View Budgets For Each Category
View Budgets For All Category
Update Budgets For Each Category
Delete Budgets For Each Category

Transaction
Add Transactions for Each Category
View transactions
Update transactions
Delete transactions


API Endpoints

Register:
Method : POST
URL : http://localhost:8081/register

Login:
Method : POST
URL : http://localhost:8081/login

Budget Set:
Method : POST
URL : http://localhost:8081/budget

Budget Get:
Method : GET
URL : http://localhost:8081/budget?month=AUGUST&year=2026&category=Food

Budget Get All:
Method : GET
URL : http://localhost:8081/allbudgets?month=AUGUST&year=2026

Budget Update:
Method : PUT
URL : http://localhost:8081/budget

Budget Delete:
Method : DELETE
URL : http://localhost:8081/budget?month=AUGUST&year=2026&category=Food


Transaction Add:
Method : POST
URL : http://localhost:8081/transaction

Transaction Get:
Method : GET
URL : http://localhost:8081/transaction

Transaction Update:
Method : PUT
URL : http://localhost:8081/transaction/1

Transaction Delete:
Method : DELETE
URL : http://localhost:8081/transaction/1
