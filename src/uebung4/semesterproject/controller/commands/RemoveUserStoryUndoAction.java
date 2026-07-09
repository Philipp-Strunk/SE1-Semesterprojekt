package uebung4.semesterproject.controller.commands;

import uebung4.semesterproject.model.Container;

public class RemoveUserStoryUndoAction implements UndoAction {

    private final Container container;
    private final int userStoryId;

    public RemoveUserStoryUndoAction(Container container, int userStoryId) {
        this.container = container;
        this.userStoryId = userStoryId;
    }

    @Override
    public void undo() {
        if (container.removeUserStory(userStoryId)) {
            System.out.println("User Story " + userStoryId + " wurde entfernt.");
        } else {
            System.out.println("User Story " + userStoryId + " konnte nicht entfernt werden.");
        }
    }
}
