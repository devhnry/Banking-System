# Simple Java Banking Application

## Overview

This Java banking application demonstrates basic CRUD (Create, Read, Update, Delete) operations using file handling to manage user data. 
The application simulates a simple banking system where users can create accounts, view account details, update account information.
This project is designed to help practice file handling and object-oriented programming concepts in Java.

## Features

- Create a new user account and Login.
- CRUD operation to View Balance, Withdraw, Deposit.
- Update user account information.
- Store user data in a file (acting as a simple database)

## Requirements

- Java Development Kit (JDK) 8 or higher

## Project Structure

```
BankingSystem/
├── src/
│   ├── com/
│       ├── banking/
│           ├── Main.java
│           ├── User.java
│           ├── FileUserDataManager.java
├── userData.txt
└── README.md
```

## File Handling

User data is stored in a text file (`data/users.txt`). Each user's information is saved on a new line in the format:

```
userName,email,password,balance
```

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any changes.
