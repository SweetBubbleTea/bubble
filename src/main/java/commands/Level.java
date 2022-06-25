package commands;

import com.mongodb.client.model.Filters;
import commandutils.Command;
import commandutils.CommandContext;


public class Level implements Command, Utilities {

    @Override
    public String getName() {
        return "level";
    }

    @Override
    public String getDescription() {
        return "Obtain your current level";
    }

    @Override
    public void onCommand(CommandContext context) {
        if (memberFind(context.getMember()) == null) {
            context.getChannel().sendMessage("You are currently not registered in a class.").queue();
        } else {
            int level = getIntData(context.getMember(), "Level");
            context.getChannel().sendMessage("You are level " + level + ".").queue();
        }
    }
}