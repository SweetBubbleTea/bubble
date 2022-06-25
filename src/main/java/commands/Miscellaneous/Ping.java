package commands.Miscellaneous;

import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.JDA;

public class Ping implements Command {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Get the ping of the user to the Discord servers";
    }

    @Override
    public void onCommand(CommandContext context) {
        JDA jda = context.getChannel().getJDA();

        jda.getRestPing().queue(
                (ping) -> context.getChannel()
                        .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()
        );
    }
}
