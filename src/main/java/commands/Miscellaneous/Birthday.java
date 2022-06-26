package commands.Miscellaneous;

import classtypes.Class;
import com.mongodb.client.model.Filters;
import commands.RPG.Utilities;
import commandutils.Command;
import commandutils.CommandContext;
import net.dv8tion.jda.api.entities.Member;
import org.bson.Document;

import java.util.List;

public class Birthday implements Command, Utilities {
    @Override
    public String getName() {
        return "bday";
    }

    @Override
    public String getDescription() {
        return "Sets the birthday of a member";
    }

    @Override
    public void onCommand(CommandContext context) {
        String[] args = context.getArgs();
        List<Member> mentionedMembers = context.getMessage().getMentions().getMembers();
        if (args.length == 2) {
            if (birthdayCollection.find(Filters.in("User ID", mentionedMembers.get(0).getIdLong())).first() != null) {
                String entire = birthdayCollection.find(Filters.in("User ID", mentionedMembers.get(0).getIdLong())).first().getString("Entire");
                context.getChannel().sendMessage(mentionedMembers.get(0).getAsMention() + "'s birthday is " + entire).queue();
            } else {
                context.getChannel().sendMessage("Currently not registered").queue();
            }
        } else if (args.length == 5){
            if (birthdayCollection.find(Filters.in("User ID", mentionedMembers.get(0).getIdLong())).first() == null) {
                context.getChannel().sendMessage("Birthday for " + mentionedMembers.get(0).getAsMention() + " added.").queue();
                addBirthday(context.getMember().getIdLong(), args[2], args[3],args[4]);
            } else {
                context.getChannel().sendMessage("Birthday for " + mentionedMembers.get(0).getAsMention() + " updated.").queue();
                updateBirthday(context.getMember().getIdLong(), args[2], args[3],args[4]);
            }
        } else {
            context.getChannel().sendMessage("Usage: !bday <User> <MM> <DD> <YYYY> \nOR \n !bday <User>").queue();
        }
    }

    public void addBirthday(long id, String month, String day, String year){
        Document birthday = new Document("User ID", id);
        birthday.append("Month", month);
        birthday.append("Day", day);
        birthday.append("Year", year);
        birthday.append("Entire", month + "/" + day +  "/" + year);

        birthdayCollection.insertOne(birthday);
    }

    public void updateBirthday(long id, String month, String day, String year){
        Document birthday = new Document("User ID", id);
        birthday.append("Month", month);
        birthday.append("Day", day);
        birthday.append("Year", year);

        birthdayCollection.updateOne(Filters.in("User ID", id), new Document("$set", birthday));
    }
}
