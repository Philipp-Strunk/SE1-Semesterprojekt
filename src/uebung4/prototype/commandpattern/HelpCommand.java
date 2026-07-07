package uebung4.prototype.commandpattern;

public class HelpCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Hier die Kommandos: help....");

    }

    @Override
    public void undo() {
        System.out.println("Nothing to Undo!");
    }
}
