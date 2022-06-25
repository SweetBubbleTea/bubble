package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;

public class Leave implements Command {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leaves a server";
    }

    @Override
    public void onCommand(CommandContext context) {
        if (context.getMember() == context.getGuild().getOwner()) {
            context.getGuild().leave().queue();
        }
    }
}
