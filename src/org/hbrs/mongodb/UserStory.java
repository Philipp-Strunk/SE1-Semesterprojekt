package org.hbrs.mongodb;

import java.io.Serializable;
import java.util.Objects;

public class UserStory implements Comparable<UserStory>, Serializable {

    private String title;
    private String acceptanceCriterion;
    private int effort = 0;
    private int id = 0;
    private int value = 0;
    private int risk = 0;
    private int penalty = 0;
    private double priority = 0.0;
    private String project;

    public UserStory() {
    }

    public UserStory(int id, String title, String acceptanceCriterion, int value,
                     int penalty, int effort, int risk, double priority) {
        this.id = id;
        this.title = title;
        this.acceptanceCriterion = acceptanceCriterion;
        this.value = value;
        this.penalty = penalty;
        this.effort = effort;
        this.risk = risk;
        this.priority = priority;
    }

    public int compareTo(UserStory input) {
        return Double.compare(input.getPriority(), this.getPriority());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAcceptanceCriterion() {
        return acceptanceCriterion;
    }

    public void setAcceptanceCriterion(String acceptanceCriterion) {
        this.acceptanceCriterion = acceptanceCriterion;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "UserStory{" +
                "title='" + title + '\'' +
                ", acceptanceCriterion='" + acceptanceCriterion + '\'' +
                ", effort=" + effort +
                ", id=" + id +
                ", value=" + value +
                ", risk=" + risk +
                ", penalty=" + penalty +
                ", priority=" + priority +
                ", project='" + project + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        UserStory userStory = (UserStory) object;
        return effort == userStory.effort
                && id == userStory.id
                && value == userStory.value
                && risk == userStory.risk
                && penalty == userStory.penalty
                && Double.compare(priority, userStory.priority) == 0
                && Objects.equals(title, userStory.title)
                && Objects.equals(acceptanceCriterion, userStory.acceptanceCriterion)
                && Objects.equals(project, userStory.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, acceptanceCriterion, effort, id, value, risk, penalty, priority, project);
    }
}
