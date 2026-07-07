package uebung4.prototype.improvements.model.analysis;

import uebung4.prototype.improvements.model.ActorRegistry;
import uebung4.prototype.improvements.model.UserStory;

public class KnownActorRule implements QualityRule {

    @Override
    public boolean isBroken(UserStory userStory, ActorRegistry actorRegistry) {
        String title = normalize(userStory.getTitle());
        for (String actor : actorRegistry.getActors()) {
            String expectedStart = "als " + actor.toLowerCase();
            if (title.startsWith(expectedStart)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getPenalty() {
        return 20;
    }

    @Override
    public String getDetail() {
        return "Akteur ist nicht bekannt (-20%)";
    }

    @Override
    public String getHint() {
        return "Registrieren Sie den Akteur mit addElement --actor <Akteur>.";
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.toLowerCase().trim();
    }
}
