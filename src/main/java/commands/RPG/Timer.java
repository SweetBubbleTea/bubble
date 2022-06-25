package commands.RPG;

import commandutils.Command;
import commandutils.CommandContext;

import java.util.concurrent.TimeUnit;

public class Timer implements Command {
    @Override
    public String getName() {
        return "timer";
    }

    @Override
    public String getDescription() {
        return "Set a countdown timer in seconds";
    }

    @Override
    public void onCommand(CommandContext context) {
        String[] args = context.getMessage().getContentRaw().split(" ");

        if (args.length < 2) {
            context.getChannel().sendMessage("Usage: !timer <seconds>").queue();
        } else {
            context.getChannel().sendMessage("Your countdown for " + args[1] + " seconds begins now.").queue();
            context.getChannel().sendMessage("TIMER UP").queueAfter(Integer.parseInt(args[1]), TimeUnit.SECONDS);
        }
    }
}
