package view;

import cotroller.UserConfig;
import model.User;
import sun.applet.Main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PortConfigMenu {
    private final static String generalError = MainMenu.generalError;
    private final static String success = MainMenu.success;
    private final static String portAlreadySet = "the port is already set";
    private final static String notOpenPort = "the port you specified was not open";


    public static String checkCommand(String command) {
        User user = UserConfig.getLoggedInUser();
        if (user == null) return MainMenu.noLogin;
        if (command.contains("--close ")) {
            int port = MainMenu.getIntValueOfFlag(command, "port");
            if (port == -1) return generalError;
            if (user.getPort() != port) return notOpenPort;//todo : fine?
            else {
                user.closePort();
                return success;
            }
        }

        if (command.contains("--listen ")) {
            int port = MainMenu.getIntValueOfFlag(command, "port");
            if (port == -1) return generalError;
            if (command.contains("--rebind ")) {
                user.closePort(); //todo fine? shouldn't there be any error?
            } else {
                if (user.isPortSet()) return portAlreadySet;
            }
            try {
                ServerSocket socket = new ServerSocket(port);
                socket.close();
            } catch (IOException e) {
                return MainMenu.generalError; //todo I guess it should be something else.
            }
            user.setPort(port);
            return success;
        }
        return null;
    }
}
