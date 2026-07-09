package uebung4.semesterproject.controller;

import uebung4.semesterproject.controller.commands.CommandHandler;
import uebung4.semesterproject.model.Container;

import java.util.Scanner;

public class InputDialog {

    private Container container = Container.getInstance();

    public InputDialog() {
    }

    /*
     * Diese Methode realisiert eine Eingabe ueber einen Scanner
     */
    public void startEingabe()  {
        Scanner scanner = new Scanner( System.in );
        CommandHandler commandHandler = new CommandHandler(scanner, container);

        System.out.println("UserStory-Tool V1.2");
        commandHandler.printHelp();

        while (!commandHandler.isExitRequested()) {
            System.out.print( "> "  );
            String strInput = scanner.nextLine().trim();

            if (strInput.isEmpty()) {
                continue;
            }

            commandHandler.handle(strInput);
        }
    }
}
