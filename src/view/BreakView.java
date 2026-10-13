package view;

import model.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.UserStorage;

import java.util.LinkedList;

public class BreakView {

    private Timeline timeline;
    private int secondsLeft;

    public void show(Stage stage, String username, LinkedList<Task> tasks, Task task, UserStorage userStorage) {


        Label screenTitle = new Label("You earned it! 🎉");
        screenTitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #aaa;");


        Label breakLabel = new Label("☕ Break Time!");
        breakLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");


        Label timerLabel = new Label();
        timerLabel.setStyle(
                "-fx-font-size: 52px; -fx-font-weight: bold; -fx-text-fill: #000000; " +
                        "-fx-background-color: white; -fx-background-radius: 12; -fx-padding: 20 40;"
        );
        secondsLeft = task.getPauseMinutes() * 60;
        updateTimerLabel(timerLabel, secondsLeft);


        Button skipButton = new Button("⏭ Skip Break");
        skipButton.setStyle("-fx-font-size: 13px; -fx-padding: 8 24; -fx-background-color: #eafaf1; -fx-text-fill: #27ae60;");



        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsLeft--;
            updateTimerLabel(timerLabel, secondsLeft);
            if (secondsLeft <= 0)
                finishBreak(stage, username, tasks, task, userStorage);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        skipButton.setOnAction(e -> finishBreak(stage, username, tasks, task, userStorage));


        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f0faf4;");
        layout.getChildren().addAll(screenTitle, breakLabel, timerLabel, skipButton);

        stage.setTitle("FocusFlow - Break");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }

    private void finishBreak(Stage stage, String username, LinkedList<Task> tasks, Task task, UserStorage userStorage) {
        timeline.stop();
        task.setStatus("Done");
        userStorage.saveTasks(username, tasks);
        new ProgressView().show(stage, username, tasks, userStorage);
    }

    private void updateTimerLabel(Label label, int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        label.setText(String.format("%02d:%02d", min, sec));
    }
}