package uebung4.prototype.improvements.controller;

import uebung4.prototype.improvements.model.Container;
import uebung4.prototype.improvements.model.UserStory;
import uebung4.prototype.improvements.model.persistence.PersistenceStrategyMongoDB;
import uebung4.prototype.improvements.model.persistence.PersistenceStrategyStream;

public class Main {

	 /** Start-Methoden zum Starten des Programms
	 * (hier koennen ggf. weitere Initialisierungsarbeiten gemacht werden spaeter)
      */
    public static void main (String[] args) {
        Container con = Container.getInstance();
        if (usesMongoDB(args)) {
            con.setPersistenceStrategy(new PersistenceStrategyMongoDB());
            System.out.println("Persistenz: MongoDB");
        } else {
            con.setPersistenceStrategy(new PersistenceStrategyStream<UserStory>());
            System.out.println("Persistenz: Datei objects.ser");
        }
        InputDialog dialog = new InputDialog();
        dialog.startEingabe();
    }

    private static boolean usesMongoDB(String[] args) {
        return args.length > 0 && args[0].equals("--mongodb");
    }
}
