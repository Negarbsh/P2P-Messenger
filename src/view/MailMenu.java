package view;

import cotroller.MailController;
import cotroller.UserConfig;

public class MailMenu {
    private static int currentPortFocus;
    //    private static int currentHostFocus;
    private static String currentHostFocus;

    private static String noHostFocus = "you must focus on a host before using this command";

    {
//        currentHostFocus = -1;
        currentHostFocus = null;
        currentPortFocus = -1;
    }

    public static String checkFocusCommand(String command) {
        if (UserConfig.getLoggedInUser() == null)
            return MainMenu.noLogin;
        if (command.contains("--stop ")) {
            currentHostFocus = null;
            currentPortFocus = -1;
            return null;
        }
        if (command.contains("--start ")) {
            currentHostFocus = null;
            currentPortFocus = -1;
            setFocusHost(command);
        } else {
            if (currentHostFocus != null) setFocusPort(command);
            else return noHostFocus;
        }
        command = command.concat("--port " + currentPortFocus + " --host " + currentHostFocus + " ");
        return checkSendCommand(command);
    }

    private static void setFocusHost(String command) {
        currentHostFocus = MainMenu.getValueOfFlag(command, "host");
    }

    private static void setFocusPort(String command) {
        currentPortFocus = MainMenu.getIntValueOfFlag(command, "port");
    }


    public static String checkSendCommand(String command) {
        if (UserConfig.getLoggedInUser() == null) return MainMenu.noLogin;
        int port = MainMenu.getIntValueOfFlag(command, "port");
        String host = MainMenu.getValueOfFlag(command, "host");
        String message = MainMenu.getValueOfFlag(command, "message");
        if (port == -1 || host == null || message == null) return null;
        else return MailController.sendMessage(port, host, message);
    }
}
