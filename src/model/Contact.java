package model;

public class Contact {
    private final String username;
    private String host;
    private int port;

    public Contact(String username, String host, int port) {
        this.username = username;
        this.host = host;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public String toStringWithUsername() {
        return username + " -> " + host + ":" + port;
    }

    @Override
    public String toString() {
        return host + ":" + port;
    }
}
