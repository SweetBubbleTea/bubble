package commands.Miscellaneous;

import commandutils.Command;
import commandutils.CommandContext;

import java.util.List;
import java.util.Random;

public class EightBall implements Command {
    @Override
    public String getName() {
        return "8ball";
    }

    @Override
    public String getDescription() {
        return "Receives a response from the Magic 8ball";
    }

    @Override
    public void onCommand(CommandContext context) {
        List<String> responses = List.of("Without a Doubt", "It is Certain", "Signs Point to Yes", "As I see it, Yes",
                "Very Doubtful", "Ask Again Later", "Better Not Tell You Now", "Concentrate and Ask Again",
                "Reply Hazy Try Again", "Don't Count On It");
        Random rand = new Random();
        int getResponse = rand.nextInt(responses.size());
        context.getChannel().sendMessage(responses.get(getResponse)).queue();
    }
}
