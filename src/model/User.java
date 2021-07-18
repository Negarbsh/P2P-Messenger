package model;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import cotroller.MailController;
import cotroller.UserConfig;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private final static ArrayList<User> allUsers;

    private final String username;
    private final String password;
    private final ArrayList<User> contacts;
    private int port;
    private boolean isPortSet;
    private ArrayList<Message> messages;

    static {
        allUsers = new ArrayList<>();
    }

    {
        isPortSet = false;
        port = 0;
        contacts = new ArrayList<>();
        messages = new ArrayList<>();
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

    public String getUsername() {
        return this.username;
    }

    public ArrayList<String> getSenders(){
        ArrayList<String> senders = new ArrayList<>();
        for (Message message : messages) {
            senders.add(message.getSenderUsername());
        }
        return senders;
    }


    public ArrayList<Message> getMessages() {
        return messages;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        isPortSet = true;
        this.port = port;
        if (UserConfig.getLoggedInUser() == this)
            MailController.setShouldCheckNewMessages(true);
    }

    public boolean isPortSet() {
        return isPortSet;
    }

    public void closePort() {
        isPortSet = false;
        port = 0;
    }

    public void addToMessages(String newMessage) {
        String[] messageInfo = newMessage.split(" -> ");
        if (messageInfo.length != 2) return;
        String sender = messageInfo[0];
        String text = messageInfo[1];
        Message message = new Message(text,sender,this.username);
        messages.add(message);
    }

}
