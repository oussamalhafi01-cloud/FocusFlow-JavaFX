package view;

import model.Task;
import model.UserStorage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;

public class ProgressView {

    public void show(Stage stage, String username, LinkedList<Task> tasks, UserStorage userStorage) {


        Label title = new Label("📊 Your Progress");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2e86ab;");


        long completedCount = 0;
        int totalMinutes = 0;
        for (Task t : tasks) {
            if (t.getStatus().equals("Done")) {
                completedCount++;
                totalMinutes += t.getStudyMinutes();
            }
        }



        Label sessionsTitle = new Label("✅ Sessions Completed");
        sessionsTitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        Label sessionsValue = new Label(String.valueOf(completedCount));
        sessionsValue.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2e86ab;");
        VBox sessionsCard = new VBox(5, sessionsTitle, sessionsValue);
        sessionsCard.setAlignment(Pos.CENTER);
        sessionsCard.setPadding(new Insets(15));
        sessionsCard.setStyle("-fx-background-color: #eaf4fb; -fx-background-radius: 10;");



        Label timeTitle = new Label("⏱️ Total Study Time");
        timeTitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        Label timeValue = new Label(totalMinutes + " minutes");
        timeValue.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
        VBox timeCard = new VBox(5, timeTitle, timeValue);
        timeCard.setAlignment(Pos.CENTER);
        timeCard.setPadding(new Insets(15));
        timeCard.setStyle("-fx-background-color: #eafaf1; -fx-background-radius: 10;");



        int streak = userStorage.updateAndGetStreak(username);
        Label streakTitle = new Label("🔥 Current Streak");
        streakTitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");
        Label streakValue = new Label(streak + " day(s)");
        streakValue.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #e67e22;");
        VBox streakCard = new VBox(5, streakTitle, streakValue);
        streakCard.setAlignment(Pos.CENTER);
        streakCard.setPadding(new Insets(15));
        streakCard.setStyle("-fx-background-color: #fef9e7; -fx-background-radius: 10;");


        String message;
        if (completedCount == 0)      message = "Start your first session! 💪";
        else if (completedCount == 1) message = "Good start! Keep going! 😊";
        else if (completedCount == 2) message = "Great work! 🚀";
        else                          message = "You're on fire! Amazing focus! 🔥";


        Label motivationLabel = new Label(message);
        motivationLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #888; -fx-font-style: italic;");



        Button homeButton = new Button("🏠 Go Home");
        homeButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 30; -fx-background-color: #2e86ab; -fx-text-fill: white;");
        homeButton.setOnAction(e -> new HomeView().show(stage, username, tasks, userStorage));


        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #f9f9f9;");
        layout.getChildren().addAll(title, sessionsCard, timeCard, streakCard, motivationLabel, homeButton);

        stage.setTitle("FocusFlow - Progress");
        stage.setScene(new Scene(layout, 500, 600));
        stage.show();
    }
}