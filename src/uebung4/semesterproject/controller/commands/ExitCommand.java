package uebung4.semesterproject.controller.commands;

public class ExitCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        System.out.println("Programm beendet.");
        context.requestExit();
    }

    @Override
    public String getDescription() {
        return "Anwendung beenden";
    }
}
