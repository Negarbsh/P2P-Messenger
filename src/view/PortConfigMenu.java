package view;

import cotroller.UserConfig;
import model.User;
import sun.applet.Main;

public class PortConfigMenu {
    private final static String generalError = MainMenu.generalError;
    private final static String portAlreadySet = "the port is already set";
    private final static String notOpenPort = "the port you specified was not open";


    public static String checkCommand(String command) {
        User user = UserConfig.getLoggedInUser();
        if (user == null) return MainMenu.noLogin;
        if (command.contains("--close ")) {
            String portString = MainMenu.getValueOfFlag(command, "port");
            if (portString == null) return generalError;
            int port;
            try {
                port = Integer.parseInt(portString);
            } catch (Exception e) {
                return generalError;
            }
            if (user.getPort() != port) return notOpenPort;//todo : fine?
            else user.closePort();
        }


        if (command.contains("--listen ")) {
            String portString = MainMenu.getValueOfFlag(command, "port");
            if (portString == null) return generalError;
            int port;
            try {
                port = Integer.parseInt(portString);
            } catch (Exception e) {
                return generalError;
            }
            if (command.contains("--rebind ")) {
                user.closePort(); //todo fine? shouldn't there be any error?
                user.setPort(port);
            } else {
                if (user.isPortSet()) return portAlreadySet;
                user.setPort(port);
            }

        }
        return null;
    }
}
