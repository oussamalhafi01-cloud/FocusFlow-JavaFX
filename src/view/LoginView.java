package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Task;
import model.UserStorage;

import java.util.LinkedList;

public class LoginView {

    private LoginController controller = new LoginController();

    public void show(Stage stage) {


        Label title = new Label("FocusFlow 📚");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2e86ab;");

        Label subtitle = new Label("Stay focused. Stay on track.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaa;");


        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-background-radius: 8 ; -fx-padding: 8;");


        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-background-radius: 8; -fx-padding: 8;");


        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 30; -fx-background-color: #2e86ab; -fx-text-fill: white; -fx-background-radius: 8;");
        loginButton.setMaxWidth(Double.MAX_VALUE);

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setStyle("-fx-font-size: 13px; -fx-padding: 8 20; -fx-background-color: #eaf4fb; -fx-text-fill: #2e86ab; -fx-background-radius: 8;");
        createAccountButton.setMaxWidth(Double.MAX_VALUE);

        Button forgotButton = new Button("Forgot Password?");
        forgotButton.setStyle("-fx-font-size: 12px; -fx-text-fill: #aaa; -fx-background-color: transparent;");


        Label messageLabel = new Label("");
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");


        Label credentialsLabel = new Label("Test account — username: Aya | password: 1234");
        credentialsLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #8f8b8b;");


        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please fill in all fields! ❌");
                return;
            }

            if (controller.login(username, password)) {
                UserStorage userStorage = new UserStorage();
                LinkedList<Task> tasks = userStorage.loadTasks(username);
                new HomeView().show(stage, username, tasks, userStorage);
            } else {
                messageLabel.setText("Wrong username or password! ❌");
            }
        });

        createAccountButton.setOnAction(event -> new CreateAccountView().show(stage));

        forgotButton.setOnAction(event -> new ForgotPasswordView().show(stage));


        VBox layout = new VBox(12);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(
                title,
                subtitle,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                loginButton,
                createAccountButton,
                forgotButton,
                messageLabel,
                credentialsLabel
        );

        stage.setTitle("FocusFlow - Login");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }
}