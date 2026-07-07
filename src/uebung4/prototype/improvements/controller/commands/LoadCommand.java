package uebung4.prototype.improvements.controller.commands;

import uebung4.prototype.improvements.model.ContainerException;

public class LoadCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        try {
            context.getContainer().load();
            context.loadActorsIfMongoDB();
        } catch (ContainerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "User Stories persistent laden";
    }
}
