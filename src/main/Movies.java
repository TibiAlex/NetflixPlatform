package main;

import java.util.ArrayList;
import java.util.List;

public class Movies {

    //variabilele necesare fiecarui film
    String name;
    int year;
    int duration;
    List<String> genres;
    List<String> actors;
    //rating-urile primite pe parcursul programului
    List<Double> ratings = new ArrayList<>();
    //userii care au dat rate, pentru a nu da de 2 ori
    List<String> raters = new ArrayList<>();

    //constructor pentru primirea datelor de intrare
    public Movies(String name, ArrayList<String> actors, ArrayList<String> genres, int year, int duration) {
        this.duration = duration;
        this.name = name;
        this.year = year;
        this.genres = genres;
        this.actors = actors;
    }

    //getter-ele necesare
    public String getName() { return name; }
    public int getYear() { return year; }
    public int getDuration() { return duration; }
    public List<String> getGenres() { return genres; }
    public List<String> getActors() { return actors; }

    @Override
    public String toString() {
        return "Movies{" +
                "name='" + name + '\'' +
                ", year=" + year +
                ", duration=" + duration +
                ", genres=" + genres +
                ", actors=" + actors +
                '}';
    }
}
