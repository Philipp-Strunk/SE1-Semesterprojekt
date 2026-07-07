package uebung4.prototype.improvements.model.persistence;

import java.io.*;
import java.util.List;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL der Datei, in der die Objekte gespeichert werden
    private String LOCATION = "objects.ser";

    // Used only for testing purposes, if the location should be changed
    // Example: Location is a directory
    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    @Override
    /**
     * Method for saving a list of objects to a disk (HDD)
     */
    public void save(List<E> list) throws PersistenceException {
        try (FileOutputStream fos = new FileOutputStream(LOCATION);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject( list );
            System.out.println( "LOG: Es wurden " +  list.size() + " User Stories erfolgreich gespeichert!");
        }
        catch (IOException e) {
            throw new PersistenceException( PersistenceException.ExceptionType.SaveFailure ,
                    "Fehler beim Speichern der Datei!");
        }
    }

    @Override
    /**
     * Method for loading a list of objects from a disk (HDD)
     */
    public List<E> load() throws PersistenceException {
        try (FileInputStream fis = new FileInputStream(LOCATION);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                @SuppressWarnings("unchecked")
                List<E> list = (List<E>) obj;
                System.out.println("LOG: Es wurden " + list.size() + " User Stories erfolgreich geladen!");
                return list;
            }
            throw new PersistenceException( PersistenceException.ExceptionType.LoadFailure , "Fehler beim Laden der Datei!");
        }
        catch (FileNotFoundException e) {
            throw new PersistenceException( PersistenceException.ExceptionType.ConnectionNotAvailable
                    , "Error in opening the connection, File could not be found");
        }
        catch (IOException e) {
            throw new PersistenceException( PersistenceException.ExceptionType.LoadFailure , "Fehler beim Laden der Datei!");
        }
        catch (ClassNotFoundException e) {
            throw new PersistenceException( PersistenceException.ExceptionType.LoadFailure , "Fehler beim Laden der Datei! Class not found!");
        }
    }
}
