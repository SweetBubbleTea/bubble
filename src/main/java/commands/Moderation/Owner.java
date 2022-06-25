package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;

import java.util.Objects;

public class Owner implements Command {
    @Override
    public String getName() {
        return "owner";
    }

    @Override
    public String getDescription() {
        return "Retrieves the owner of the server.";
    }

    @Override
    public void onCommand(CommandContext context) {
        context.getChannel().sendMessage("The owner is " + Objects.requireNonNull(context.getGuild().getOwner()).getAsMention()).queue();
    }
}
