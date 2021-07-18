package model;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;

import java.util.ArrayList;

public class User {
    private final static ArrayList<User> allUsers;

    private final String username;
    private final String password;
    private final ArrayList<User> contacts;
    private int port;
    private boolean isPortSet;

    static {
        allUsers = new ArrayList<>();
    }

    {
        isPortSet = false;
        port = 0;
        contacts = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        allUsers.add(this);
    }

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.username.equals(username)) return user;
        }
        return null;
    }

    public String getPassword() {
        return this.password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        isPortSet = true;
        this.port = port;
    }

    public boolean isPortSet() {
        return isPortSet;
    }

    public void closePort() {
        isPortSet = false;
        port = 0;
    }
}
