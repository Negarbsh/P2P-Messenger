package view;

import cotroller.MailController;
import cotroller.UserConfig;
import sun.applet.Main;

public class MailMenu {
    private static int currentPortFocus;
    private static String currentHostFocus;

    private final static String noHostFocus = "you must focus on a host before using this command";
    private final static String messageSendingError = "could not send message";

    {
        currentHostFocus = null;
        currentPortFocus = -1;
    }

    public static String checkFocusCommand(String command) {
        if (UserConfig.getLoggedInUser() == null)
            return MainMenu.noLogin;
        if (command.contains("--stop ")) {
            currentHostFocus = null;
            currentPortFocus = -1;
            return MainMenu.success;
        }
        if (command.contains("--start ")) {
            currentHostFocus = null;
            currentPortFocus = -1;
            if (!command.contains("--port"))
                return setFocusHost(command);
            else{
                if(!setFocusHost(command).equals(MainMenu.generalError)){
                    return setFocusPort(command);
                } else return MainMenu.generalError;
            }
        } else {
            if (currentHostFocus != null) return setFocusPort(command);
            else return noHostFocus;
        }
    }

    private static String setFocusHost(String command) {
        String host = MainMenu.getValueOfFlag(command, "host");
        if (host != null) {
            currentHostFocus = host;
            return (MainMenu.success);
        } else
            return (MainMenu.generalError);
    }

    private static String setFocusPort(String command) {
        int port = MainMenu.getIntValueOfFlag(command, "port");
        if (port != -1) {
            currentPortFocus = port;
            return MainMenu.success;
        } else return (MainMenu.generalError);
    }


    public static String checkSendCommand(String command) {
        if (UserConfig.getLoggedInUser() == null) return MainMenu.noLogin;
        int port = MainMenu.getIntValueOfFlag(command, "port");
        String host = MainMenu.getValueOfFlag(command, "host");
        String message = MainMenu.getMessageInCommand(command);
        if (message == null) return null;
        if (host == null) {
            host = currentHostFocus;
            if (port != -1)
                return MailController.sendMessage(port, host, message);
            else if (currentPortFocus != -1) {
                port = currentPortFocus;
                return MailController.sendMessage(port, host, message);
            } else return messageSendingError;
        } else {
            if (port == -1)
                port = currentPortFocus;
            if (port == -1)
                return messageSendingError;
            return MailController.sendMessage(port, host, message);
        }
    }
}
