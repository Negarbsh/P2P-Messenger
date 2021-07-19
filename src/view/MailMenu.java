package view;

import cotroller.MailController;
import cotroller.UserConfig;
import model.Contact;
import model.User;

public class MailMenu {
    private static int currentPortFocus;
    private static String currentHostFocus;

    private final static String noHostFocus = "you must focus on a host before using this command";
    private final static String messageSendingError = "could not send message";

    static {
        currentHostFocus = null;
        currentPortFocus = -1;
    }

    public static String checkFocusCommand(String command) {
        User user = UserConfig.getLoggedInUser();
        if (user == null)
            return MainMenu.noLogin;
        if (command.contains("--stop ")) {
            currentHostFocus = null;
            currentPortFocus = -1;
            return MainMenu.success;
        }
        if (command.contains("--start ")) {
            currentHostFocus = null;
            currentPortFocus = -1;
            if (command.contains("--username ")) {
                return focusOnContact(user.getContactByUsername(MainMenu.getValueOfFlag(command, "username")));
            } else if (!command.contains("--port "))
                return focusOnHost(MainMenu.getValueOfFlag(command, "host"));
            else {
                if (!focusOnHost(MainMenu.getValueOfFlag(command, "host")).equals(MainMenu.generalError)) {
                    return focusOnPort(MainMenu.getIntValueOfFlag(command, "port"));
                } else return MainMenu.generalError;
            }
        } else {
            if (currentHostFocus != null) return focusOnPort(MainMenu.getIntValueOfFlag(command, "port"));
            else return noHostFocus;
        }
    }

    private static String focusOnContact(Contact contact) {
        if (contact == null) return "no contact with such username was found";
        if (focusOnHost(contact.getHost()).equals(MainMenu.generalError)) return MainMenu.generalError;
        if (focusOnPort(contact.getPort()).equals(MainMenu.generalError)) return MainMenu.generalError;
        return MainMenu.success;
    }

    private static String focusOnHost(String host) {

        if (host != null) {
            currentHostFocus = host;
            return (MainMenu.success);
        } else
            return (MainMenu.generalError);
    }

    private static String focusOnPort(int port) {
        if (port != -1) {
            currentPortFocus = port;
            return MainMenu.success;
        } else return (MainMenu.generalError);
    }


    public static String checkSendCommand(String command) {
        User user = UserConfig.getLoggedInUser();
        if (user == null)
            return MainMenu.noLogin;
        if (command.contains("--username ")) {
            String contactUsername = MainMenu.getValueOfFlag(command, "username");
            String message = MainMenu.getMessageInCommand(command);
            if (message == null || contactUsername == null) return MainMenu.generalError;
            return MailController.sendToContact(user, message, contactUsername);
        }
        int port = MainMenu.getIntValueOfFlag(command, "port");
        String host = MainMenu.getValueOfFlag(command, "host");
        String message = MainMenu.getMessageInCommand(command);
        if (message == null) return null;
        if (host == null) {
            host = currentHostFocus;
            if (port != -1)
                return MailController.sendMessage(port, host, user.getPort(), message);
            else if (currentPortFocus != -1) {
                port = currentPortFocus;
                return MailController.sendMessage(port, host, user.getPort(), message);
            } else return messageSendingError;
        } else {
            if (port == -1)
                port = currentPortFocus;
            if (port == -1)
                return messageSendingError;
            return MailController.sendMessage(port, host, user.getPort(), message);
        }
    }
}
