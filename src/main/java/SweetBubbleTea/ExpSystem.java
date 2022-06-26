package SweetBubbleTea;

import com.mongodb.client.model.Filters;
import commands.RPG.Utilities;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ExpSystem extends ListenerAdapter implements Utilities {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (!event.getAuthor().isBot() && event.isFromGuild()) {
            if (memberNotRegister(event.getMember())) {
                return;
            } else {
                randExp(event.getMember());
                levelUp(event.getMember());
            }
        }
    }

    private int getPlayerTime(Member member) {
        Document warriorExp = timerCollection.find(Filters.in("User ID", Long.parseLong(member.getUser().getId()))).first();
        assert warriorExp != null;
        return warriorExp.getInteger("Timer");
    }

    private void setPlayerTimer(Member member, int num) {
        timerCollection.updateOne(Filters.in("User ID", Long.parseLong(member.getUser().getId())), new Document("$set", new Document("Timer", num)));
    }

    private int getPlayerExp(Member member) {
        return getIntData(member, "XP");
    }

    private void setPlayerExp(Member member, int num) {
        setIntData(member, "XP", num);
    }

    private void randExp(Member member) {
        Random rand = new Random();
        setPlayerExp(member, getPlayerExp(member) + (rand.nextInt(20) + 1));
    }

    private int getLevel(Member member) {
        return getIntData(member, "Level");
    }

    private int getLevelCap(Member member) {
        return getIntData(member, "Level Cap");
    }

    private void levelUp(Member member) {
        int max = getLevelCap(member);
        int level = getLevel(member);

        if (level % 5 == 0) {
            setIntData(member, "Level Cap", 600);
        } else if (level % 10 == 0) {
            setIntData(member, "Level Cap", 1000);
        } else if (level % 15 == 0) {
            setIntData(member, "Level Cap", 1500);
        } else if (level % 20 == 0) {
            setIntData(member, "Level Cap", 2000);
        }

        if (getPlayerExp(member) / max >= 1) {
            setIntData(member, "XP", getPlayerExp(member) - max);
            setIntData(member, "Level", getLevel(member) + 1);
        }
    }
}
