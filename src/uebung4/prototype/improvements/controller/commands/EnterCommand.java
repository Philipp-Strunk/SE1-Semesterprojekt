package uebung4.prototype.improvements.controller.commands;

import uebung4.prototype.improvements.model.ContainerException;
import uebung4.prototype.improvements.model.UserStory;

import java.util.Scanner;

public class EnterCommand implements Command {

    @Override
    public void execute(CommandContext context, String[] args) {
        try {
            UserStory userStory = readUserStory(context.getScanner());
            context.getContainer().addUserStory(userStory);
            context.addUndoAction(new RemoveUserStoryUndoAction(context.getContainer(), userStory.getId()));
            System.out.printf("User Story %d wurde erfasst. Prio: %.2f%n",
                    userStory.getId(), userStory.getPriority());
        } catch (ContainerException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "neue User Story eingeben";
    }

    private UserStory readUserStory(Scanner scanner) {
        int id = readPositiveInt(scanner, "ID: ", false);
        String title = readText(scanner, "Titel: ");
        String acceptanceCriterion = readText(scanner, "Akzeptanzkriterium: ");
        int value = readNumberInRange(scanner, "Mehrwert (1-5): ", 1, 5);
        int penalty = readNumberInRange(scanner, "Strafe (1-5): ", 1, 5);
        int effort = readPositiveInt(scanner, "Aufwand (Fibonacci-Zahl > 0): ", true);
        int risk = readNumberInRange(scanner, "Risiko (1-5): ", 1, 5);
        String project = readText(scanner, "Projekt: ");

        return new UserStory(id, title, acceptanceCriterion, value, penalty, effort, risk, project);
    }

    private String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Bitte einen Wert eingeben.");
        }
    }

    private int readNumberInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("Bitte eine Zahl zwischen " + min + " und " + max + " eingeben.");
        }
    }

    private int readPositiveInt(Scanner scanner, String prompt, boolean fibonacciRequired) {
        while (true) {
            int value = readInt(scanner, prompt);
            if (value > 0 && (!fibonacciRequired || isFibonacci(value))) {
                return value;
            }
            if (fibonacciRequired) {
                System.out.println("Bitte eine positive Fibonacci-Zahl eingeben: 1, 2, 3, 5, 8, 13, ...");
            } else {
                System.out.println("Bitte eine positive Zahl eingeben.");
            }
        }
    }

    private int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine ganze Zahl eingeben.");
            }
        }
    }

    private boolean isFibonacci(int value) {
        int first = 1;
        int second = 2;

        while (first < value) {
            int next = first + second;
            first = second;
            second = next;
        }

        return first == value;
    }
}
