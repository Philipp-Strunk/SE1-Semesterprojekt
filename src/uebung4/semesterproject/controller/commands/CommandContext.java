package uebung4.semesterproject.controller.commands;

import uebung4.semesterproject.model.Container;
import uebung4.semesterproject.model.ActorRegistry;
import uebung4.semesterproject.model.persistence.PersistenceStrategyMongoDB;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class CommandContext {

    private final Scanner scanner;
    private final Container container;
    private final ActorRegistry actorRegistry = new ActorRegistry();
    private final Map<String, Command> commands;
    private final Stack<UndoAction> undoHistory = new Stack<>();
    private boolean exitRequested;

    public CommandContext(Scanner scanner, Container container, Map<String, Command> commands) {
        this.scanner = scanner;
        this.container = container;
        this.commands = commands;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public Container getContainer() {
        return container;
    }

    public ActorRegistry getActorRegistry() {
        return actorRegistry;
    }

    public void storeActorsIfMongoDB() {
        if (container.getPersistenceStrategy() instanceof PersistenceStrategyMongoDB) {
            PersistenceStrategyMongoDB strategy = (PersistenceStrategyMongoDB) container.getPersistenceStrategy();
            strategy.saveActors(actorRegistry.getActors());
        }
    }

    public void loadActorsIfMongoDB() {
        if (container.getPersistenceStrategy() instanceof PersistenceStrategyMongoDB) {
            PersistenceStrategyMongoDB strategy = (PersistenceStrategyMongoDB) container.getPersistenceStrategy();
            actorRegistry.setActors(strategy.loadActors());
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void addUndoAction(UndoAction undoAction) {
        undoHistory.push(undoAction);
    }

    public boolean hasUndoAction() {
        return !undoHistory.empty();
    }

    public UndoAction getLastUndoAction() {
        return undoHistory.pop();
    }

    public boolean isExitRequested() {
        return exitRequested;
    }

    public void requestExit() {
        this.exitRequested = true;
    }
}
