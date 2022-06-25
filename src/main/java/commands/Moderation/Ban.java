package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class Ban implements Command {
    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "Ban a user";
    }

    @Override
    public void onCommand(CommandContext context) {

        String[] args = context.getMessage().getContentRaw().split("\\s+");
        List<Member> mentionedMembers = context.getMessage().getMentions().getMembers();

        if (context.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            if (args.length < 3) {
                context.getChannel().sendMessage("Error").queue();
            } else {
                context.getGuild().ban(mentionedMembers.get(0), 0, args[2]).queue();
            }
        } else {
            context.getChannel().sendMessage("You do not have permission to ban members!").queue();
        }

    }
}
