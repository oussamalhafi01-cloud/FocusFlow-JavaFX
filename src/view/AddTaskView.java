package view;

import model.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.UserStorage;

import java.util.LinkedList;

public class AddTaskView {

    public void show(Stage stage, String username, LinkedList<Task> tasks, UserStorage userStorage) {


        Label title = new Label("➕ Add New Task");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e86ab;");


        Label nameLabel = new Label("Task name:");
        nameLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g. Math homework");
        nameField.setStyle("-fx-background-radius: 8; -fx-padding: 8;");


        Label timerLabel = new Label("Timer type:");
        timerLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");


        Button pomodoroButton = new Button("🍅 Pomodoro (25/5)");
        pomodoroButton.setStyle("-fx-background-color: #eaf4fb; -fx-text-fill: #2e86ab; -fx-padding: 8 16;");

        Button customButton = new Button("⚙️ Custom");
        customButton.setStyle("-fx-background-color: #eaf4fb; -fx-text-fill: #2e86ab; -fx-padding: 8 16;");

        HBox timerButtons = new HBox(10, pomodoroButton, customButton);


        Label studyLabel = new Label("Study minutes:");
        studyLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        TextField studyField = new TextField();
        studyField.setPromptText("e.g. 45");
        studyField.setStyle("-fx-background-radius: 8; -fx-padding: 8;");


        Label pauseLabel = new Label("Pause minutes:");
        pauseLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        TextField pauseField = new TextField();
        pauseField.setPromptText("e.g. 10");
        pauseField.setStyle("-fx-background-radius: 8; -fx-padding: 8;");


        VBox customFields = new VBox(8, studyLabel, studyField, pauseLabel, pauseField);
        customFields.setVisible(false);
        customFields.setManaged(false);


        boolean[] usePomodoro = {false};

        pomodoroButton.setOnAction(e -> {
            usePomodoro[0] = true;
            customFields.setVisible(false);
            customFields.setManaged(false);
            pomodoroButton.setStyle("-fx-background-color: #2e86ab; -fx-text-fill: white; -fx-padding: 8 16;");
            customButton.setStyle("-fx-background-color: #eaf4fb; -fx-text-fill: #2e86ab; -fx-padding: 8 16;");
        });

        customButton.setOnAction(e -> {
            usePomodoro[0] = false;
            customFields.setVisible(true);
            customFields.setManaged(true);
            customButton.setStyle("-fx-background-color: #2e86ab; -fx-text-fill: white;  -fx-padding: 8 16;");
            pomodoroButton.setStyle("-fx-background-color: #eaf4fb; -fx-text-fill: #2e86ab;  -fx-padding: 8 16;");
        });


        Label messageLabel = new Label("");
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px;");


        Button createButton = new Button("✅ Create Task");
        createButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 30; -fx-background-color: #27ae60; -fx-text-fill: white;");
        createButton.setMaxWidth(Double.MAX_VALUE);


        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-font-size: 12px; -fx-text-fill: #aaa; -fx-background-color: transparent;");



        createButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                messageLabel.setText("Please enter a task name! ❌");
                return;
            }

            int studyMin, pauseMin;
            if (usePomodoro[0]) {
                studyMin = 25;
                pauseMin = 5;
            } else {
                try {
                    studyMin = Integer.parseInt(studyField.getText().trim());
                    pauseMin = Integer.parseInt(pauseField.getText().trim());
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Please enter valid numbers! ❌");
                    return;
                }
            }

            tasks.add(new Task(name, studyMin, pauseMin));
            userStorage.saveTasks(username, tasks);
            new HomeView().show(stage, username, tasks, userStorage);
        });

        backButton.setOnAction(e -> new HomeView().show(stage, username, tasks, userStorage));



        VBox layout = new VBox(12);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(
                title,
                nameLabel, nameField,
                timerLabel, timerButtons,
                customFields,
                createButton,
                backButton,
                messageLabel
        );

        stage.setTitle("FocusFlow - Add Task");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }
}