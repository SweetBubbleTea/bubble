package commands.RPG;

import commandutils.Command;
import commandutils.CommandContext;

public class Xp implements Command, Utilities {

    @Override
    public String getName() {
        return "xp";
    }

    @Override
    public String getDescription() {
        return "Obtain your current xp level";
    }

    @Override
    public void onCommand(CommandContext context) {
        if (memberFind(context.getMember()) == null) {
            context.getChannel().sendMessage("You are currently not registered in a class.").queue();
        } else {
            int exp = getIntData(context.getMember(), "XP");
            context.getChannel().sendMessage("You currently have " + exp + " XP.").queue();
        }
    }
}
