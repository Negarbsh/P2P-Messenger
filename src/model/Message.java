package model;

public class Message {
    private final String text;
    private final String senderUsername;

    public Message(String text, String senderUsername) {
        this.text = text;
        this.senderUsername = senderUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }


    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return senderUsername + " -> " + text;
    }

}
