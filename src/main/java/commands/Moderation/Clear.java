package commands.Moderation;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Clear implements Command {
    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clear a set of messages";
    }

    @Override
    public void onCommand(CommandContext context) {

        String[] args = context.getMessage().getContentRaw().split("\\s+");

        if (context.getMember().hasPermission(Permission.MESSAGE_MANAGE)) {
            if (args.length < 2) {
                context.getChannel().sendMessageEmbeds(error("Error")).queue();
            } else {
                List<Message> messages = context.getChannel().getHistory().retrievePast(Integer.parseInt(args[1] + 1)).complete();
                context.getChannel().purgeMessages(messages);
                context.getChannel().sendMessageEmbeds(error("Complete")).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }
        } else {
            context.getChannel().sendMessage("You do not have permission to clear messages!").queue();
        }
    }

    public MessageEmbed error(String key) {
        EmbedBuilder eb = new EmbedBuilder();
        if (key.equals("Error")) {
            eb.setTitle("Error");
            eb.addField("", "Usage: !clear <amount>", true);
            eb.setColor(Color.red);
        } else {
            eb.setTitle("Complete");
            eb.addField("", "Messages has been deleted", true);
            eb.setColor(Color.green);
        }
        return eb.build();
    }
}
