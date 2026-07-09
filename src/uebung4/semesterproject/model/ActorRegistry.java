package uebung4.semesterproject.model;

import java.util.ArrayList;
import java.util.List;

public class ActorRegistry {

    private final List<String> actors = new ArrayList<>();

    public boolean addActor(String actor) {
        if (containsActor(actor)) {
            return false;
        }
        actors.add(actor);
        return true;
    }

    public void setActors(List<String> actors) {
        this.actors.clear();
        for (String actor : actors) {
            addActor(actor);
        }
    }

    public boolean removeActor(String actor) {
        for (String currentActor : actors) {
            if (currentActor.equalsIgnoreCase(actor)) {
                actors.remove(currentActor);
                return true;
            }
        }
        return false;
    }

    public boolean containsActor(String actor) {
        for (String currentActor : actors) {
            if (currentActor.equalsIgnoreCase(actor)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getActors() {
        return new ArrayList<>(actors);
    }
}
