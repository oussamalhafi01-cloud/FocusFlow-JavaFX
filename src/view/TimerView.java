package view;

import model.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.UserStorage;

import java.util.LinkedList;

public class TimerView {

    private Timeline timeline;
    private int secondsLeft;

    public void show(Stage stage, String username, LinkedList<Task> tasks, Task task, UserStorage userStorage) {


        Label screenTitle = new Label("⏱️ Study Time");
        screenTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #aaa;");


        Label taskName = new Label(task.getName());
        taskName.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e86ab;");


        Label timerLabel = new Label();
        timerLabel.setStyle(
                "-fx-font-size: 52px; -fx-font-weight: bold; -fx-text-fill: #000000; " +
                        "-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20 40;"
        );
        secondsLeft = task.getStudyMinutes() * 60;
        updateTimerLabel(timerLabel, secondsLeft);



        Button startButton = new Button("▶ Start");
        startButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 28; -fx-background-color: #2eab38; -fx-text-fill: white;");

        Button pauseButton = new Button("⏸ Pause");
        pauseButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 28; -fx-background-color: #f48728; -fx-text-fill: white;");
        pauseButton.setDisable(true);

        HBox timerButtons = new HBox(10, startButton, pauseButton);
        timerButtons.setAlignment(Pos.CENTER);

        Button backButton = new Button("← Back");
        backButton.setStyle("-fx-font-size: 12px; -fx-text-fill: #aaa; -fx-background-color: transparent;");

        HBox bottomBar = new HBox(backButton);
        bottomBar.setAlignment(Pos.BOTTOM_LEFT);



        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsLeft--;
            updateTimerLabel(timerLabel, secondsLeft);

            if (secondsLeft <= 0) {
                timeline.stop();
                if (task.getPauseMinutes() > 0) {
                    new BreakView().show(stage, username, tasks, task, userStorage);
                } else {
                    task.setStatus("Done");
                    userStorage.saveTasks(username, tasks);
                    new ProgressView().show(stage, username, tasks, userStorage);
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);


        startButton.setOnAction(e -> {
            timeline.play();
            startButton.setDisable(true);
            pauseButton.setDisable(false);
        });

        pauseButton.setOnAction(e -> {
            timeline.pause();
            startButton.setDisable(false);
            pauseButton.setDisable(true);
        });

        backButton.setOnAction(e -> {
            timeline.stop();
            new HomeView().show(stage, username, tasks, userStorage);
        });



        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(screenTitle, taskName, timerLabel, timerButtons, bottomBar);

        stage.setTitle("FocusFlow - Timer");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }

    private void updateTimerLabel(Label label, int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        label.setText(String.format("%02d:%02d", min, sec));
    }
}