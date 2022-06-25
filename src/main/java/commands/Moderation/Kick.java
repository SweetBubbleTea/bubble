package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class Kick implements Command {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kick3 an user";
    }

    @Override
    public void onCommand(CommandContext context) {
        String[] args = context.getMessage().getContentRaw().split("\\s+");
        List<Member> mentionedMembers = context.getMessage().getMentions().getMembers();

        if (context.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            if (args.length < 2) {
                context.getChannel().sendMessage("Usage: !kick <User>").queue();
            } else {
                context.getGuild().kick(mentionedMembers.get(0)).queue();
            }
        } else {
            context.getChannel().sendMessage("You do not have permission to kick members").queue();
        }
    }
}
