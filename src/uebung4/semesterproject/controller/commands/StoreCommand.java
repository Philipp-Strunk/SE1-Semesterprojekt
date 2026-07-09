package uebung4.semesterproject.controller.commands;

import uebung4.semesterproject.model.ContainerException;

public class StoreCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        try {
            context.getContainer().store();
            context.storeActorsIfMongoDB();
        } catch (ContainerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "User Stories persistent speichern";
    }
}
