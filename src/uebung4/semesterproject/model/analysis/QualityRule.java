package uebung4.semesterproject.model.analysis;

import uebung4.semesterproject.model.ActorRegistry;
import uebung4.semesterproject.model.UserStory;

public interface QualityRule {

    boolean isBroken(UserStory userStory, ActorRegistry actorRegistry);

    int getPenalty();

    String getDetail();

    String getHint();
}
