package uebung4.semesterproject.controller.commands;

import uebung4.semesterproject.model.Container;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandHandler {

    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final CommandContext context;

    public CommandHandler(Scanner scanner, Container container) {
        this.context = new CommandContext(scanner, container, commands);
        registerDefaults();
    }

    public void handle(String input) {
        String[] args = input.trim().split("\\s+");
        String commandName = args[0].toLowerCase();

        Command command = commands.get(commandName);
        if (command == null) {
            System.out.println("Unbekannter Befehl. Mit 'help' werden alle Befehle angezeigt.");
            return;
        }

        command.execute(context, args);
    }

    public boolean isExitRequested() {
        return context.isExitRequested();
    }

    public void printHelp() {
        commands.get("help").execute(context, new String[] {"help"});
    }

    public boolean containsActor(String actor) {
        return context.getActorRegistry().containsActor(actor);
    }

    private void registerDefaults() {
        commands.put("enter", new EnterCommand());
        commands.put("store", new StoreCommand());
        commands.put("load", new LoadCommand());
        commands.put("dump", new DumpCommand());
        commands.put("addelement", new AddElementCommand());
        commands.put("actors", new ActorsCommand());
        commands.put("analyze", new AnalyzeCommand());
        commands.put("undo", new UndoCommand());
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
    }
}
