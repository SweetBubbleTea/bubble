package SweetBubbleTea;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {
        Bot bot = new Bot(Config.get("token"));
        bot.start();
    }
}
