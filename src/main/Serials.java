package main;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

public class Serials {

    //variabilele necesare fiecarui serial
    String title;
    List<String> cast;
    List<String> genres;
    int numberOfSeasons;
    List<Season> seasons;
    int year;
    //rating-urile primite pe sezon
    List<Double> ratings = new ArrayList<>(10);
    //numarul de rating-uri primite la fiecare sezon , pentru a face usor media la final
    List<Integer> number = new ArrayList<>(10);

    //constructor pentru a citii datele de intrare
    public Serials(String title, ArrayList<String> cast, ArrayList<String> genres, int numberOfSeasons, final ArrayList<Season> seasons, int year) {
        this.title = title;
        this.cast = cast;
        this.genres = genres;
        this.year = year;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        for(int i = 0; i < numberOfSeasons ; i++) {
            ratings.add(i, 0.0);
            number.add(i, 0);
        }
    }

    //getter-ele necesare
    public String getTitle() { return title; }
    public List<String> getCast() { return cast; }
    public List<String> getGenres() { return genres; }
    public int getNumberOfSeasons() { return numberOfSeasons; }
    public List<Season> getSeasons() { return seasons; }
    public int getYear() { return year; }

    @Override
    public String toString() {
        return "Serials{" +
                "title='" + title + '\'' +
                ", cast=" + cast +
                ", genres=" + genres +
                ", numberOfSeasons=" + numberOfSeasons +
                ", seasons=" + seasons +
                ", year=" + year +
                '}';
    }
}
