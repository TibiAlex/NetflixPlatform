package main;

import java.util.ArrayList;
import java.util.Map;

public class User {

    //variabilele pentru fiecare user
    String username;
    String subscription;
    Map<String, Integer> history;
    ArrayList<String> favoriteMovies;

    //variabila in care voi salva de cate ori a dat rate un user
    int noOfRatings = 0;

    //constructor pentru a primii datele din input
    public User(String username, String subscription, Map<String, Integer> history, ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscription = subscription;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
    }

    //getter-ele necesare pe parcursul programului
    public String getUsername() { return username; }
    public String getSubscription() { return subscription; }
    public Map<String, Integer> getHistory() { return history; }
    public ArrayList<String> getFavoriteMovies() { return favoriteMovies; }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", subscription='" + subscription + '\'' +
                ", history=" + history +
                ", favoriteMovies=" + favoriteMovies +
                '}';
    }

    //functie de swap intre 2 variabile de tipul User
    public void swap(User a, User b) {
        User user1 = new User(a.username, a.subscription, a.history, a.favoriteMovies);
        user1.noOfRatings = a.noOfRatings;
        a.username = b.username;
        a.subscription = b.subscription;
        a.history = b.history;
        a.favoriteMovies = b.favoriteMovies;
        a.noOfRatings = b.noOfRatings;
        b.username = user1.username;
        b.subscription = user1.subscription;
        b.history = user1.history;
        b.favoriteMovies = user1.favoriteMovies;
        b.noOfRatings = user1.noOfRatings;
    }
}

