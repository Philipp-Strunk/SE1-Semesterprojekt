package uebung4.semesterproject.controller.commands;

public interface Command {
    void execute(CommandContext context, String[] args);

    String getDescription();
}
