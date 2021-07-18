package view;

import cotroller.MailController;
import cotroller.UserConfig;
import sun.applet.Main;

public class MailMenu {
    private static int currentPortFocus;
    private static int currentHostFocus;

    {
        currentHostFocus = -1;
        currentPortFocus = -1;
    }

    public static String checkFocusCommand(String command) {
        if (UserConfig.getLoggedInUser() == null) return MainMenu.noLogin;
        return null;
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
