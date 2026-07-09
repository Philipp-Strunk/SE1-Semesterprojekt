package uebung4.semesterproject.model;

import uebung4.semesterproject.model.persistence.PersistenceException;
import uebung4.semesterproject.model.persistence.PersistenceStrategy;

import java.util.ArrayList;
import java.util.List;

/*
 * Klasse zum Management sowie zur Eingabe unnd Ausgabe von User-Stories.
 * Die Anwendung wird über dies Klasse auch gestartet (main-Methode hier vorhanden)
 *
 * erstellt von Julius P., H-BRS 2025, Version 1.1
 *
 * Strategie für die Wiederverwendung (Reuse):
 * - Anlegen der Klasse UserStory
 * - Anpassen des Generic in der List-Klasse (ALT: Member, NEU: UserStory)
 * - Anpassen der Methodennamen
 *
 * (Was ist ihre Strategie zur Wiederverwendung?)
 *
 * Klasse UserStory implementiert Interface Member (UserStory implements Member)
 * Vorteil: Wiederverwendung von Member, ID verwenden; Strenge Implementierung gegen Interface
 * Nachteil: Viele Casts notwendig, um auf die anderen Methoden zuzugreifen
 *
 * Alternative: Container mit Generic entwickeln (z.B. Container<E>))
 *
 * Achtung: eine weitere Aufteilung dieser Klasse ist notwendig (siehe F2, vgl auch Klassendiagramm für 4-2)
 * 
 */

public class Container {
	 
	private List<UserStory> userStories = null;
	
	private static Container instance = new Container();

	/**
	 * Liefert ein Singleton zurück.
	 */
	public static Container getInstance() {
		return instance;
	}
	
	/**
	 * Vorschriftsmäßiges Ueberschreiben des Konstruktors (private) gemaess Singleton-Pattern (oder?)
	 * Nun auf private gesetzt! Vorher ohne Access Qualifier (--> dann package-private)
	 */
	private Container(){
		userStories = new ArrayList<>();
	}

	private PersistenceStrategy<UserStory> strategy = null;

	public void setPersistenceStrategy(PersistenceStrategy<UserStory> strategy) {
		this.strategy = strategy;
	}

	public PersistenceStrategy<UserStory> getPersistenceStrategy() {
		return strategy;
	}
	
	/*
	 * Methode zum Speichern der Liste. Es wird die komplette Liste
	 * inklusive ihrer gespeicherten UserStory-Objekte gespeichert.
	 * Verwendung der Klasse PersistenceStrategyStream
	 *
	 * 
	 */
	public void load() throws ContainerException {
		if (this.strategy == null) {
			throw new ContainerException("Keine Persistenzstrategie gesetzt.");
		}
        try {
            List<UserStory> loadedUserStories = this.strategy.load();
			this.userStories = loadedUserStories;
        } catch (PersistenceException e) {
            throw new ContainerException("Fehler beim Laden");
        }
    }

	/*
	 * Methode zum Laden der Liste. Es wird die komplette Liste
	 * inklusive ihrer gespeicherten UserStory-Objekte geladen.
	 * Verwendung der Klasse PersistenceStrategyStream
	 * 
	 */
	public void store() throws ContainerException {
		if (this.strategy == null) {
			throw new ContainerException("Keine Persistenzstrategie gesetzt.");
		}
        try {
            this.strategy.save(this.userStories);
        } catch (PersistenceException e) {
			throw new ContainerException("Fehler beim Speichern");
        }
    }

	/**
	 * Methode zum Hinzufügen eines Mitarbeiters unter Wahrung der Schlüsseleigenschaft
	 * @param userStory
	 * @throws ContainerException
	 */
	public void addUserStory ( UserStory userStory ) throws ContainerException {
		if (contains(userStory)) {
			throw new ContainerException("ID bereits vorhanden!");
		}
		userStories.add(userStory);
	}

	public boolean removeUserStory(int id) {
		for (UserStory userStory : userStories) {
			if (userStory.getId() == id) {
				userStories.remove(userStory);
				return true;
			}
		}
		return false;
	}

	public void clear() {
		userStories.clear();
	}

	/**
	 * Prüft, ob eine UserStory bereits vorhanden ist
	 * @param userStory
	 * @return
	 */
	public boolean contains( UserStory userStory ) {
		int id = userStory.getId();
		for ( UserStory currentUserStory : userStories) {
			if ( currentUserStory.getId() == id ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Ermittlung der Anzahl von internen UserStory
	 * -Objekten
	 * @return
	 */
	public int size() {
		return userStories.size();
	}

	/**
	 * Methode zur Rückgabe der aktuellen Liste mit Stories
	 * Findet aktuell keine Anwendung bei Hr. P.
	 * @return
	 */
	public List<UserStory> getCurrentList() {
		return new ArrayList<>(this.userStories);
	}

	/**
	 * Liefert eine bestimmte UserStory zurück
	 * @param id
	 * @return
	 */
	public UserStory getUserStory(int id) {
		for ( UserStory userStory : userStories) {
			if (id == userStory.getId() ){
				return userStory;
			}
		}
		return null;
	}
}
