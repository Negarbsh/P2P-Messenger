package view;

import cotroller.UserConfig;
import model.Contact;
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
            handleSendersCommand(command, senders);
        } else if (command.contains("--messages ")) {
            if (command.contains("--from ")) {
                showMessagesFromSender(command, user);
            } else {
                ArrayList<Message> messages = user.getCopyOfMessages();
                if (command.contains("--count "))
                    System.out.println(messages.size());
                else {
                    if (messages.isEmpty()) return nothingToShow;
                    for (Message message : messages) {
                        System.out.println(message);
                    }
                }
            }
        } else if (command.contains("--contacts ")) {
            ArrayList<String> contacts = user.getContactsInfo();
            for (String contact : contacts) {
                System.out.println(contact);
            }
        } else if (command.contains("--contact ")) {
            String contactName = MainMenu.getValueOfFlag(command, "contact");
            Contact contact = user.getContactByUsername(contactName);
            if (contact == null) System.out.println("no contact with such username was found");
            else System.out.println(contact);
        }
        return null;
    }

    private static void showMessagesFromSender(String command, User user) {
        String senderUsername = MainMenu.getValueOfFlag(command, "from");
        ArrayList<String> messages = user.getMessagesFrom(senderUsername);
        if (command.contains("--count ")) {
            System.out.println(messages.size());
        } else {
            for (String message : messages) {
                System.out.println(message);
            }
        }
    }

    private static void handleSendersCommand(String command, ArrayList<String> senders) {
        if (command.contains("--count ")) {
            System.out.println(senders.size());
        } else {
            for (String sender : senders) {
                System.out.println(sender);
            }
        }
    }
}
