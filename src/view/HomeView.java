package view;

import model.Task;
import model.UserStorage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.LinkedList;

public class HomeView {

    public void show(Stage stage, String username, LinkedList<Task> tasks, UserStorage userStorage) {


        Label welcome = new Label("Welcome back, " + username + "! 👋");
        welcome.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e86ab;");

        long doneTasks = tasks.stream().filter(t -> t.getStatus().equals("Done")).count();
        Label counterLabel = new Label(tasks.size() + " tasks  •  " + doneTasks + " completed");
        counterLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #aaa;");


        Button addTaskButton = new Button("+ Add Task");
        addTaskButton.setStyle("-fx-font-size: 13px; -fx-padding: 8 24; -fx-background-color: #2e86ab; -fx-text-fill: white; -fx-background-radius: 8;");
        addTaskButton.setMaxWidth(Double.MAX_VALUE);
        addTaskButton.setOnAction(e -> new AddTaskView().show(stage, username, tasks, userStorage));


        VBox taskList = new VBox(10);
        taskList.setPadding(new Insets(5));
        refreshTaskList(taskList, stage, username, tasks, userStorage, counterLabel);

        ScrollPane scrollPane = new ScrollPane(taskList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #ffffff; -fx-background: #ffffff;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);


        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 12px; -fx-text-fill: #e74c3c; -fx-background-color: transparent;");
        logoutButton.setOnAction(e -> new LoginView().show(stage));

        HBox bottomBar = new HBox(logoutButton);
        bottomBar.setAlignment(Pos.BOTTOM_LEFT);


        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(welcome, counterLabel, addTaskButton, scrollPane, bottomBar);

        stage.setTitle("FocusFlow - Home");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }

    private void refreshTaskList(VBox taskList, Stage stage, String username,
                                 LinkedList<Task> tasks, UserStorage userStorage, Label counterLabel) {
        taskList.getChildren().clear();

        for (Task task : tasks) {


            Label nameLabel = new Label(task.getName());
            nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");


            boolean isDone = task.getStatus().equals("Done");
            String color = isDone ? "#27ae60" : "#2e86ab";
            String text  = isDone ? "✅ Done" : "🔵 Pending";
            Label statusLabel = new Label(text);
            statusLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #ffffff; -fx-background-color: " + color + "; -fx-background-radius: 6; -fx-padding: 2 8;");


            Label infoLabel = new Label("⏱ " + task.getStudyMinutes() + " min study  •  ☕ " + task.getPauseMinutes() + " min break");
            infoLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #aea9a9;");

            VBox taskInfo = new VBox(4, nameLabel, statusLabel, infoLabel);
            HBox.setHgrow(taskInfo, Priority.ALWAYS);

            Button startButton = new Button("▶");
            startButton.setStyle("-fx-background-color: #eaf4fb ; -fx-text-fill:#2eab38   ;  -fx-padding: 6 12;");
            startButton.setDisable(isDone);
            startButton.setOnAction(e -> new TimerView().show(stage, username, tasks, task, userStorage));

            Button deleteButton = new Button("🗑");
            deleteButton.setStyle("-fx-background-color: #fdecea; -fx-padding: 6 12;");
            deleteButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Task");
                alert.setHeaderText("Delete \" " + task.getName() +"\"?");
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        tasks.remove(task);
                        userStorage.saveTasks(username, tasks);
                        long done = tasks.stream().filter(t -> t.getStatus().equals("Done")).count();
                        counterLabel.setText(tasks.size() + " tasks  •  " + done + " completed");
                        refreshTaskList(taskList, stage, username, tasks, userStorage, counterLabel);
                    }
                });
            });



            HBox taskRow = new HBox(10, taskInfo,  startButton, deleteButton);
            taskRow.setAlignment(Pos.CENTER_LEFT);
            taskRow.setPadding(new Insets(12));
            taskRow.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, #ddd, 4, 0, 0, 2);");

            taskList.getChildren().add(taskRow);
        }
    }
}