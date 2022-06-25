package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class Nicknames implements Command {
    @Override
    public String getName() {
        return "nickname";
    }

    @Override
    public String getDescription() {
        return "Change the nickname of an user";
    }

    @Override
    public void onCommand(CommandContext context) {
        String[] args = context.getMessage().getContentRaw().split("\\s+");
        String alt = context.getMessage().getContentRaw().replace("!nickname ", "").replace(args[1] + " ", "");
        List<Member> mentionedMembers = context.getMessage().getMentions().getMembers();

        if (context.getMember().hasPermission(Permission.NICKNAME_MANAGE)) {
            if (args.length < 3 && !(mentionedMembers.get(0).getUser() == context.getMember().getUser())) {
                context.getChannel().sendMessage("Usage: !nickname <user> <new nickname>").queue();
            } else if (mentionedMembers.get(0).getUser() == context.getMember().getUser()){
                context.getChannel().sendMessage("Cannot modify a role that is higher or equal in hierarchy").queue();
            } else {
                context.getGuild().modifyNickname(mentionedMembers.get(0), alt).queue();
            }
        } else {
            context.getChannel().sendMessage("You do not have the permission to manage nickname!").queue();
        }
    }
}
