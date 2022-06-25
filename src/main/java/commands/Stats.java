package commands;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bson.Document;

import java.awt.*;

public class Stats implements Command {

    String uri = "mongodb+srv://admin:admin@discord-bot.wjqm5.mongodb.net/test";
    MongoClientURI mongoClientURI = new MongoClientURI(uri);
    MongoClient mongoClient = new MongoClient(mongoClientURI);

    MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
    MongoCollection<Document> timerCollection = mongoDatabase.getCollection("Timer");

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Obtain the stats of the hero";
    }

    @Override
    public void onCommand(CommandContext context) {
        if (memberFind(context.getMember()) == null) {
            context.getChannel().sendMessageEmbeds(error()).queue();
        } else {
            context.getChannel().sendMessageEmbeds(heroStats(memberFind(context.getMember()), context.getMember(),Long.parseLong(context.getMember().getUser().getId()))).queue();
        }
    }

    public MessageEmbed heroStats(MongoCollection<Document> dom, Member member,long id) {
        Document doc = dom.find(Filters.in("User ID", id)).first();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(member.getUser().getName() + " Stats");
        eb.setThumbnail(member.getUser().getAvatarUrl());
        eb.setColor(Color.orange);
        eb.addField("Level", Integer.toString(doc.getInteger("Level")), false);
        eb.addField("Health", Integer.toString(doc.getInteger("Health")), false);
        eb.addField("Attack", Integer.toString(doc.getInteger("Attack")), false);
        eb.addField("Defense", Integer.toString(doc.getInteger("Defense")), false);
        eb.addField("Weapon Type", doc.getString("Weapon"), false);
        eb.addField("XP", doc.getInteger("Level") + "/" + doc.getInteger("Level Cap"), false);
        return eb.build();
    }

    public MessageEmbed error() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Error");
        eb.addField("", "You are currently not registered to a class", true);
        eb.setColor(Color.red);
        return eb.build();
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
