package cotroller;

import model.User;
import sun.applet.Main;
import view.MainMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MailController {

    private static boolean shouldCheckNewMessages = false;
    private static String messageSendingError = "could not send message";

    public static void setShouldCheckNewMessages(boolean shouldCheckNewMessages) {
        MailController.shouldCheckNewMessages = shouldCheckNewMessages;
        if (shouldCheckNewMessages) startReceivingMessages();
    }

    public static String sendMessage(int portToSend, String hostToSend, String message) {
        User currentUser = UserConfig.getLoggedInUser();
        if (currentUser == null) return MainMenu.noLogin;
        if (hostToSend == null || portToSend == -1) return messageSendingError; //todo null or error?
        try {
            Socket socket = new Socket(hostToSend, portToSend);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(currentUser.getUsername() + " -> " + message);
            dataOutputStream.flush();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            return messageSendingError;
        }
        return MainMenu.success;
    }

    public static void startReceivingMessages() {
        User user = UserConfig.getLoggedInUser();
        if (user.getPort() == -1) return; //todo havaset bashe ke vaghti in seda mishe bayad port ham set shode bashe:)
        new Thread(() -> {
            shouldCheckNewMessages = true;
            try {
                ServerSocket serverSocket = new ServerSocket(user.getPort(), 0, InetAddress.getByName(null));
                DataInputStream dataInputStream = null;
                while (shouldCheckNewMessages) {
                    Socket socket = serverSocket.accept();
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    String message = dataInputStream.readUTF();
                    user.addToMessages(message);
                }
                if (dataInputStream != null)
                    dataInputStream.close();
                serverSocket.close();
            } catch (IOException e) {
                System.out.println(MainMenu.generalError);      //todo fine?
            }
        }).start();
    }

    public static void stopReceivingMessages() {
        shouldCheckNewMessages = false;
    }
}
