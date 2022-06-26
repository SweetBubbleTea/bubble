package commands.RPG;

import SweetBubbleTea.Config;
import classtypes.Class;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.bson.Document;

public interface Utilities {

    String uri = Config.get("uri");
    MongoClientURI mongoClientURI = new MongoClientURI(uri);
    MongoClient mongoClient = new MongoClient(mongoClientURI);

    MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
    MongoCollection<Document> warriorCollection = mongoDatabase.getCollection("Warrior");
    MongoCollection<Document> archerCollection = mongoDatabase.getCollection("Archer");
    MongoCollection<Document> vanguardCollection = mongoDatabase.getCollection("Vanguard");
    MongoCollection<Document> timerCollection = mongoDatabase.getCollection("Timer");

    String WARRIOR_EMOTE = "⚔";
    String ARCHER_EMOTE = "🏹";
    String VANGUARD_EMOTE = "🛡";
    int NOT_REGISTERED = 1;
    int NO_CLASS = 2;
    int ACTIVE_CLASS = 3;

    default MongoCollection<Document> memberFind(Member member) {

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

    default MessageEmbed initialClassesEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Classes");
        eb.addField("", "There are 3 classes you can choose from.", true);
        eb.addField("", "Warrior class" + WARRIOR_EMOTE, false);
        eb.addField("", "Archer class" + ARCHER_EMOTE, false);
        eb.addField("", "Vanguard class" + VANGUARD_EMOTE, false);
        return eb.build();
    }

    default MessageEmbed Hero(Class hero) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("You have chosen the " + hero.getClassChosen() + " class.");
        eb.addField("Initial Stats", "", false);
        eb.addField("Health", Integer.toString(hero.getHealth()), false);
        eb.addField("Attack", Integer.toString(hero.getAttack()), false);
        eb.addField("Defense", Integer.toString(hero.getDefense()), true);
        eb.addField("Weapon Type", hero.getWeapon(), false);
        return eb.build();
    }

    default void addHero(Class classObj){
        Document hero = new Document("User ID", classObj.getUserID());
        hero.append("Class", classObj.getClassChosen());
        hero.append("Health", classObj.getHealth());
        hero.append("Attack", classObj.getAttack());
        hero.append("Weapon", classObj.getWeapon());
        hero.append("Defense", classObj.getDefense());
        hero.append("XP", 0);
        hero.append("Level", 1);
        hero.append("Level Cap", 500);

        if (classObj.getClassChosen().equals("Warrior")) {
            warriorCollection.insertOne(hero);
        } else if (classObj.getClassChosen().equals("Archer")) {
            archerCollection.insertOne(hero);
        } else {
            vanguardCollection.insertOne(hero);
        }

        Document timer = new Document("User ID", classObj.getUserID());
        timer.append("Timer", 1000);
        timer.append("Class", classObj.getClassChosen());
        timerCollection.insertOne((timer));
    }

    default int classSearch(Class search) {

        Document found;

        if (search.getClassChosen().equals("Warrior")) {
            found = warriorCollection.find(Filters.in("User ID", search.getUserID())).first();
        } else if (search.getClassChosen().equals("Archer")) {
            found = archerCollection.find(Filters.in("User ID", search.getUserID())).first();
        } else {
            found = vanguardCollection.find(Filters.in("User ID", search.getUserID())).first();
        }

        if (found == null) {
            return NOT_REGISTERED;
        } else if (found.getString("Class") == null){
            return NO_CLASS;
        } else {
            return ACTIVE_CLASS;
        }
    }

    default boolean memberNotRegister(Member member) {

        Document found = timerCollection.find(Filters.in("User ID", Long.parseLong(member.getUser().getId()))).first();

        return found == null;
    }

    default int getIntData(Member member, String data) {
        return memberFind(member).find(Filters.in("User ID", Long.parseLong(member.getUser().getId()))).first().getInteger(data);
    }

    default void setIntData(Member member, String field , int data) {
        memberFind(member).updateOne(Filters.in("User ID", Long.parseLong(member.getUser().getId())), new Document("$set", new Document(field, data)));
    }
}
