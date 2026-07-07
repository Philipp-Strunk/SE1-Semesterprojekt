package uebung4.prototype.improvements.model;

import java.io.Serializable;

public class UserStory implements Comparable<UserStory>, Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String acceptanceCriterion;
    private int value;
    private int penalty;
    private int effort;
    private int risk;
    private double priority;
    private String project;

    public UserStory() {
    }

    public UserStory(int id, String title, String acceptanceCriterion, int value,
                     int penalty, int effort, int risk, String project) {
        this.id = id;
        this.title = title;
        this.acceptanceCriterion = acceptanceCriterion;
        this.value = value;
        this.penalty = penalty;
        this.effort = effort;
        this.risk = risk;
        this.project = project;
        calculatePriority();
    }

    public void calculatePriority() {
        this.priority = (double) (value + penalty) / (effort + risk);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getEffort() {
        return effort;
    }

    public void setEffort(int effort) {
        this.effort = effort;
    }

    public int getRisk() {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
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
    public int compareTo(UserStory input) {
        return Double.compare(input.getPriority(), this.getPriority());
    }

    @Override
    public String toString() {
        return "UserStory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", acceptanceCriterion='" + acceptanceCriterion + '\'' +
                ", value=" + value +
                ", penalty=" + penalty +
                ", effort=" + effort +
                ", risk=" + risk +
                ", priority=" + priority +
                ", project='" + project + '\'' +
                '}';
    }
}
