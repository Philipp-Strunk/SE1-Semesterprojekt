package uebung4.prototype.improvements.model.persistence;

import org.hbrs.mongodb.MongoDBControllerImpl;
import org.hbrs.mongodb.MongoDBControllerInterface;
import uebung4.prototype.improvements.model.UserStory;

import java.util.ArrayList;
import java.util.List;

public class PersistenceStrategyMongoDB implements PersistenceStrategy<UserStory> {

    private final MongoDBControllerInterface mongoDBController;

    public PersistenceStrategyMongoDB() {
        this(new MongoDBControllerImpl());
    }

    public PersistenceStrategyMongoDB(MongoDBControllerInterface mongoDBController) {
        this.mongoDBController = mongoDBController;
    }

    @Override
    public void save(List<UserStory> userStories) throws PersistenceException {
        try {
            mongoDBController.openConnection();
            mongoDBController.clearUserStories();
            for (UserStory userStory : userStories) {
                mongoDBController.insertUserStory(toMongoUserStory(userStory));
            }

            System.out.println("LOG: Es wurden " + userStories.size() + " User Stories erfolgreich in MongoDB gespeichert!");
        } catch (RuntimeException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.SaveFailure,
                    "Fehler beim Speichern in MongoDB: " + e.getMessage());
        } finally {
            mongoDBController.closeConnection();
        }
    }

    @Override
    public List<UserStory> load() throws PersistenceException {
        try {
            mongoDBController.openConnection();
            List<UserStory> userStories = new ArrayList<>();
            for (org.hbrs.mongodb.UserStory mongoUserStory : mongoDBController.listUserStories()) {
                userStories.add(fromMongoUserStory(mongoUserStory));
            }

            System.out.println("LOG: Es wurden " + userStories.size() + " User Stories erfolgreich aus MongoDB geladen!");
            return userStories;
        } catch (RuntimeException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.LoadFailure,
                    "Fehler beim Laden aus MongoDB: " + e.getMessage());
        } finally {
            mongoDBController.closeConnection();
        }
    }

    public void saveActors(List<String> actors) {
        try {
            mongoDBController.openConnection();
            mongoDBController.clearActors();
            for (String actor : actors) {
                mongoDBController.insertActor(actor);
            }
            System.out.println("LOG: Es wurden " + actors.size() + " Akteure erfolgreich in MongoDB gespeichert!");
        } finally {
            mongoDBController.closeConnection();
        }
    }

    public List<String> loadActors() {
        try {
            mongoDBController.openConnection();
            List<String> actors = mongoDBController.listActors();
            System.out.println("LOG: Es wurden " + actors.size() + " Akteure erfolgreich aus MongoDB geladen!");
            return actors;
        } finally {
            mongoDBController.closeConnection();
        }
    }

    org.hbrs.mongodb.UserStory toMongoUserStory(UserStory userStory) {
        org.hbrs.mongodb.UserStory mongoUserStory = new org.hbrs.mongodb.UserStory(
                userStory.getId(),
                userStory.getTitle(),
                userStory.getAcceptanceCriterion(),
                userStory.getValue(),
                userStory.getPenalty(),
                userStory.getEffort(),
                userStory.getRisk(),
                userStory.getPriority());
        mongoUserStory.setProject(userStory.getProject());
        return mongoUserStory;
    }

    UserStory fromMongoUserStory(org.hbrs.mongodb.UserStory mongoUserStory) {
        return new UserStory(
                mongoUserStory.getId(),
                mongoUserStory.getTitle(),
                mongoUserStory.getAcceptanceCriterion(),
                mongoUserStory.getValue(),
                mongoUserStory.getPenalty(),
                mongoUserStory.getEffort(),
                mongoUserStory.getRisk(),
                mongoUserStory.getProject());
    }
}
