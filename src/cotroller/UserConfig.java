package cotroller;

import model.User;

public class UserConfig {
    private static User loggedInUser;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void login(User loggedInUser) {
        UserConfig.loggedInUser = loggedInUser;
        if (loggedInUser.isPortSet())
            MailController.setShouldCheckNewMessages(true);
    }

    public static void logout() {
        MailController.stopReceivingMessages();
        loggedInUser = null;
    }


}
