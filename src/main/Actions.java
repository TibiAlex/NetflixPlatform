package main;

import java.util.ArrayList;
import java.util.List;

public class Actions {

    //variabilele necesare fiecarei actiuni
    int actionId;
    String actionType;
    String type;
    String username;
    String objectType;
    String sortType;
    String criteria;
    String title;
    String genre;
    int number;
    double grade;
    int seasonNumber;
    List<List<String>> filters = new ArrayList<>();

    //Setter-ele pentru variabilele actiunilor
    public void setActionId(int actionId) { this.actionId = actionId; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public void setType(String type) { this.type = type; }
    public void setUsername(String username) { this.username = username; }
    public void setObjectType(String objectType) { this.objectType = objectType; }
    public void setSortType(String sortType) { this.sortType = sortType; }
    public void setCriteria(String criteria) { this.criteria = criteria; }
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setNumber(int number) { this.number = number; }
    public void setGrade(double grade) { this.grade = grade; }
    public void setSeasonNumber(int seasonNumber) { this.seasonNumber = seasonNumber; }
    public void setFilters(List<List<String>> filters) { this.filters = filters; }

    //Getter-ele pentru variabilele actiunilor
    public int getActionId() { return actionId; }
    public String getActionType() { return actionType; }
    public String getType() { return type; }
    public String getUsername() { return username; }
    public String getObjectType() { return objectType; }
    public String getSortType() { return sortType; }
    public String getCriteria() { return criteria; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getNumber() { return number; }
    public double getGrade() { return grade; }
    public int getSeasonNumber() { return seasonNumber; }
    public List<List<String>> getFilters() { return filters; }

    //metoda to string suprascrisa
    @Override
    public String toString() {
        return "Actions{" +
                "actionId=" + actionId +
                ", actionType='" + actionType + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", objectType='" + objectType + '\'' +
                ", sortType='" + sortType + '\'' +
                ", criteria='" + criteria + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", number=" + number +
                ", grade=" + grade +
                ", seasonNumber=" + seasonNumber +
                ", filters=" + filters +
                '}';
    }
}
