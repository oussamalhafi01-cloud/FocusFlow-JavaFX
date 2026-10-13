package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ForgotPasswordView {

    private LoginController controller = new LoginController();

    public void show(Stage stage) {


        Label title = new Label("Forgot Password? 🔑");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2e86ab;");

        Label subtitle = new Label("Answer your secret question to reset.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaa;");


        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-background-radius: 8; -fx-padding: 8;");


        Label answerLabel = new Label("What city were you born in?");
        answerLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        TextField answerField = new TextField();
        answerField.setPromptText("Your answer");
        answerField.setStyle("-fx-background-radius: 8;  -fx-padding: 8;");


        Label newPasswordLabel = new Label("New Password:");
        newPasswordLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Choose a new password");
        newPasswordField.setStyle("-fx-background-radius: 8;  -fx-padding: 8;");


        Label messageLabel = new Label("");
        messageLabel.setStyle("-fx-font-size: 12px;");


        Button resetButton = new Button("🔑 Reset Password");
        resetButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 30; -fx-background-color: #2e86ab; -fx-text-fill: white;");
        resetButton.setMaxWidth(Double.MAX_VALUE);


        Button backButton = new Button("← Back to Login");
        backButton.setStyle("-fx-font-size: 12px; -fx-text-fill: #aaa; -fx-background-color: transparent;");


        resetButton.setOnAction(e -> {
            String username = usernameField.getText();
            String answer = answerField.getText();
            String newPassword = newPasswordField.getText();

            if (username.isEmpty() || answer.isEmpty() || newPassword.isEmpty()) {
                messageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c;");
                messageLabel.setText("Please fill in all fields! ❌");
                return;
            }

            if (controller.resetPassword(username, answer, newPassword)) {
                messageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60;");
                messageLabel.setText("Password reset! ✅ Go back and login.");
            } else {
                messageLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c;");
                messageLabel.setText("Wrong username or answer! ❌");
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
                answerLabel, answerField,
                newPasswordLabel, newPasswordField,
                resetButton,
                backButton,
                messageLabel
        );

        stage.setTitle("FocusFlow - Forgot Password");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }
}