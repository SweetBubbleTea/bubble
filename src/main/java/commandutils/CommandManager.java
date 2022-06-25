package commandutils;

import SweetBubbleTea.Config;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private static final String PREFIX = "!";

    private static List<Command> commands = new ArrayList<>();

    public static void runCommand(CommandContext context) {
        Command commandToRun = null;

        for(Command command : commands) {
            if(command.getName().equalsIgnoreCase(context.getHandler())) {
                commandToRun = command;
            }
        }

        if (commandToRun == null) {
            return;
        }

        commandToRun.onCommand(context);
    }

    private static boolean ifCommandExists(Command command) {
        for (Command checkExisting : commands) {
            if (command.equals(checkExisting)) {
                return true;
            }
        }
        return false;
    }

    public static void registerCommand(Command command) {
        if (!ifCommandExists(command)) {
            commands.add(command);
        }
    }

    public static void registerCommand(ArrayList<Command> commandAdded) {
        for (Command command : commandAdded) {
            commands.add(command);
        }
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static List<Command> getCommands() {
        return commands;
    }
}
