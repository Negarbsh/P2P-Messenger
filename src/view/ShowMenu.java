package view;

import cotroller.UserConfig;
import model.Message;
import model.User;

import java.util.ArrayList;

public class ShowMenu {
    private final static String nothingToShow = "no item is available";

    public static String checkCommand(String command) {
        User user = UserConfig.getLoggedInUser();
        if (user == null) return MainMenu.noLogin;
        ArrayList<String> senders = user.getSenders();

        if (command.contains("--senders ")) {
            if (command.contains("--count ")) {
                System.out.println(senders.size());
            } else {
                for (String sender : senders) {
                    System.out.println(sender);
                }
            }
        } else if (command.contains("--messages ")) {
            ArrayList<Message> messages = user.getCopyOfMessages();
            if (command.contains("--count "))
                System.out.println(messages.size());
            else {
                if(messages.isEmpty()) return nothingToShow;
                for (Message message : messages) {
                    System.out.println(message);
                }
            }
        }
        return null;
    }
}
