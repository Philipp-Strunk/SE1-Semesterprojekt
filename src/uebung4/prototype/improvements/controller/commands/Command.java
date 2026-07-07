package uebung4.prototype.improvements.controller.commands;

public interface Command {
    void execute(CommandContext context, String[] args);

    String getDescription();
}
