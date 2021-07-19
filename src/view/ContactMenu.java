package view;

import cotroller.UserConfig;
import model.User;

public class ContactMenu {
    public static String checkCommand(String command) {
        User user = UserConfig.getLoggedInUser();
        if (user == null) return MainMenu.noLogin;
        if (!command.contains("--link ")) return MainMenu.generalError;
        String username = MainMenu.getValueOfFlag(command, "username");
        String host = MainMenu.getValueOfFlag(command, "host");
        int port = MainMenu.getIntValueOfFlag(command, "port");
        if (username == null || host == null || port == -1) return MainMenu.generalError;
        user.addContact(username, host, port);
        return MainMenu.success;
    }
}
