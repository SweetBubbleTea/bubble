package commands.Miscellaneous;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.format.DateTimeFormatter;

public class User implements Command {
    @Override
    public String getName() {
        return "user";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void onCommand(CommandContext context) {
        context.getChannel().sendMessageEmbeds(createEmbed(context)).queue();
    }

    private MessageEmbed createEmbed(CommandContext context) {
        EmbedBuilder eb = new EmbedBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy");
        eb.setTitle(context.getMember().getEffectiveName() + "'s Stats");
        eb.setThumbnail(context.getMember().getUser().getAvatarUrl());
        eb.addField("Time Joined", context.getMember().getTimeJoined().format(formatter), false);
        if (context.getMember().getTimeBoosted() != null) {
            eb.addField("Time Boosted", context.getGuild().getMemberById(context.getMember().getId()).getTimeBoosted().format(formatter) , false);
        }
        eb.addField("Online Status", String.valueOf(context.getMember().getOnlineStatus()), false);
        return eb.build();
    }
}
