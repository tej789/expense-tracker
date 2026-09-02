Expense Tracker

The application allows users to manage spending categories, record  expenses, 
set monthly budgets, and view monthly spending summaries. 
Write operations are protected using JWT authentication.

Technologies Used
Spring Boot - 3.4.0
Java - 21
Spring Web
Spring Data JPA
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

Admin
Get All Users
Get User By ID
Remove User By ID

Budget
Add Monthly Budget For Each Category
Get Budgets For Each Category
Get Budgets For All Category
Get Total Budget of Month
Update Budgets For Each Category
Delete Budgets For Each Category

Transaction
Add Transactions for Each Category
Get transactions
Get All Transaction Of a Month
Get Total Expense Of a Month
Update transactions
Delete transactions

Summary
Get All Category Monthly Summary
Get Individual Category Summary


Categories
Food
Travel
Housing
Entertainment 
Other


API Endpoints

Authentication
POST /register
POST /login

Register:
Method : POST
URL : http://localhost:8081/register
Authentication Not Required

Body example:
{
"username": "USER3",
"firstname" : "Tej",
"lastname" : "Patel",
"mail": "tej2005@gmail.com",
"phone":"8167815363",
"password" : "12345"
}

Login:
Method : POST
URL : http://localhost:8081/login
Authentication Not Required

Body example:
{
"username": "USER3",
"password": "12345"
}     

Admin
GET /users
GET /user/{id}
DELETE /user/{id}


Get All Users:
Method : GET
URL : http://localhost:8081/users
Only Admin Have Authority

Get User By ID:
Method : GET
URL : http://localhost:8081/user/1
Only Admin Have Authority

Delete User By ID:
Method : DELETE
URL : http://localhost:8081/user/1
Only Admin Have Authority


Budget
POST   /budget
GET    /budget?month=...&year=...&category=...
GET    /allbudgets?month=...&year=...
PUT    /budget
DELETE /budget?month=...&year=...&category=...
GET    /budget/total?month=...&year=...

Budget Set:
Method : POST
URL : http://localhost:8081/budget
Authentication Required

Body example:
{
"category": "Food",
"amount": 5000.0,
"month": "AUGUST",
"year": "2026"
}


Budget Get:
Method : GET
URL : http://localhost:8081/budget?month=AUGUST&year=2026&category=Food
Authentication Required

Budget Get All:
Method : GET
URL : http://localhost:8081/allbudgets?month=AUGUST&year=2026
Authentication Required

Budget Update:
Method : PUT
URL : http://localhost:8081/budget
Authentication Required

Body example:
{
"category": "Food",
"amount": 5000.0,
"month": "AUGUST",
"year": "2026"
}

Budget Delete:
Method : DELETE
URL : http://localhost:8081/budget?month=AUGUST&year=2026&category=Food
Authentication Required

Get Total Monthly Budget:
Method : GET
URL :
http://localhost:8081/budget/total?month=AUGUST&year=2026
Authentication Required


Transaction
POST   /transaction
GET    /transaction
GET    /transaction/month?month=...&year=...
PUT    /transaction/{id}
DELETE /transaction/{id}
GET    /transaction/total?month=...&year=...

Transaction Add:
Method : POST
URL : http://localhost:8081/transaction
Authentication Required

Body example:
{
"amount": 3000,
"description": "Food Bill",
"transactionDate" : "2026-08-29",
"type" : "EXPENSE",
"category" : "Food"
}

Transaction Get:
Method : GET
URL : http://localhost:8081/transaction
Authentication Required

Transaction By Month:
Method : GET
URL : http://localhost:8081/transaction/month?month=AUGUST&year=2026
Authentication Required

Transaction Update:
Method : PUT
URL : http://localhost:8081/transaction/1
Authentication Required

Body example:
{
"amount": 3000,
"description": "Food Bill",
"transactionDate" : "2026-08-29",
"type" : "EXPENSE",
"category" : "Food"
}

Transaction Delete:
Method : DELETE
URL : http://localhost:8081/transaction/1
Authentication Required

get total monthly Expense:
Method : GET
URL : http://localhost:8081/transaction/total?month=AUGUST&year=2026
Authentication Required


Summary
GET /summary?month=...&year=...
GET /summary?month=...&year=...&category=...

Monthly Summary:
Method : GET
URL : http://localhost:8081/summary?month=AUGUST&year=2026
Authentication Required

Monthly Summary By category:
Method : GET
URL : http://localhost:8081/summary?month=AUGUST&year=2026&category=Food
Authentication Required
