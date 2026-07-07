package uebung4.prototype.improvements.model.analysis;

import uebung4.prototype.improvements.model.ActorRegistry;
import uebung4.prototype.improvements.model.UserStory;

public class FunctionalityRule implements QualityRule {

    @Override
    public boolean isBroken(UserStory userStory, ActorRegistry actorRegistry) {
        String title = normalize(userStory.getTitle());
        return !title.contains("moechte") && !title.contains("möchte");
    }

    @Override
    public int getPenalty() {
        return 20;
    }

    @Override
    public String getDetail() {
        return "Keine klare Funktionalität mit 'möchte' zu erkennen (-20%)";
    }

    @Override
    public String getHint() {
        return "Formulieren Sie die Funktionalität mit 'möchte'.";
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.toLowerCase().trim();
    }
}
