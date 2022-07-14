package commandutils;

import java.util.List;

public interface Command {

    String getName();

    String getDescription();

    void onCommand(CommandContext context);

    default boolean equals(Command command) {
        return this.getName().equals(command.getName());
    }
}
