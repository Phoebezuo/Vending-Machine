package VendingMachine.Processor;

import VendingMachine.Data.User;
import VendingMachine.DatabaseHandler;

import java.io.IOException;
import java.util.List;

public class UserProcessor {
    private static UserProcessor userProcessor;
    private final List<User> users;
    private User currentUser;

    private UserProcessor() throws IOException {
        users = DatabaseHandler.loadUserData();
        this.currentUser = this.users.get(0);
    }

    public static UserProcessor getInstance() {
        return userProcessor;
    }

    public static UserProcessor load() throws IOException {
        userProcessor = new UserProcessor();
        return userProcessor;
    }

    public boolean verifyUser(String username, String password) {
        // verify the username and password from the database
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }

    public boolean addUser(String username, String password) {
        // check the username is used or not
        return this.addUser(username, password, "CUSTOMER");
    }

    public boolean addUser(String username, String password, String type) {
        // check the username is used or not
        if (!hasUser(username)) {
            User newUser = new User(username, password, User.UserType.valueOf(type));
            users.add(newUser);
            return true;
        } else {
            return false;
        }
    }

    public boolean hasUser(String username) {
        // loop users to check the username is exists or not
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    public boolean setUsername(int id, String newUsername) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setUsername(newUsername);
                return true;
            }
        }
        return false;
    }

    public boolean setPassword(int id, String newPassword) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setPassword(newPassword);
                return true;
            }
        }
        return false;
    }

    public boolean setUserType(int id, String newType) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setType(User.UserType.valueOf(newType));
                return true;
            }
        }
        return false;
    }

    public void logoutUser() {
        this.currentUser = this.users.get(0);
    }

    public List<User> getUsers() {
        return this.users;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }
}
