package controller;

import model.User;
import model.UserStorage;

public class LoginController {

    private UserStorage storage = new UserStorage();

    public boolean login(String username, String password) {
        User user = storage.findUser(username);
        if (user == null) return false;
        return user.getPassword().equals(password);
    }
    public boolean createAccount(User newUser) {
        if (storage.exists(newUser.getUsername())) return false;
        storage.saveUser(newUser);
        return true;
    }
    public boolean resetPassword(String username, String answer, String newPassword) {
        User user = storage.findUser(username);
        if (user == null) return false;
        if (!user.getSecretAnswer().equals(answer)) return false;
        user.setPassword(newPassword);
        storage.updateUser(user);
        return true;
    }
}