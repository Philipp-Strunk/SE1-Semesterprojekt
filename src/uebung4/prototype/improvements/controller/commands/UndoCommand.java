package uebung4.prototype.improvements.controller.commands;

public class UndoCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        if (!context.hasUndoAction()) {
            System.out.println("Nichts zum Rückgängigmachen vorhanden.");
            return;
        }

        UndoAction undoAction = context.getLastUndoAction();
        undoAction.undo();
    }

    @Override
    public String getDescription() {
        return "letzten undo-fähigen Befehl rückgängig machen";
    }
}
