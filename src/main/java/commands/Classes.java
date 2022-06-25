package commands;

import classtypes.ArcherClass;
import classtypes.VanguardClass;
import classtypes.WarriorClass;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.mongodb.client.model.Filters;
import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

public class Classes implements Command, Utilities {

    private EventWaiter waiter;

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
                                if (event.getReactionEmote().getName().equals(WARRIOR_EMOTE) && memberRegister(event.getMember())) {
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
                                } else if (event.getReactionEmote().getName().equals(ARCHER_EMOTE) && memberRegister(event.getMember())) {
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
                                } else if (event.getReactionEmote().getName().equals(VANGUARD_EMOTE) && memberRegister(event.getMember())) {
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
}
