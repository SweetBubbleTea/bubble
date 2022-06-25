package commands;

import classtypes.ArcherClass;
import classtypes.Class;
import classtypes.VanguardClass;
import classtypes.WarriorClass;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

public class Classes implements Command {

    String uri = "mongodb+srv://admin:admin@discord-bot.wjqm5.mongodb.net/test";
    MongoClientURI mongoClientURI = new MongoClientURI(uri);
    MongoClient mongoClient = new MongoClient(mongoClientURI);

    private EventWaiter waiter; 
    private static final String WARRIOR_EMOTE = "⚔";
    private static final String ARCHER_EMOTE = "🏹";
    private static final String VANGUARD_EMOTE = "🛡";
    private static final int NOT_REGISTERED = 1;
    private static final int NO_CLASS = 2;
    private static final int ACTIVE_CLASS = 3;

    MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
    MongoCollection<Document> warriorCollection = mongoDatabase.getCollection("Warrior");
    MongoCollection<Document> archerCollection = mongoDatabase.getCollection("Archer");
    MongoCollection<Document> vanguardCollection = mongoDatabase.getCollection("Vanguard");
    MongoCollection<Document> timerCollection = mongoDatabase.getCollection("Timer");
    MongoCollection<Document> idCollection = mongoDatabase.getCollection("ID");

    public Classes(EventWaiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public String getName() {
        return "class";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void onCommand(CommandContext context) {
        context.getChannel().sendMessageEmbeds(initialClassesEmbed())
                .queue(msg -> {
                    msg.addReaction(WARRIOR_EMOTE).queue();
                    msg.addReaction(ARCHER_EMOTE).queue();
                    msg.addReaction(VANGUARD_EMOTE).queue();

                    this.waiter.waitForEvent(
                            MessageReactionAddEvent.class,
                            (event) -> context.getMessage().getAuthor().equals(event.getUser()),
                            event -> {
                                if (event.getReactionEmote().getName().equals(WARRIOR_EMOTE) && memberFind(event.getMember()) != true) {
                                    WarriorClass newWarrior = new WarriorClass(event.getUser().getIdLong());
                                    if (classSearch(newWarrior) == NOT_REGISTERED) {
                                        addHero(newWarrior);
                                        event.getChannel().sendMessageEmbeds(Hero(newWarrior)).queue();
                                        context.getChannel().sendMessage("Welcome to the Warrior Class").queue();
                                    } else if (classSearch(newWarrior) == NO_CLASS) {
                                        warriorCollection.updateOne(Filters.in("User ID", newWarrior.getUserID()), new Document("$set", new Document("Class", "Warrior")));
                                        context.getChannel().sendMessage("Welcome to the Warrior Class").queue();
                                    } else {
                                        event.getChannel().sendMessage("You are already in the Warrior class.").queue();
                                        return;
                                    }
                                } else if (event.getReactionEmote().getName().equals(ARCHER_EMOTE) && memberFind(event.getMember()) != true) {
                                    ArcherClass newArcher = new ArcherClass(event.getUser().getIdLong());
                                    if (classSearch(newArcher) == NOT_REGISTERED) {
                                        addHero(newArcher);
                                        event.getChannel().sendMessageEmbeds(Hero(newArcher)).queue();
                                        context.getChannel().sendMessage("Welcome to the Archer Class").queue();
                                    } else if (classSearch(newArcher) == NO_CLASS) {
                                        archerCollection.updateOne(Filters.in("User ID", newArcher.getUserID()), new Document("$set", new Document("Class", "Archer")));
                                        context.getChannel().sendMessage("Welcome to the Archer Class").queue();
                                    } else {
                                        event.getChannel().sendMessage("You are already in the Archer class.").queue();
                                        return;
                                    }
                                } else if (event.getReactionEmote().getName().equals(VANGUARD_EMOTE) && memberFind(event.getMember()) != true) {
                                    VanguardClass newVanguard = new VanguardClass(event.getUser().getIdLong());
                                    if (classSearch(newVanguard) == NOT_REGISTERED) {
                                        addHero(newVanguard);
                                        event.getChannel().sendMessageEmbeds(Hero(newVanguard)).queue();
                                        context.getChannel().sendMessage("Welcome to the Vanguard Class").queue();
                                    } else if (classSearch(newVanguard) == NO_CLASS) {
                                        archerCollection.updateOne(Filters.in("User ID", newVanguard.getUserID()), new Document("$set", new Document("Class", "Vanguard")));
                                        context.getChannel().sendMessage("Welcome to the Vanguard Class").queue();
                                    } else {
                                        event.getChannel().sendMessage("You are already in the Vanguard class.").queue();
                                        return;
                                    }
                                } else {
                                    event.getChannel().sendMessage("You are in the " + timerCollection.find(Filters.in("User ID", Long.parseLong(event.getUser().getId()))).first().getString("Class") + " class.").queue();
                                    return;
                                }
                            }, 30L, TimeUnit.SECONDS,
                            () -> context.getChannel().sendMessage("You have waited too long to choose your class").queue()
                    );
                });
    }

    public MessageEmbed initialClassesEmbed() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Classes");
        eb.addField("", "There are 3 classes you can choose from.", true);
        eb.addField("", "Warrior class" + WARRIOR_EMOTE, false);
        eb.addField("", "Archer class" + ARCHER_EMOTE, false);
        eb.addField("", "Vanguard class" + VANGUARD_EMOTE, false);
        return eb.build();
    }

    public MessageEmbed Hero(Class hero) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("You have chosen the " + hero.getClassChosen() + " class.");
        eb.addField("Initial Stats", "", false);
        eb.addField("Health", Integer.toString(hero.getHealth()), false);
        eb.addField("Attack", Integer.toString(hero.getAttack()), false);
        eb.addField("Defense", Integer.toString(hero.getDefense()), true);
        eb.addField("Weapon Type", hero.getWeapon(), false);
        return eb.build();
    }

    public void addHero(Class classObj){
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

    public int classSearch(Class search) {

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
        } else if (found.getString("Class").equals(null)){
            return NO_CLASS;
        } else {
            return ACTIVE_CLASS;
        }
    }

    public boolean memberFind(Member member) {

        Document found = timerCollection.find(Filters.in("User ID", Long.parseLong(member.getUser().getId()))).first();

        if (found == null) {
            return false;
        } else {
            return true;
        }
    }
}
