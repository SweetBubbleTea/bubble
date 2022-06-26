package commands.Miscellaneous;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class About implements Command {
    @Override
    public String getName() {
        return "about";
    }

    @Override
    public String getDescription() {
        return "Find more information about me";
    }

    @Override
    public void onCommand(CommandContext context) {
        context.getChannel().sendMessageEmbeds(createEmbed()).queue();
    }

    private MessageEmbed createEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("About My Creator");
        eb.addField("Name", "Andy Zhao", false);
        eb.addField("Age", "20", false);
        eb.addField("Occupation", "Student", false);
        eb.addField("Github", "https://github.com/SweetBubbleTea/", false);
        return eb.build();
    }
}
