package uebung4.semesterproject.model.analysis;

import uebung4.semesterproject.model.ActorRegistry;
import uebung4.semesterproject.model.UserStory;

public class WrittenBenefitRule implements QualityRule {

    @Override
    public boolean isBroken(UserStory userStory, ActorRegistry actorRegistry) {
        String title = normalize(userStory.getTitle());
        return !title.contains(" um ");
    }

    @Override
    public int getPenalty() {
        return 30;
    }

    @Override
    public String getDetail() {
        return "Kein schriftlicher Mehrwert zu erkennen (-30%)";
    }

    @Override
    public String getHint() {
        return "Fügen Sie einen schriftlichen Mehrwert mit 'um ...' hinzu.";
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.toLowerCase().trim();
    }
}
