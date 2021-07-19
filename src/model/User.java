package model;

import cotroller.MailController;
import cotroller.UserConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class User {
    private final static ArrayList<User> allUsers;

    private final String username;
    private final String password;
    private final ArrayList<Contact> contacts;
    private int port;
    private boolean isPortSet;
    private final ArrayList<Message> messages;

    static {
        allUsers = new ArrayList<>();
    }

    {
        isPortSet = false;
        port = -1;
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

    public ArrayList<String> getSenders() {
        ArrayList<String> senders = new ArrayList<>();
        for (Message message : messages) {
            senders.add(message.getSenderUsername());
        }
        Set<String> set = new HashSet<>(senders);
        senders.clear();
        senders.addAll(set);
        return senders;
    }


    public ArrayList<Message> getCopyOfMessages() {
        return new ArrayList<>(messages);
    }

    public synchronized void addToMessages(String newMessage, String senderHostName) {
        String[] messageInfo = newMessage.split(" -> ");
        if (messageInfo.length != 3) return;
        String sender = messageInfo[0];
        String text = messageInfo[1];
        try {
            int senderPort = Integer.parseInt(messageInfo[2]);
            Message message = new Message(text, sender);
            messages.add(message);
            addContact(sender, senderHostName, senderPort);
        } catch (Exception ignored) {
        }
    }

    public Contact getContactByUsername(String username) {
        for (Contact contact : contacts) {
            if (contact.getUsername().equals(username)) return contact;
        }
        return null;
    }

    public void addContact(String username, String host, int port) {
        Contact previousContact = getContactByUsername(username);
        if (previousContact != null) {
            previousContact.setPort(port);
            previousContact.setHost(host);
        } else {
            Contact contact = new Contact(username, host, port);
            contacts.add(contact);
        }
    }

    public ArrayList<String> getContactsInfo() {
        ArrayList<String> contactsInfo = new ArrayList<>();
        for (Contact contact : contacts) {
            contactsInfo.add(contact.toStringWithUsername());
        }
        return contactsInfo;
    }

    public ArrayList<String> getMessagesFrom(String senderUsername) {
        ArrayList<String> messagesFromUsername = new ArrayList<>();
        for (Message message : messages) {
            if (message.getSenderUsername().equals(senderUsername))
                messagesFromUsername.add(message.getText());
        }
        return messagesFromUsername;
    }
}
