package commands.Miscellaneous;

import commandutils.Command;
import commandutils.CommandContext;

import java.util.ArrayList;
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
        ArrayList<String> responses = new ArrayList<>();
        responses.add("Without a Doubt");
        responses.add("It is Certain");
        responses.add("Signs Point to Yes");
        responses.add("As I see it, Yes");
        responses.add("Very Doubtful");
        responses.add("Ask Again Later");
        responses.add("Better Not Tell You Now");
        responses.add("Concentrate and Ask Again");
        responses.add("Reply Hazy Try Again");
        responses.add("Don't Count On It");
        Random rand = new Random();
        int getResponse = rand.nextInt(responses.size());
        context.getChannel().sendMessage(responses.get(getResponse)).queue();
    }
}
