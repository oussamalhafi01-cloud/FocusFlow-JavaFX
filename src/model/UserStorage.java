package model;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class UserStorage {

    private static final String FILE = "users.txt";
    private HashMap<String, User> users = new HashMap<>();

    public UserStorage() {
        loadUsers();
    }

    public void saveUser(User newUser) {
        users.put(newUser.getUsername(), newUser);
        writeToFile(newUser);
    }

    public User findUser(String username) {
        return users.get(username);
    }

    public boolean exists(String username) {
        return users.containsKey(username);
    }

    public void updateUser(User user) {
        users.put(user.getUsername(), user);
        rewriteFile();
    }



    public void saveTasks(String username, LinkedList<Task> tasks) {
        String taskFile = username + "_tasks.txt";
        try (FileWriter fw = new FileWriter(taskFile, false)) {
            for (Task task : tasks) {
                fw.write(task.getName() + "," +
                        task.getStudyMinutes() + "," +
                        task.getPauseMinutes() + "," +
                        task.getStatus() + "\n");
            }
        } catch (IOException error) {
            System.out.println("Error saving tasks: " + error.getMessage());
        }
    }


    public LinkedList<Task> loadTasks(String username) {
        LinkedList<Task> tasks = new LinkedList<>();
        String taskFile = username + "_tasks.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(taskFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Task task = new Task(parts[0],
                            Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]));
                    task.setStatus(parts[3]);
                    tasks.add(task);
                }
            }
        } catch (IOException error) {
            System.out.println("Error loading tasks: " + error.getMessage());
        }

        return tasks;
    }



    public int updateAndGetStreak(String username) {
        String streakFile = username + "_streak.txt";
        LocalDate today = LocalDate.now();
        int streak = 1;

        try {
            File file = new File(streakFile);

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                LocalDate lastDate = LocalDate.parse(reader.readLine());
                int savedStreak = Integer.parseInt(reader.readLine());
                reader.close();

                if (lastDate.equals(today)) {
                    return savedStreak;
                } else if (lastDate.equals(today.minusDays(1))) {

                    streak = savedStreak + 1;
                } else {

                    streak = 1;
                }
            }


            FileWriter writer = new FileWriter(streakFile);
            writer.write(today.toString() + "\n" + streak);
            writer.close();

        } catch (IOException error) {
            System.out.println("Error with streak: " + error.getMessage());
        }

        return streak;
    }



    private String toLine(User user) {
        return user.getUsername() + "," + user.getPassword() + "," + user.getSecretQuestion() + "," + user.getSecretAnswer();
    }


    private void writeToFile(User user) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            fw.write(toLine(user) + "\n");
        } catch (IOException error) {
            System.out.println("Error saving: " + error.getMessage());
        }
    }


    private void rewriteFile() {
        try (FileWriter fw = new FileWriter(FILE, false)) {
            for (User user : users.values()) {
                fw.write(toLine(user) + "\n");
            }
        } catch (IOException error) {
            System.out.println("Error rewriting: " + error.getMessage());
        }
    }


    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    users.put(parts[0], new User(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException error) {
            System.out.println("Error loading users: " + error.getMessage());
        }
    }
}