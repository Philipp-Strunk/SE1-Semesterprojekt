package uebung4.prototype.improvements.view;
import uebung4.prototype.improvements.model.UserStory;

import java.util.List;
import java.util.stream.Collectors;

public class UserStoryView {
    public void startAusgabe(List<UserStory> userStories) {
        List<UserStory> sortedUserStories = userStories.stream()
                .sorted()
                .collect(Collectors.toList());

        if (sortedUserStories.isEmpty()) {
            System.out.println("Keine User Stories vorhanden.");
            return;
        }

        System.out.printf("%-4s %-28s %-18s %8s %8s %8s %8s %8s %-30s%n",
                "ID", "Titel", "Projekt", "Mehrwert", "Strafe", "Aufwand", "Risiko", "Prio", "Akzeptanzkriterium");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        for (UserStory story : sortedUserStories) {
            System.out.printf("%-4d %-28s %-18s %8d %8d %8d %8d %8.2f %-30s%n",
                    story.getId(),
                    shorten(story.getTitle(), 28),
                    shorten(story.getProject(), 18),
                    story.getValue(),
                    story.getPenalty(),
                    story.getEffort(),
                    story.getRisk(),
                    story.getPriority(),
                    shorten(story.getAcceptanceCriterion(), 30));
        }
    }

    private String shorten(String text, int length) {
        if (text == null) {
            return "";
        }
        if (text.length() <= length) {
            return text;
        }
        return text.substring(0, length - 3) + "...";
    }
}
