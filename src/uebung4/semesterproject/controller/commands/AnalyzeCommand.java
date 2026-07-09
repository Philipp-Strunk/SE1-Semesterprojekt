package uebung4.semesterproject.controller.commands;

import uebung4.semesterproject.model.UserStory;
import uebung4.semesterproject.model.analysis.AnalysisResult;
import uebung4.semesterproject.model.analysis.UserStoryAnalyzer;

import java.util.List;

public class AnalyzeCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        if (args.length < 2 || args.length > 4) {
            printUsage();
            return;
        }

        UserStoryAnalyzer analyzer = new UserStoryAnalyzer(context.getActorRegistry());

        if (args[1].equals("--all")) {
            analyzeAll(context, analyzer, args);
            return;
        }

        boolean detailsRequested = false;
        boolean hintsRequested = false;
        for (int i = 2; i < args.length; i++) {
            if (args[i].equals("--details")) {
                detailsRequested = true;
            } else if (args[i].equals("--hints")) {
                hintsRequested = true;
            } else {
                printUsage();
                return;
            }
        }

        if (hintsRequested && !detailsRequested) {
            System.out.println("Nutzung: analyze <ID> --details --hints");
            return;
        }

        if (args[1].startsWith("--")) {
            printUsage();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Bitte eine gültige ID angeben.");
            return;
        }

        UserStory userStory = context.getContainer().getUserStory(id);
        if (userStory == null) {
            System.out.println("Keine User Story mit der ID " + id + " gefunden.");
            return;
        }

        AnalysisResult result = analyzer.analyze(userStory);

        System.out.println("Die User Story mit der ID " + id + " hat folgende Qualität:");
        System.out.println(result.getQuality() + "% (" + result.getRating() + ")");

        if (detailsRequested) {
            System.out.println("Details:");
            if (!result.hasDetails()) {
                System.out.println("Alles ok");
            } else {
                for (String detail : result.getDetails()) {
                    System.out.println(detail);
                }
            }

            if (hintsRequested) {
                System.out.println("Hints:");
                if (!result.hasHints()) {
                    System.out.println("Keine Hinweise notwendig.");
                } else {
                    for (String hint : result.getHints()) {
                        System.out.println(hint);
                    }
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Qualität einer User Story analysieren";
    }

    private void analyzeAll(CommandContext context, UserStoryAnalyzer analyzer, String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        List<UserStory> userStories = context.getContainer().getCurrentList();
        if (userStories.isEmpty()) {
            System.out.println("Keine User Stories vorhanden.");
            return;
        }

        int sum = 0;
        for (UserStory userStory : userStories) {
            AnalysisResult result = analyzer.analyze(userStory);
            sum = sum + result.getQuality();
        }

        double average = (double) sum / userStories.size();
        if (userStories.size() == 1) {
            System.out.println("Ihre 1 User Story hat durchschnittlich folgende Qualität:");
        } else {
            System.out.println("Ihre " + userStories.size() + " User Stories haben durchschnittlich folgende Qualität:");
        }
        System.out.printf("%.1f%% (%s)%n", average, analyzer.getRating(average));
    }

    private void printUsage() {
        System.out.println("Nutzung: analyze <ID> [--details] [--hints] oder analyze --all");
    }
}
