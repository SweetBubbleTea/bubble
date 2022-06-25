package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.UserSnowflake;

public class Unban implements Command {
    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public String getDescription() {
        return "Unban an User";
    }

    @Override
    public void onCommand(CommandContext context) {
        String[] args = context.getMessage().getContentRaw().split("\\s+");

        if (context.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            if (args.length < 2) {
                context.getChannel().sendMessage("Usage: !unban <User>").queue();
            } else {
                context.getGuild().unban(UserSnowflake.fromId(Long.parseLong(args[1]))).queue();
            }
        } else {
            context.getChannel().sendMessage("You do not have permission to unban members!").queue();
        }
    }
}
