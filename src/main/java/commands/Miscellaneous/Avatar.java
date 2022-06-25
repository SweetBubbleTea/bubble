package commands.Miscellaneous;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class Avatar implements Command {

    @Override
    public String getName() {
        return "avatar";
    }

    @Override
    public String getDescription() {
        return "Obtain the user avatar";
    }


    @Override
    public void onCommand(CommandContext context) {
        List<Member> mentionedMembers = context.getMessage().getMentions().getMembers();
        if (mentionedMembers.isEmpty()) {
            context.getChannel().sendMessageEmbeds(this.createEmbed(context.getMember())).queue();
        } else {
            context.getChannel().sendMessageEmbeds(this.createEmbed(mentionedMembers.get(0))).queue();
        }
    }

    public MessageEmbed createEmbed(Member member) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setImage(member.getUser().getAvatarUrl());
        return eb.build();
    }
}
