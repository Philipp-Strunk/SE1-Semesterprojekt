package uebung4.semesterproject.model.analysis;

import uebung4.semesterproject.model.ActorRegistry;
import uebung4.semesterproject.model.UserStory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserStoryAnalyzer {

    private final ActorRegistry actorRegistry;
    private final List<QualityRule> rules;

    public UserStoryAnalyzer(ActorRegistry actorRegistry) {
        this.actorRegistry = actorRegistry;
        this.rules = Arrays.asList(
                new KnownActorRule(),
                new FunctionalityRule(),
                new WrittenBenefitRule());
    }

    public UserStoryAnalyzer(ActorRegistry actorRegistry, List<QualityRule> rules) {
        this.actorRegistry = actorRegistry;
        this.rules = rules;
    }

    public AnalysisResult analyze(UserStory userStory) {
        int quality = 100;
        List<String> details = new ArrayList<>();
        List<String> hints = new ArrayList<>();

        for (QualityRule rule : rules) {
            if (rule.isBroken(userStory, actorRegistry)) {
                quality = quality - rule.getPenalty();
                details.add(rule.getDetail());
                hints.add(rule.getHint());
            }
        }

        if (quality < 0) {
            quality = 0;
        }

        return new AnalysisResult(quality, getRating(quality), details, hints);
    }

    public String getRating(double quality) {
        if (quality >= 90) {
            return "sehr gut";
        }
        if (quality >= 75) {
            return "gut";
        }
        if (quality >= 60) {
            return "befriedigend";
        }
        if (quality >= 40) {
            return "ausreichend";
        }
        return "mangelhaft";
    }
}
