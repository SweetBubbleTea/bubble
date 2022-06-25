package commands.Miscellaneous;

import commandutils.Command;
import commandutils.CommandContext;

import java.util.Random;

public class Coin implements Command {
    @Override
    public String getName() {
        return "coin";
    }

    @Override
    public String getDescription() {
        return "Flips a coin";
    }

    @Override
    public void onCommand(CommandContext context) {
        Random rand = new Random();
        int flip = rand.nextInt(2);

        if (flip == 0) {
            context.getChannel().sendMessage("HEADS").queue();
        } else {
            context.getChannel().sendMessage("TAILS").queue();
        }
    }
}
