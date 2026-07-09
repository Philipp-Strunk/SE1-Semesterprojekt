package uebung4.semesterproject.controller.commands;

public class RemoveActorUndoAction implements UndoAction {

    private final CommandContext context;
    private final String actor;

    public RemoveActorUndoAction(CommandContext context, String actor) {
        this.context = context;
        this.actor = actor;
    }

    @Override
    public void undo() {
        if (context.getActorRegistry().removeActor(actor)) {
            System.out.println("Der Akteur " + actor + " wurde entfernt.");
        } else {
            System.out.println("Der Akteur " + actor + " konnte nicht entfernt werden.");
        }
    }
}
