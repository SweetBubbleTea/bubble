package commands;

import commandutils.Command;
import commandutils.CommandContext;

public class Invite implements Command {

    String oAuthURL = "https://discord.com/api/oauth2/authorize?client_id=987788631185977384&permissions=8&scope=bot";

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invite the bot to a new server";
    }

    @Override
    public void onCommand(CommandContext context) {
        context.getChannel().sendMessage(String.format(oAuthURL, context.getChannel().getJDA().getSelfUser().getId())).queue();
    }
}
