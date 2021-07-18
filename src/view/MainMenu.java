package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    public static String generalError = "an error occurred";
    public static String success = "success";
    public static String noLogin = "you must login to access this feature";


    public static void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            command = command.concat(" ");
            String message;
            if (command.startsWith("userconfig "))
                message = (UserConfigMenu.checkCommand(command.substring("userconfig ".length())));
            else if (command.startsWith("portconfig "))
                message = (PortConfigMenu.checkCommand(command.substring("portconfig ".length())));
            else if (command.startsWith("contactconfig "))
                message = (ContactMenu.checkCommand(command.substring("contactconfig ".length())));
            else if (command.startsWith("send "))
                message = (MailMenu.checkSendCommand(command.substring("send ".length())));
            else if (command.startsWith("focus "))
                message = (MailMenu.checkFocusCommand(command.substring("focus ".length())));
            else if (command.startsWith("show "))
                message = (ShowMenu.checkCommand(command.substring("show ".length())));
            else if (command.equals("exit ")) return;
            else message = generalError;
            if (message != null) System.out.println(message);
        }
    }

    public static String getValueOfFlag(String command, String flag) {
        Pattern pattern = Pattern.compile("--" + flag + " (\\S+) ");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find()) return null;
        else return matcher.group(1);
    }

    public static int getIntValueOfFlag(String command, String flag) {
        String intString = getValueOfFlag(command, flag);
        if (intString == null) return -1;
        try {
            return Integer.parseInt(intString);
        } catch (Exception e) {
            return -1; //it's not good but who cares?
        }
    }

    public static String getMessageInCommand(String command) {
        Pattern pattern = Pattern.compile("--message" + " \"([^\"]+)\" ");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find()) return null;
        else return matcher.group(1);
    }
}
