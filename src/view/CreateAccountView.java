package view;

import model.User;
import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateAccountView {

    private LoginController controller = new LoginController();

    public void show(Stage stage) {


        Label title = new Label("Create Account 📝");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2e86ab;");

        Label subtitle = new Label("Join FocusFlow and start studying smarter.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaa;");


        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Choose a username");
        usernameField.setStyle("-fx-background-radius: 8;  -fx-padding: 8;");


        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Choose a password");
        passwordField.setStyle("-fx-background-radius: 8;  -fx-padding: 8;");


        Label cityLabel = new Label("What city were you born in?");
        cityLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        TextField cityField = new TextField();
        cityField.setPromptText("Your answer");
        cityField.setStyle("-fx-background-radius: 8; -fx-padding: 8;");


        Label messageLabel = new Label("");
        messageLabel.setStyle("-fx-font-size: 12px;");


        Button createAccountButton = new Button("✅ Create Account");
        createAccountButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 30; -fx-background-color: #2e86ab; -fx-text-fill: white; -fx-background-radius: 8;");
        createAccountButton.setMaxWidth(Double.MAX_VALUE);


        Button backButton = new Button("← Back to Login");
        backButton.setStyle("-fx-font-size: 12px; -fx-text-fill: #aaa; -fx-background-color: transparent;");


        createAccountButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String city = cityField.getText();

            if (username.isEmpty() || password.isEmpty() || city.isEmpty()) {
                messageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c;");
                messageLabel.setText("Please fill in all fields! ❌");
                return;
            }

            if (controller.createAccount(new User(username, password, "What city were you born in ?", city))) {
                messageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60;");
                messageLabel.setText("Account created! ✅ Go back and login.");
            } else {
                messageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c;");
                messageLabel.setText("Username already taken! ❌");
            }
        });

        backButton.setOnAction(e -> new LoginView().show(stage));




        VBox layout = new VBox(12);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(
                title,
                subtitle,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                cityLabel, cityField,
                createAccountButton,
                backButton,
                messageLabel
        );

        stage.setTitle("FocusFlow - Register");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }
}