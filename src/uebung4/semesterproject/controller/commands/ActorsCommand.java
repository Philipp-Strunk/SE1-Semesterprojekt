package uebung4.semesterproject.controller.commands;

import java.util.List;

public class ActorsCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        List<String> actors = context.getActorRegistry().getActors();
        if (actors.isEmpty()) {
            System.out.println("Keine Akteure vorhanden.");
            return;
        }

        for (String actor : actors) {
            System.out.println(actor);
        }
    }

    @Override
    public String getDescription() {
        return "registrierte Akteure anzeigen";
    }
}
