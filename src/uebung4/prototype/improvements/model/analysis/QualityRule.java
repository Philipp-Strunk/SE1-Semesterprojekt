package uebung4.prototype.improvements.model.analysis;

import uebung4.prototype.improvements.model.ActorRegistry;
import uebung4.prototype.improvements.model.UserStory;

public interface QualityRule {

    boolean isBroken(UserStory userStory, ActorRegistry actorRegistry);

    int getPenalty();

    String getDetail();

    String getHint();
}
