package presenter;

import presenter.windowBeforeLogin.LoginWindow;

/**
 * the class is used to open the Tech Conference
 */
public class Present {
    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
        /**
         * After FuYao internship , I realized that we should move config setting to here, it significantly increase
         * the readability.
         *
         * LoginWindow conferenceSystem = new LoginWindow();
         * conferenceSystem.setServerIp("");
         * conferenceSystem.setPassword("");
         * conferenceSystem.setPort("");
         *
         * loginWindow.start()
         */
    }
}
