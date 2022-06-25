package commandutils;

import java.util.List;

public interface Command {

    public String getName();

    public String getDescription();

    default public List<String> getAliases() {
        return List.of();
    }

    public void onCommand(CommandContext context);

    public default boolean equals(Command command) {
        return this.getName().equals(command.getName());
    }
}
