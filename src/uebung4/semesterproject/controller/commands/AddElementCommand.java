package uebung4.semesterproject.controller.commands;

public class AddElementCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        if (args.length < 3 || !args[1].equals("--actor")) {
            System.out.println("Nutzung: addElement --actor <Akteur>");
            return;
        }

        String actor = readActor(args);
        if (actor.isEmpty()) {
            System.out.println("Bitte einen Akteur angeben.");
            return;
        }

        boolean added = context.getActorRegistry().addActor(actor);
        if (!added) {
            System.out.println("Der Akteur " + actor + " ist bereits registriert.");
            return;
        }

        context.addUndoAction(new RemoveActorUndoAction(context, actor));
        System.out.println("Der Akteur " + actor + " wurde im System registriert!");
    }

    @Override
    public String getDescription() {
        return "neuen Akteur registrieren";
    }

    private String readActor(String[] args) {
        String actor = "";
        for (int i = 2; i < args.length; i++) {
            if (!actor.isEmpty()) {
                actor = actor + " ";
            }
            actor = actor + args[i];
        }
        return actor.trim();
    }
}
