package uebung4.semesterproject.controller.commands;

import java.util.Map;

public class HelpCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        System.out.println("Folgende Befehle stehen zur Verfügung:");
        for (Map.Entry<String, Command> entry : context.getCommands().entrySet()) {
            System.out.printf("%-10s - %s%n", getDisplayName(entry.getKey()), entry.getValue().getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "diese Hilfe anzeigen";
    }

    private String getDisplayName(String commandName) {
        if (commandName.equals("addelement")) {
            return "addElement";
        }
        return commandName;
    }
}
