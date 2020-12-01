package main;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Actors {

    //variabilele fiecarui actor
    String name;
    String career_description;
    List<String> filmography;
    Map<ActorsAwards, Integer> awards;

    //constructor pentru a primii variabilele din input
    public Actors(String name, String career_description, ArrayList<String> filmography, Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.career_description = career_description;
        this.filmography = filmography;
        this.awards = awards;
    }

    //getter-ele necesare
    public String getName() { return name; }
    public String getCareer_description() { return career_description; }
    public List<String> getFilmography() { return filmography; }
    public Map<ActorsAwards, Integer> getAwards() { return awards; }

    @Override
    public String toString() {
        return "Actors{" +
                "name='" + name + '\'' +
                ", career_description='" + career_description + '\'' +
                ", filmography=" + filmography +
                ", awards=" + awards +
                '}';
    }
}
