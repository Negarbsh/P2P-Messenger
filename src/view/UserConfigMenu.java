package view;

import cotroller.UserConfig;
import model.User;
import sun.applet.Main;

import java.time.chrono.MinguoChronology;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserConfigMenu {
    private final static String generalError = MainMenu.generalError;
    private final static String success = MainMenu.success;
    private final static String unavailableUserName = "this username is unavailable";
    private final static String invalidUser = "user not found";
    private final static String wrongPassword = "incorrect password";
    private final static String logoutError = MainMenu.noLogin;

    public static String checkCommand(String command) {
        if (command.equals("--logout ")) {
            if (UserConfig.getLoggedInUser() != null){
                UserConfig.logout();
                return success;
            }
            else return logoutError;
        } else if (command.contains("--create ")) {
            String username = MainMenu.getValueOfFlag(command, "username");
            String password = MainMenu.getValueOfFlag(command, "password");
            if (username == null || password == null) return generalError;
            if (!isUsernameFine(username)) return unavailableUserName;
            else {
                new User(username, password);
                return success;
            }
        } else if (command.contains("--login")) {
            String username = MainMenu.getValueOfFlag(command, "username");
            String password = MainMenu.getValueOfFlag(command, "password");
            if (username == null || password == null) return generalError;
            User user = User.getUserByUsername(username);
            if (user == null) return invalidUser;
            if (!user.getPassword().equals(password)) return wrongPassword;
            else{
                UserConfig.login(user); //todo : should I check if the previous user has logged out?
                return success;
            }
        }
        return null;
    }

    private static boolean isUsernameFine(String username) {
        if (User.getUserByUsername(username) != null) return false;
        username = username.concat(" ");
        username = " ".concat(username);
        Pattern pattern = Pattern.compile(" [\\w\\d-_]+ ");
        Matcher matcher = pattern.matcher(username);
        return matcher.find(); //todo fine?
    }
}
