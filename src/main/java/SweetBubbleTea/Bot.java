package SweetBubbleTea;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import commands.Miscellaneous.Avatar;
import commands.Miscellaneous.Coin;
import commands.Miscellaneous.Ping;
import commands.Moderation.*;
import commands.RPG.*;
import commandutils.Command;
import commandutils.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;


public class Bot {

    JDA jda;
    String token;
    EventWaiter waiter = new EventWaiter();

    EnumSet<GatewayIntent> gatewayIntents = EnumSet.of(
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_MESSAGE_REACTIONS,
            GatewayIntent.GUILD_MESSAGES);

    ArrayList<ListenerAdapter> listenerAdapters = new ArrayList<>(
            Arrays.asList(new Listener()
            ));

    ArrayList<Command> commands = new ArrayList<>(
            Arrays.asList(new Ping(),
                    new Avatar(),
                    new Invite(),
                    new Coin(),
                    new Clear(),
                    new Timer(),
                    new Classes(waiter),
                    new Stats(),
                    new Xp(),
                    new Level(),
                    new Ban(),
                    new Kick(),
                    new Deafen(),
                    new Unban(),
                    new Undeafen(),
                    new Unmute(),
                    new Mute(),
                    new AddRoles(),
                    new RemoveRoles(),
                    new Nicknames(),
                    new Owner(),
                    new Leave()
            ));

    public Bot(String token) {
        this.token = token;
    }

    public void start() throws LoginException, InterruptedException {

        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        for (ListenerAdapter listener : listenerAdapters) {
            jdaBuilder.addEventListeners(listener);
        }
        for (Command command : commands) {
            CommandManager.registerCommand(command);
        }
        jdaBuilder.addEventListeners(waiter, waiter);
        jdaBuilder.enableIntents(gatewayIntents);
        jdaBuilder.setActivity(Activity.playing("RPGBOT"));
        jda = jdaBuilder.build();
        jda.awaitReady();
    }
}
