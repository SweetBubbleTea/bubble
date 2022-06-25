package commandutils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandContext {

    //The member that ran the command
    private Member member;

    //The textchannel that the command was used in
    private TextChannel channel;

    //The message that was sent containing the command
    private Message message;

    //The handler that was used to call the command
    private String handler;

    //The arguments that was passed with the command
    private String[] args;

    public CommandContext(Member member, TextChannel channel, Message message, String handler, String[] z) {
        this.member = member;
        this.channel = channel;
        this.message = message;
        this.handler = handler;
        this.args = args;
    }

    public Member getMember() {
        return member;
    }

    public TextChannel getChannel() {
        return channel;
    }

    public Message getMessage() {
        return message;
    }

    public String getHandler() {
        return handler;
    }

    public String[] getArgs() {
        return args;
    }
}
