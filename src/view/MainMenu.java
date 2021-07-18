package view;

import sun.net.PortConfig;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {
    public static String generalError = "an error occurred";
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
                message = (MailMenu.checkCommand(command.substring("send ".length())));
            else if (command.startsWith("focus "))
                message = (MailMenu.checkCommand(command.substring("focus ".length())));
            else if (command.startsWith("show "))
                message = (ShowMenu.checkCommand(command.substring("show ".length())));
            else if (command.equals("exit ")) return;
            else message = generalError;
            if (message != null) System.out.println(message);
        }
    }

    public static String getValueOfFlag(String command, String flag) {
        Pattern pattern = Pattern.compile(command);
        Matcher matcher = pattern.matcher("--" + flag + " ([\\S]+) ");
        if (!matcher.find()) return null;
        else return matcher.group(1);
    }

}
