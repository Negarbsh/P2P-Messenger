package model;

public class Message {
    private final String text;
    private final String senderUsername;
    private final String receiverUsername;

    public Message(String text, String senderUsername, String receiverUsername) {
        this.text = text;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
    }

    public String getText() {
        return text;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    @Override
    public String toString() {
        return senderUsername + " -> " + text;
    }
}
