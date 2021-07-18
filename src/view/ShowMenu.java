package view;

import cotroller.UserConfig;
import model.Message;
import model.User;

import java.util.ArrayList;
import java.util.HashMap;

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
            if (command.contains("--count "))
                System.out.println(user.getMessages().size());
            else {
                for (Message message : user.getMessages()) {
                    System.out.println(message);
                }
            }
        }

        return null;
    }
}
