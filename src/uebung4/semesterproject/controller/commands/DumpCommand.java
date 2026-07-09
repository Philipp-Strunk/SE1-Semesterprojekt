package uebung4.semesterproject.controller.commands;

import uebung4.semesterproject.view.UserStoryView;

public class DumpCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        UserStoryView view = new UserStoryView();
        view.startAusgabe(context.getContainer().getCurrentList());
    }

    @Override
    public String getDescription() {
        return "User Stories sortiert ausgeben";
    }
}
