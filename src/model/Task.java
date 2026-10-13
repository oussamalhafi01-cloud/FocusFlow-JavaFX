package model;

public class Task {
    private String name;
    private String status;
    private int studyMinutes;
    private int pauseMinutes;

    public Task(String name, int studyMinutes, int pauseMinutes) {
        this.name = name;
        this.status = "To Do";
        this.studyMinutes = studyMinutes;
        this.pauseMinutes = pauseMinutes;
    }

    public String getName() { return name; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getStudyMinutes() { return studyMinutes; }
    public int getPauseMinutes() { return pauseMinutes; }
}