package org.hbrs.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;

public class MongoDBControllerImpl implements MongoDBControllerInterface {

    private static final String MONGODB_COLLECTION = "se1_pstrun2s_userstories";
    private static final String MONGODB_ACTOR_COLLECTION = "se1_pstrun2s_actors";

    private static final String MONGODB_HOST = "iar-mongo.inf.h-brs.de";
    private static final int MONGODB_PORT = 27017;
    private static final String MONGODB_DATABASE = "demo";
    private static final String MONGODB_USERNAME = "demo";
    private static final String MONGODB_PASSWORD = "demo!";
    private static final String MONGODB_AUTH_DATABASE = MONGODB_DATABASE;

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;
    private MongoCollection<Document> mongoActorCollection;

    @Override
    public void openConnection() throws IllegalStateException {
        if (this.mongoClient != null) {
            throw new IllegalStateException("MongoDBController ist bereits geöffnet.");
        }

        String authString = "";
        if (!MONGODB_USERNAME.trim().isEmpty()) {
            authString = MONGODB_USERNAME + ":" + MONGODB_PASSWORD + "@";
        }

        String connectionString = "mongodb://" + authString + MONGODB_HOST + ":" + MONGODB_PORT
                + "/?authSource=" + MONGODB_AUTH_DATABASE;
        this.mongoClient = MongoClients.create(connectionString);
        this.mongoDatabase = mongoClient.getDatabase(MONGODB_DATABASE);
        this.mongoCollection = this.mongoDatabase.getCollection(MONGODB_COLLECTION);
        this.mongoActorCollection = this.mongoDatabase.getCollection(MONGODB_ACTOR_COLLECTION);
    }

    @Override
    public void closeConnection() {
        if (this.mongoClient == null) {
            return;
        }
        this.mongoClient.close();
        this.mongoClient = null;
        this.mongoDatabase = null;
        this.mongoCollection = null;
        this.mongoActorCollection = null;
    }

    @Override
    public void insertUserStory(UserStory story) {
        this.mongoCollection.insertOne(this.storyToDocument(story));
    }

    @Override
    public void updateUserStory(int id, UserStory story) {
        this.mongoCollection.replaceOne(Filters.eq("id", id), storyToDocument(story));
    }

    @Override
    public void deleteUserStory(int id) {
        this.mongoCollection.deleteOne(Filters.eq("id", id));
    }

    @Override
    public void clearUserStories() {
        this.mongoCollection.drop();
    }

    @Override
    public UserStory readUserStory(int id) {
        Document document = this.mongoCollection.find(Filters.eq("id", id)).first();
        if (document == null) {
            return null;
        }
        return documentToStory(document);
    }

    @Override
    public ArrayList<UserStory> listUserStories() {
        ArrayList<UserStory> results = new ArrayList<>();
        this.mongoCollection.find().map(this::documentToStory).into(results);
        return results;
    }

    @Override
    public void insertActor(String actor) {
        Document document = new Document();
        document.append("name", actor);
        this.mongoActorCollection.insertOne(document);
    }

    @Override
    public void clearActors() {
        this.mongoActorCollection.drop();
    }

    @Override
    public ArrayList<String> listActors() {
        ArrayList<String> actors = new ArrayList<>();
        for (Document document : this.mongoActorCollection.find()) {
            actors.add(document.getString("name"));
        }
        return actors;
    }

    private Document storyToDocument(UserStory story) {
        Document document = new Document();
        document.append("id", story.getId());
        document.append("title", story.getTitle());
        document.append("acceptanceCriterion", story.getAcceptanceCriterion());
        document.append("project", story.getProject());
        document.append("priority", story.getPriority());
        document.append("effort", story.getEffort());
        document.append("value", story.getValue());
        document.append("risk", story.getRisk());
        document.append("penalty", story.getPenalty());
        return document;
    }

    private UserStory documentToStory(Document document) {
        UserStory story = new UserStory(
                document.getInteger("id"),
                document.getString("title"),
                document.getString("acceptanceCriterion"),
                document.getInteger("value"),
                document.getInteger("penalty"),
                document.getInteger("effort"),
                document.getInteger("risk"),
                document.getDouble("priority"));
        story.setProject(document.getString("project"));
        return story;
    }
}
