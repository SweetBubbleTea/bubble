package commands;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;

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
