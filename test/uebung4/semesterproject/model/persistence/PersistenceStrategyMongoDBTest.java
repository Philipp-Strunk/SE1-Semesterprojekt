package uebung4.semesterproject.model.persistence;

import org.junit.jupiter.api.Test;
import uebung4.semesterproject.model.UserStory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersistenceStrategyMongoDBTest {

    @Test
    void convertsUserStoryToMongoUserStory() {
        PersistenceStrategyMongoDB strategy = new PersistenceStrategyMongoDB();
        UserStory userStory = createUserStory();

        org.hbrs.mongodb.UserStory mongoUserStory = strategy.toMongoUserStory(userStory);

        assertEquals(1, mongoUserStory.getId());
        assertEquals("Als Student möchte ich Lernräume suchen, um schneller einen Platz zu finden.", mongoUserStory.getTitle());
        assertEquals("Suchergebnis zeigt freie Räume.", mongoUserStory.getAcceptanceCriterion());
        assertEquals(5, mongoUserStory.getValue());
        assertEquals(4, mongoUserStory.getPenalty());
        assertEquals(3, mongoUserStory.getEffort());
        assertEquals(2, mongoUserStory.getRisk());
        assertEquals(1.8, mongoUserStory.getPriority(), 0.0001);
        assertEquals("AllMyResources", mongoUserStory.getProject());
    }

    @Test
    void convertsMongoUserStoryToUserStory() {
        PersistenceStrategyMongoDB strategy = new PersistenceStrategyMongoDB();
        org.hbrs.mongodb.UserStory mongoUserStory = strategy.toMongoUserStory(createUserStory());

        UserStory userStory = strategy.fromMongoUserStory(mongoUserStory);

        assertEquals(1, userStory.getId());
        assertEquals("Als Student möchte ich Lernräume suchen, um schneller einen Platz zu finden.", userStory.getTitle());
        assertEquals("Suchergebnis zeigt freie Räume.", userStory.getAcceptanceCriterion());
        assertEquals(5, userStory.getValue());
        assertEquals(4, userStory.getPenalty());
        assertEquals(3, userStory.getEffort());
        assertEquals(2, userStory.getRisk());
        assertEquals(1.8, userStory.getPriority(), 0.0001);
        assertEquals("AllMyResources", userStory.getProject());
    }

    private UserStory createUserStory() {
        return new UserStory(
                1,
                "Als Student möchte ich Lernräume suchen, um schneller einen Platz zu finden.",
                "Suchergebnis zeigt freie Räume.",
                5,
                4,
                3,
                2,
                "AllMyResources");
    }
}
