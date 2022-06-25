package commands;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;

public abstract class Utilities {

    String uri = "mongodb+srv://admin:admin@discord-bot.wjqm5.mongodb.net/test";
    MongoClientURI mongoClientURI = new MongoClientURI(uri);
    MongoClient mongoClient = new MongoClient(mongoClientURI);

    MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
    MongoCollection<Document> warriorCollection = mongoDatabase.getCollection("Warrior");
    MongoCollection<Document> archerCollection = mongoDatabase.getCollection("Archer");
    MongoCollection<Document> vanguardCollection = mongoDatabase.getCollection("Vanguard");
    MongoCollection<Document> timerCollection = mongoDatabase.getCollection("Timer");

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
