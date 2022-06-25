package SweetBubbleTea;

import commandutils.CommandContext;
import commandutils.CommandManager;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class Listener extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.printf("%#s is ready", event.getJDA().getSelfUser());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String prefix = Config.get("prefix");

        if (event.getAuthor().isBot()) {
            return;
        }

        if (!event.getMessage().getContentRaw().startsWith(prefix)) {
            return;
        }

        String command = event.getMessage().getContentStripped().replace(prefix, "").split(" ")[0];
        String[] args = event.getMessage().getContentRaw().replace(prefix, "").replace(command, "").split(" ");
        CommandContext context = new CommandContext(event.getMember(), event.getTextChannel(), event.getMessage(), command, args, event.getGuild());

        CommandManager.runCommand(context);

    }
}
