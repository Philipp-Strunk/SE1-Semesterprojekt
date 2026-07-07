package uebung4.prototype.improvements;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import uebung4.prototype.improvements.controller.commands.CommandHandler;
import uebung4.prototype.improvements.model.ActorRegistry;
import uebung4.prototype.improvements.model.Container;
import uebung4.prototype.improvements.model.ContainerException;
import uebung4.prototype.improvements.model.UserStory;
import uebung4.prototype.improvements.model.analysis.AnalysisResult;
import uebung4.prototype.improvements.model.analysis.QualityRule;
import uebung4.prototype.improvements.model.analysis.UserStoryAnalyzer;
import uebung4.prototype.improvements.model.persistence.PersistenceException;
import uebung4.prototype.improvements.model.persistence.PersistenceStrategy;
import uebung4.prototype.improvements.model.persistence.PersistenceStrategyStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserStoryToolTest {

    @AfterEach
    void clearContainer() {
        Container.getInstance().clear();
    }

    @Test
    void calculatesPriority() {
        UserStory story = createGoodStory(1);

        assertEquals(1.8, story.getPriority(), 0.0001);
    }

    @Test
    void registersActorsCaseInsensitive() {
        ActorRegistry actorRegistry = new ActorRegistry();

        assertTrue(actorRegistry.addActor("Student"));
        assertFalse(actorRegistry.addActor("student"));
        assertTrue(actorRegistry.containsActor("STUDENT"));
        assertTrue(actorRegistry.removeActor("Student"));
        assertFalse(actorRegistry.containsActor("Student"));
    }

    @Test
    void rejectsDuplicateUserStoryIds() throws ContainerException {
        Container container = Container.getInstance();
        container.addUserStory(createGoodStory(1));

        ContainerException exception = assertThrows(ContainerException.class,
                () -> container.addUserStory(createGoodStory(1)));

        assertEquals("ID bereits vorhanden!", exception.getMessage());
    }

    @Test
    void returnsCopyOfCurrentList() throws ContainerException {
        Container container = Container.getInstance();
        container.addUserStory(createGoodStory(1));

        List<UserStory> copy = container.getCurrentList();
        copy.clear();

        assertEquals(1, container.size());
    }

    @Test
    void removesUserStoryById() throws ContainerException {
        Container container = Container.getInstance();
        container.addUserStory(createGoodStory(1));

        assertTrue(container.removeUserStory(1));
        assertFalse(container.removeUserStory(1));
        assertEquals(0, container.size());
    }

    @Test
    void analyzesGoodUserStory() {
        ActorRegistry actorRegistry = new ActorRegistry();
        actorRegistry.addActor("Student");
        UserStoryAnalyzer analyzer = new UserStoryAnalyzer(actorRegistry);

        AnalysisResult result = analyzer.analyze(createGoodStory(1));

        assertEquals(100, result.getQuality());
        assertEquals("sehr gut", result.getRating());
        assertFalse(result.hasDetails());
    }

    @Test
    void analyzesMissingUserStoryParts() {
        ActorRegistry actorRegistry = new ActorRegistry();
        UserStoryAnalyzer analyzer = new UserStoryAnalyzer(actorRegistry);
        UserStory story = new UserStory(
                2,
                "Lernräume suchen",
                "Suchergebnis zeigt freie Räume.",
                5,
                4,
                3,
                2,
                "AllMyResources");

        AnalysisResult result = analyzer.analyze(story);

        assertEquals(30, result.getQuality());
        assertEquals(3, result.getDetails().size());
        assertEquals(3, result.getHints().size());
    }

    @Test
    void analyzerCanUseAdditionalQualityRule() {
        ActorRegistry actorRegistry = new ActorRegistry();
        actorRegistry.addActor("Student");
        QualityRule shortTitleRule = new QualityRule() {
            @Override
            public boolean isBroken(UserStory userStory, ActorRegistry actorRegistry) {
                return userStory.getTitle().length() < 100;
            }

            @Override
            public int getPenalty() {
                return 10;
            }

            @Override
            public String getDetail() {
                return "User Story ist sehr kurz (-10%)";
            }

            @Override
            public String getHint() {
                return "Beschreiben Sie die User Story etwas genauer.";
            }
        };
        UserStoryAnalyzer analyzer = new UserStoryAnalyzer(actorRegistry, Arrays.asList(shortTitleRule));

        AnalysisResult result = analyzer.analyze(createGoodStory(1));

        assertEquals(90, result.getQuality());
        assertEquals("sehr gut", result.getRating());
        assertEquals(1, result.getDetails().size());
        assertEquals("User Story ist sehr kurz (-10%)", result.getDetails().get(0));
    }

    @Test
    void storesAndLoadsUserStoriesWithStreamPersistence() throws IOException, PersistenceException {
        File file = File.createTempFile("user-stories-test", ".ser");
        file.deleteOnExit();

        PersistenceStrategyStream<UserStory> strategy = new PersistenceStrategyStream<>();
        strategy.setLOCATION(file.getAbsolutePath());

        List<UserStory> stories = new ArrayList<>();
        stories.add(createGoodStory(1));

        strategy.save(stories);
        List<UserStory> loadedStories = strategy.load();

        assertEquals(1, loadedStories.size());
        assertEquals(1, loadedStories.get(0).getId());
        assertEquals(stories.get(0).getTitle(), loadedStories.get(0).getTitle());
    }

    @Test
    void containerUsesChangedPersistenceStrategy() throws ContainerException {
        Container container = Container.getInstance();
        TestPersistenceStrategy firstStrategy = new TestPersistenceStrategy();
        TestPersistenceStrategy secondStrategy = new TestPersistenceStrategy();

        container.setPersistenceStrategy(firstStrategy);
        container.addUserStory(createGoodStory(1));
        container.store();

        container.setPersistenceStrategy(secondStrategy);
        container.store();

        assertEquals(1, firstStrategy.getSaveCounter());
        assertEquals(1, secondStrategy.getSaveCounter());
        assertEquals(1, firstStrategy.getSavedStories().size());
        assertEquals(1, secondStrategy.getSavedStories().size());
    }

    @Test
    void commandHandlerCanEnterAndUndoUserStory() {
        Container container = Container.getInstance();
        Scanner scanner = new Scanner(String.join(System.lineSeparator(),
                "1",
                "Als Student möchte ich Lernräume suchen, um schneller einen Platz zu finden.",
                "Suchergebnis zeigt freie Räume.",
                "5",
                "4",
                "3",
                "2",
                "AllMyResources"));
        CommandHandler commandHandler = new CommandHandler(scanner, container);

        commandHandler.handle("enter");
        assertEquals(1, container.size());

        commandHandler.handle("undo");
        assertEquals(0, container.size());
    }

    @Test
    void commandHandlerCanAddAndUndoActor() {
        Container container = Container.getInstance();
        Scanner scanner = new Scanner("");
        CommandHandler commandHandler = new CommandHandler(scanner, container);

        commandHandler.handle("addElement --actor Student");
        assertTrue(commandHandler.containsActor("Student"));

        commandHandler.handle("undo");
        assertFalse(commandHandler.containsActor("Student"));
    }

    private UserStory createGoodStory(int id) {
        return new UserStory(
                id,
                "Als Student möchte ich Lernräume suchen, um schneller einen Platz zu finden.",
                "Suchergebnis zeigt freie Räume.",
                5,
                4,
                3,
                2,
                "AllMyResources");
    }

    private static class TestPersistenceStrategy implements PersistenceStrategy<UserStory> {

        private final List<UserStory> savedStories = new ArrayList<>();
        private int saveCounter = 0;

        @Override
        public void save(List<UserStory> userStories) {
            saveCounter++;
            savedStories.clear();
            savedStories.addAll(userStories);
        }

        @Override
        public List<UserStory> load() {
            return new ArrayList<>(savedStories);
        }

        int getSaveCounter() {
            return saveCounter;
        }

        List<UserStory> getSavedStories() {
            return new ArrayList<>(savedStories);
        }
    }
}
