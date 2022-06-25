package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class AddRoles implements Command {
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "Adds a role to an User";
    }

    @Override
    public void onCommand(CommandContext context) {
        String[] args = context.getMessage().getContentRaw().split("\\s+");
        List<Member> mentionedMembers = context.getMessage().getMentions().getMembers();

        if (context.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            if (args.length < 3) {
                context.getChannel().sendMessage("Usage: !remove <user> <name of role>").queue();
            } else {
                List<Role> roleList = context.getGuild().getRolesByName(args[2], true);
                Role role = roleList.get(0);
                if (mentionedMembers.get(0).getUser() == context.getMember().getUser()){
                    context.getChannel().sendMessage("Cannot modify a role that is higher or equal in hierarchy").queue();
                } else {
                    context.getGuild().addRoleToMember(mentionedMembers.get(0), role).queue();
                }
            }
        } else {
            context.getChannel().sendMessage("You do not have permission to add roles").queue();
        }
    }
}
