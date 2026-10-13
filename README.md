# FocusFlow – JavaFX Desktop Application

A desktop application developed in Java and JavaFX to help users organize their tasks, manage study sessions, and track their productivity.

## Features

- **User Authentication:** User registration and login functionality.
- **Password Recovery:** Password reset using a security question.
- **Task Management:** Create, manage, and save tasks.
- **Pomodoro Timer:** Manage focused work sessions and breaks.
- **Progress Tracking:** Track completed tasks, productivity, and daily streaks.
- **Local Data Storage:** Save user information and task data locally.

## Technologies Used

- Java
- JavaFX
- Object-Oriented Programming (OOP)
- Model-View-Controller (MVC)
- File Handling
- IntelliJ IDEA

## Project Structure

```text
src/
├── controller/
│   └── LoginController.java
├── model/
│   ├── Task.java
│   ├── User.java
│   └── UserStorage.java
├── view/
│   ├── AddTaskView.java
│   ├── BreakView.java
│   ├── CreateAccountView.java
│   ├── ForgotPasswordView.java
│   ├── HomeView.java
│   ├── LoginView.java
│   ├── ProgressView.java
│   └── TimerView.java
└── Main.java
```

## How to Run

1. Clone or download this repository.
2. Open the project in IntelliJ IDEA.
3. Configure a compatible Java JDK and JavaFX SDK.
4. Run `Main.java`.

**Note:** Depending on your JavaFX setup, additional library and runtime configuration may be required.

## Security Notice

This is an educational project developed for learning purposes. User credentials and security question answers are stored locally without secure password hashing. The authentication system is not intended for production use.

## Project Context

Academic Java desktop application project focused on object-oriented programming, GUI development, application architecture, and file-based data management.
