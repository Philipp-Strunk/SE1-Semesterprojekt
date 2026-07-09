package uebung4.semesterproject.view;
import uebung4.semesterproject.model.UserStory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.Integer.max;

public class UserStoryView {
    private static final String COLUMN_SEPARATOR = "  ";
    private static final int MAX_TITLE_WIDTH = 28;
    private static final int MAX_PROJECT_WIDTH = 18;
    private static final int MAX_ACCEPTANCE_CRITERION_WIDTH = 60;

    public void startAusgabe(List<UserStory> userStories) {
        List<UserStory> sortedUserStories = userStories.stream()
                .sorted()
                .collect(Collectors.toList());

        if (sortedUserStories.isEmpty()) {
            System.out.println("Keine User Stories vorhanden.");
            return;
        }

        String idHeader = "ID";
        int idSpacing = getColumnWidth(sortedUserStories, idHeader, story -> String.valueOf(story.getId()));

        String titleHeader = "Titel";
        int titleSpacing = getColumnWidth(sortedUserStories, titleHeader, UserStory::getTitle, MAX_TITLE_WIDTH);

        String projectHeader = "Projekt";
        int projectSpacing = getColumnWidth(sortedUserStories, projectHeader, UserStory::getProject, MAX_PROJECT_WIDTH);

        String valueHeader = "Mehrwert";
        int valueSpacing = getColumnWidth(sortedUserStories, valueHeader, story -> String.valueOf(story.getValue()));

        String penaltyHeader = "Strafe";
        int penaltySpacing = getColumnWidth(sortedUserStories, penaltyHeader, story -> String.valueOf(story.getPenalty()));

        String effortHeader = "Aufwand";
        int effortSpacing = getColumnWidth(sortedUserStories, effortHeader, story -> String.valueOf(story.getEffort()));

        String riskHeader = "Risiko";
        int riskSpacing = getColumnWidth(sortedUserStories, riskHeader, story -> String.valueOf(story.getRisk()));

        String priorityHeader = "Prio";
        int prioritySpacing = getColumnWidth(sortedUserStories, priorityHeader, story -> String.format("%.2f", story.getPriority()));

        String acceptanceCriterionHeader = "Akzeptanzkriterium";
        int acceptanceCriterionSpacing = getColumnWidth(sortedUserStories, acceptanceCriterionHeader,
                UserStory::getAcceptanceCriterion, MAX_ACCEPTANCE_CRITERION_WIDTH);

        String format = "%-" + idSpacing + "s" + COLUMN_SEPARATOR
                + "%-" + titleSpacing + "s" + COLUMN_SEPARATOR
                + "%-" + projectSpacing + "s" + COLUMN_SEPARATOR
                + "%-" + valueSpacing + "s" + COLUMN_SEPARATOR
                + "%-" + penaltySpacing + "s" + COLUMN_SEPARATOR
                + "%-" + effortSpacing + "s" + COLUMN_SEPARATOR
                + "%-" + riskSpacing + "s" + COLUMN_SEPARATOR
                + "%-" + prioritySpacing + "s" + COLUMN_SEPARATOR
                + "%-" + acceptanceCriterionSpacing + "s%n";

        System.out.printf(format,
                idHeader, titleHeader, projectHeader, valueHeader, penaltyHeader,
                effortHeader, riskHeader, priorityHeader, acceptanceCriterionHeader);
        System.out.println("-".repeat(idSpacing + titleSpacing + projectSpacing + valueSpacing
                + penaltySpacing + effortSpacing + riskSpacing + prioritySpacing
                + acceptanceCriterionSpacing + (COLUMN_SEPARATOR.length() * 8)));

        for (UserStory story : sortedUserStories) {
            System.out.printf(format,
                    story.getId(),
                    shorten(story.getTitle(), MAX_TITLE_WIDTH),
                    shorten(story.getProject(), MAX_PROJECT_WIDTH),
                    story.getValue(),
                    story.getPenalty(),
                    story.getEffort(),
                    story.getRisk(),
                    String.format("%.2f", story.getPriority()),
                    shorten(story.getAcceptanceCriterion(), MAX_ACCEPTANCE_CRITERION_WIDTH));
        }
    }

    private int getColumnWidth(List<UserStory> userStories, String header, Function<UserStory, String> mapper) {
        return getColumnWidth(userStories, header, mapper, Integer.MAX_VALUE);
    }

    private int getColumnWidth(List<UserStory> userStories, String header, Function<UserStory, String> mapper, int maxWidth) {
        int longestValue = userStories.stream()
                .map(mapper)
                .map(this::nullToEmpty)
                .mapToInt(String::length)
                .max()
                .orElse(0);
        return Math.min(max(longestValue, header.length()), maxWidth);
    }

    private String nullToEmpty(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }

    private String shorten(String text, int length) {
        text = nullToEmpty(text);
        if (text.length() <= length) {
            return text;
        }
        return text.substring(0, length - 3) + "...";
    }
}
