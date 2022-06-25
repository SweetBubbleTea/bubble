package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class Undeafen implements Command {
    @Override
    public String getName() {
        return "undeafen";
    }

    @Override
    public String getDescription() {
        return "Undeafens an User";
    }

    @Override
    public void onCommand(CommandContext context) {
        String[] args = context.getMessage().getContentRaw().split("\\s+");
        List<Member> mentionedMembers = context.getMessage().getMentions().getMembers();

        if (context.getMember().hasPermission(Permission.VOICE_DEAF_OTHERS)) {
            if (args.length < 2) {
                context.getChannel().sendMessage("Usage: !undeafen <User>").queue();
            } else {
                context.getGuild().deafen(mentionedMembers.get(0), false).queue();
            }
        } else {
            context.getChannel().sendMessage("You do not have permission to undeafen members!").queue();
        }
    }
}
