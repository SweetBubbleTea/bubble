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

public class Level implements Command {

    String uri = "mongodb+srv://admin:admin@discord-bot.wjqm5.mongodb.net/test";
    MongoClientURI mongoClientURI = new MongoClientURI(uri);
    MongoClient mongoClient = new MongoClient(mongoClientURI);

    MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
    MongoCollection<Document> timerCollection = mongoDatabase.getCollection("Timer");

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
            int exp = memberFind(context.getMember()).find(Filters.in("User ID", Long.parseLong(context.getMember().getUser().getId()))).first().getInteger("Level");
            context.getChannel().sendMessage("You are level " + exp + ".").queue();
        }
    }

    public MongoCollection<Document> memberFind(Member member) {

        Document found = timerCollection.find(Filters.in("User ID", Long.parseLong(member.getUser().getId()))).first();

        if (found == null) {
            return null;
        } else if (found.getString("Class").equals("Warrior")){
            return mongoDatabase.getCollection("Warrior");
        } else if (found.getString("Class").equals("Archer")) {
            return mongoDatabase.getCollection("Archer");
        } else {
            return mongoDatabase.getCollection("Vanguard");
        }
    }
}