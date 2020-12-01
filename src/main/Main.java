package main;

import actor.ActorsAwards;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.ActionInputData;
import fileio.ActorInputData;
import fileio.Input;
import fileio.InputLoader;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        //In actorsList am salvat informatiile despre actori
        List<ActorInputData> actors = new ArrayList<>(input.getActors());
        List<Actors> actorsList = new ArrayList<>();
        for (int i = 0; i < actors.size(); i++) {
                Actors actor = new Actors(actors.get(i).getName(), actors.get(i).getCareerDescription(),
                        actors.get(i).getFilmography(), actors.get(i).getAwards());
                actorsList.add(i, actor);
        }

        //In moviesList am salvat informatiile despre filme
        List<MovieInputData> movies = new ArrayList<>(input.getMovies());
        List<Movies> moviesList = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            Movies movie = new Movies(movies.get(i).getTitle(), movies.get(i).getCast(), movies.get(i).getGenres(),
                    movies.get(i).getYear(), movies.get(i).getDuration());
            moviesList.add(i, movie);
        }

        //In serialsList am salvat informatiile despre seriale
        List<SerialInputData> serials = new ArrayList<>(input.getSerials());
        List<Serials> serialsList = new ArrayList<>();
        for (int i = 0; i < serials.size(); i++) {
            Serials serial = new Serials(serials.get(i).getTitle(), serials.get(i).getCast(), serials.get(i).getGenres(),
                    serials.get(i).getNumberSeason(), serials.get(i).getSeasons(), serials.get(i).getYear());
            serialsList.add(i, serial);
        }

        //In userList am salvat informatiile despre useri
        List<UserInputData> users = new ArrayList<>(input.getUsers());
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
             User user = new User(users.get(i).getUsername(), users.get(i).getSubscriptionType(),
                     users.get(i).getHistory(), users.get(i).getFavoriteMovies());
             userList.add(i, user);
        }

        //In actionsList am salvat informatiile despre comenzi, query-uri si recomandari
        List<ActionInputData> actions = new ArrayList<>(input.getCommands());
        List<Actions> actionsList = new ArrayList<>();
        for (int i = 0; i < actions.size(); i++) {
              Actions action = new Actions();
              action.setActionId(actions.get(i).getActionId());
              action.setActionType(actions.get(i).getActionType());
              action.setCriteria(actions.get(i).getCriteria());
              action.setFilters(actions.get(i).getFilters());
              action.setGenre(actions.get(i).getGenre());
              action.setTitle(actions.get(i).getTitle());
              action.setType(actions.get(i).getType());
              action.setUsername(actions.get(i).getUsername());
              action.setObjectType(actions.get(i).getObjectType());
              action.setSeasonNumber(actions.get(i).getSeasonNumber());
              action.setSortType(actions.get(i).getSortType());
              action.setNumber(actions.get(i).getNumber());
              action.setGrade(actions.get(i).getGrade());
              actionsList.add(i, action);
        }

        //Parcurgem actionsList si completam in functie de caz
        for (Actions value : actionsList) {
            //Exista trei tipuri de actiuni diferite , command, query si recommandation
            if (value.actionType.equals("command")) {
                //Pentru command exista 3 tipuri
                //view pentru a adauga un film sau serial in istoricul unui user
                //pentru acesta am parcurs userii pana l-am gasit pe cel ce ne intereseaza
                //am verificat daca este prima data cand urmareste video-ul si il adaugam
                //sau incrementam in functie de caz
                if (value.type.equals("view")) {
                    int ok = 0;
                    for (User user : userList) {
                        if (user.username.equals(value.username)) {
                            if (user.history.containsKey(value.title)) {
                                ok = user.history.get(value.title) + 1;
                                user.history.put(value.title, user.history.get(value.title) + 1);
                            } else {
                                ok = 1;
                                user.history.put(value.title, 1);
                            }
                        }
                    }
                    arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "success -> " + value.title
                            + " was viewed with total views of " + ok));
                }
                //favorite pentru a adauga un videoclip in sectiunea de favorite
                //pentru acesta am parcurs userii pana il gaseam pe cel care ne intereseaza
                //apoi cautam daca filmul sau serialul se gaseste in istoricul sau
                //daca nu se gaseste afisam eroare
                //afisam eroare si daca acesta se afla deja in lista de favorite
                //in cazul in care se gaseste in istoric si nu este deja adaugat la favorite il adaugam
                if (value.type.equals("favorite")) {
                    for (User user : userList) {
                        if (user.username.equals(value.username)) {
                            if (user.history.containsKey(value.title)) {
                                int ok = 0;
                                for (int k = 0; k < user.favoriteMovies.size(); k++) {
                                    if (user.favoriteMovies.get(k).equals(value.title)) {
                                        arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "error -> "
                                                + value.title + " is already in favourite list"));
                                        ok = 1;
                                    }
                                }
                                if (ok == 0) {
                                    user.favoriteMovies.add(value.title);
                                    arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "success -> "
                                            + value.title + " was added as favourite"));
                                }

                            } else {
                                arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "error -> "
                                        + value.title + " is not seen"));
                            }
                        }
                    }
                }
                //rating , comanda ce adauga o nota pentru filme si seriale
                //cautam username-ul care ne intereseaza, apoi verificam daca acesta a urmarit video-ul
                //daca nu a urmarit afisam eroare
                //daca l-a urmarit verificam daca user a dat deja o nota , daca nu o adaugam intr-un vector
                if (value.type.equals("rating")) {
                    for (User user : userList) {
                        if (user.username.equals(value.username)) {
                            if (user.history.containsKey(value.title)) {
                                if (value.seasonNumber == 0) {
                                    for (Movies item : moviesList) {
                                        if (item.name.equals(value.title)) {
                                            if (!item.raters.contains(value.username)) {
                                                item.ratings.add(value.grade);
                                                item.raters.add(value.username);
                                                arrayResult.add(fileWriter.writeFile(value.actionId, filePath2,
                                                        "success -> " + value.title + " was rated with "
                                                                + value.grade + " by " + value.username));
                                            } else {
                                                arrayResult.add(fileWriter.writeFile(value.actionId, filePath2,
                                                        "error -> " + value.title + " has already been rated"));
                                            }
                                        }
                                    }
                                } else {
                                    for (Serials item : serialsList) {
                                        if (item.title.equals(value.title)) {
                                              Double oky = item.ratings.get(value.seasonNumber - 1);
                                              item.ratings.add(value.seasonNumber - 1, oky + value.grade);
                                              int ok = item.number.get(value.seasonNumber - 1);
                                              item.number.add(value.seasonNumber - 1,  ok + 1);
                                            arrayResult.add(fileWriter.writeFile(value.actionId, filePath2,
                                                    "success -> " + value.title + " was rated with "
                                                            + value.grade + " by " + value.username));
                                        }
                                    }
                                }
                                user.noOfRatings++;
                            } else {
                                arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "error -> "
                                        + value.title + " is not seen"));
                            }
                        }
                    }
                }
            }
            //Exista 4 tipuri de query-uri users, actors, movies,shows
            if (value.actionType.equals("query")) {
                //query-ul pentru users stabileste primii n useri in functie de numarul de rating-uri
                //in funtie de sortType in ordine crescatoare sau descrescatoare
                if (value.objectType.equals("users")) {
                       List<String> names = new ArrayList<>();
                       if (value.sortType.equals("asc")) {
                              for (int i = 0; i < userList.size() - 1; i++) {
                                  for (int j = i + 1; j < userList.size(); j++) {
                                        if (userList.get(i).noOfRatings > userList.get(j).noOfRatings) {
                                                userList.get(i).swap(userList.get(i), userList.get(j));
                                        }
                                        if (userList.get(i).noOfRatings == userList.get(j).noOfRatings) {
                                                if (userList.get(i).username.compareTo(userList.get(j).username) > 0) {
                                                    userList.get(i).swap(userList.get(i), userList.get(j));
                                                }
                                        }
                                  }
                              }
                              int k = 0;
                           for (User user : userList) {
                               if (user.noOfRatings != 0 && k < value.number) {
                                   names.add(k, user.username);
                                   k++;
                               }
                           }
                       }
                       if (value.sortType.equals("desc")) {
                           for (int i = 0; i < userList.size() - 1; i++) {
                               for (int j = i + 1; j < userList.size(); j++) {
                                   if (userList.get(i).noOfRatings < userList.get(j).noOfRatings) {
                                       userList.get(i).swap(userList.get(i), userList.get(j));
                                   }
                                   if (userList.get(i).noOfRatings == userList.get(j).noOfRatings) {
                                       if (userList.get(i).username.compareTo(userList.get(j).username) > 0) {
                                           userList.get(i).swap(userList.get(i), userList.get(j));
                                       }
                                   }
                               }
                           }
                           int k = 0;
                           for (User user : userList) {
                               if (user.noOfRatings != 0 && k < value.number) {
                                   names.add(k, user.username);
                                   k++;
                               }
                           }
                       }
                    arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "Query result: " + names));
                }
                //query-ul actors poate crea 3 liste diferite
                if (value.objectType.equals("actors")) {
                         List<String> names = new ArrayList<>();
                         //o lista in funtie de niste cuvinte cheie care se gasesc in descrierea actorului
                         if (value.criteria.equals("filter_description")) {
                             for (Actors item : actorsList) {
                                 int ok = 1;
                                 for (int j = 0; j < value.filters.get(2).size(); j++) {
                                     if (!item.career_description.toLowerCase().contains(value.filters.get(2).get(j).toLowerCase())) {
                                         ok = 0;
                                         break;
                                     }
                                 }
                                 if (ok == 1) {
                                     names.add(item.name);
                                 }
                             }
                             if (value.sortType.equals("asc")) {
                                 Collections.sort(names);
                             }
                             if (value.sortType.equals("desc")) {
                                 names.sort(Collections.reverseOrder());
                             }
                             arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "Query result: " + names));
                         }
                         //o lista cu actorii care au luat anumite award-uri date ca parametrii
                         //actorii sunt afisati in funtie de numarul de award-uri , crescator sau descrescator
                         if (value.criteria.equals("awards")) {
                             List<Integer> num = new ArrayList<>();
                             for (Actors item : actorsList) {
                                  int ok = 1;
                                  List<ActorsAwards> s = new ArrayList<>();
                                 for (int j = 0; j < value.filters.get(3).size(); j++) {
                                     if (value.filters.get(3).get(j).equals("BEST_PERFORMANCE")) {
                                               s.add(ActorsAwards.BEST_PERFORMANCE);
                                     }
                                     if (value.filters.get(3).get(j).equals("BEST_DIRECTOR")) {
                                         s.add(ActorsAwards.BEST_DIRECTOR);
                                     }
                                     if (value.filters.get(3).get(j).equals("PEOPLE_CHOICE_AWARD")) {
                                         s.add(ActorsAwards.PEOPLE_CHOICE_AWARD);
                                     }
                                     if (value.filters.get(3).get(j).equals("BEST_SUPPORTING_ACTOR")) {
                                         s.add(ActorsAwards.BEST_SUPPORTING_ACTOR);
                                     }
                                     if (value.filters.get(3).get(j).equals("BEST_SCREENPLAY")) {
                                         s.add(ActorsAwards.BEST_SCREENPLAY);
                                     }
                                 }
                                 int n = 0;
                                 for (ActorsAwards actorsAwards : s) {
                                     if (!item.awards.containsKey(actorsAwards)) {
                                         ok = 0;
                                         break;
                                     } else {
                                         n += item.awards.get(actorsAwards);
                                     }
                                 }
                                  if (ok == 1) {
                                     names.add(item.name);
                                     num.add(n);
                                  }
                             }
                             if (value.sortType.equals("asc")) {
                                 for (int i = 0; i < num.size() - 1; i++) {
                                         for (int j = i + 1; j < num.size(); j++) {
                                                  if (num.get(i) > num.get(j)) {
                                                      Integer m = num.get(i);
                                                      num.set(i, num.get(j));
                                                      num.set(j, m);
                                                      String s = names.get(i);
                                                      names.set(i, names.get(j));
                                                      names.set(j, s);
                                                  }
                                         }
                                 }
                                 for (int i = 0; i < num.size() - 1; i++) {
                                         for (int j = i + 1; j < num.size(); j++) {
                                                  if (num.get(i).equals(num.get(j))) {
                                                          if (names.get(i).compareTo(names.get(j)) > 0) {
                                                              Integer m = num.get(i);
                                                              num.set(i, num.get(j));
                                                              num.set(j, m);
                                                              String s = names.get(i);
                                                              names.set(i, names.get(j));
                                                              names.set(j, s);
                                                          }
                                                  }
                                         }
                                 }
                             }
                             if (value.sortType.equals("desc")) {
                                 for (int i = 0; i < num.size() - 1; i++) {
                                     for (int j = i + 1; j < num.size(); j++) {
                                         if (num.get(i) < num.get(j)) {
                                             Integer m = num.get(i);
                                             num.set(i, num.get(j));
                                             num.set(j, m);
                                             String s = names.get(i);
                                             names.set(i, names.get(j));
                                             names.set(j, s);
                                         }
                                     }
                                 }
                                 for (int i = 0; i < num.size() - 1; i++) {
                                     for (int j = i + 1; j < num.size(); j++) {
                                         if (num.get(i).equals(num.get(j))) {
                                             if (names.get(i).compareTo(names.get(j)) > 0) {
                                                 Integer m = num.get(i);
                                                 num.set(i, num.get(j));
                                                 num.set(j, m);
                                                 String s = names.get(i);
                                                 names.set(i, names.get(j));
                                                 names.set(j, s);
                                             }
                                         }
                                     }
                                 }
                             }
                             arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "Query result: " + names));
                         }
                         //o lista cu actorii in functie de rating-urile din filmele si serialele in care au jucat
                         //se strabat listele cu filme si seriale , daca actorul joaca in acel show se aduna la
                         //o suma si la final se face media si se adauga lista
                         //in final lista este ordonata dupa cum se cere (crescator sau descrescator)
                         //si apoi se afisaza primii n actori din lista
                         if (value.criteria.equals("average")) {
                             List<Double> num = new ArrayList<>();
                             for (Actors item : actorsList) {
                                 double nota = 0.0;
                                 int numar = 0;
                                 for (Movies element : moviesList) {
                                     if (element.actors.contains(item.name)) {
                                         Double suma = 0.0;
                                         int d = 0;
                                         for (int k = 0; k < element.ratings.size(); k++) {
                                             if (element.ratings.get(k) != 0) {
                                                 suma += element.ratings.get(k);
                                                 d++;
                                             }
                                         }
                                         if (d != 0) {
                                             nota += suma / d;
                                             numar++;
                                         }
                                     }
                                 }
                                 for (Serials element : serialsList) {
                                     if (element.cast.contains(item.name)) {
                                         double suma = 0.0;
                                         int d = 0;
                                         for (int k = 0; k < element.ratings.size(); k++) {
                                             if (element.ratings.get(k) != 0) {
                                                 suma += element.ratings.get(k) / element.number.get(k);
                                                 d++;
                                             }
                                         }
                                         if (d != 0) {
                                             nota += suma / d;
                                             numar++;
                                         }
                                     }
                                 }
                                 if (numar != 0) {
                                     num.add(nota / numar);
                                     names.add(item.name);
                                 }
                             }
                             if (value.sortType.equals("asc")) {
                                 for (int i = 0; i < num.size() - 1; i++) {
                                     for (int j = i + 1; j < num.size(); j++) {
                                         if (num.get(i) > num.get(j)) {
                                             Double m = num.get(i);
                                             num.set(i, num.get(j));
                                             num.set(j, m);
                                             String s = names.get(i);
                                             names.set(i, names.get(j));
                                             names.set(j, s);
                                         }
                                     }
                                 }
                                 for (int i = 0; i < num.size() - 1; i++) {
                                     for (int j = i + 1; j < num.size(); j++) {
                                         if (num.get(i).equals(num.get(j))) {
                                             if (names.get(i).compareTo(names.get(j)) > 0) {
                                                 Double m = num.get(i);
                                                 num.set(i, num.get(j));
                                                 num.set(j, m);
                                                 String s = names.get(i);
                                                 names.set(i, names.get(j));
                                                 names.set(j, s);
                                             }
                                         }
                                     }
                                 }
                                 List<String> show = new ArrayList<>();
                                 if (value.number > names.size()) {
                                     arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "Query result: " + names));
                                 } else {
                                     for (int i = 0; i < value.number; i++) {
                                         show.add(names.get(i));
                                     }
                                     arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "Query result: " + show));
                                 }
                             }
                             if (value.sortType.equals("desc")) {
                                 for (int i = 0; i < num.size() - 1; i++) {
                                     for (int j = i + 1; j < num.size(); j++) {
                                         if (num.get(i) < num.get(j)) {
                                             Double m = num.get(i);
                                             num.set(i, num.get(j));
                                             num.set(j, m);
                                             String s = names.get(i);
                                             names.set(i, names.get(j));
                                             names.set(j, s);
                                         }
                                     }
                                 }
                                 for (int i = 0; i < num.size() - 1; i++) {
                                     for (int j = i + 1; j < num.size(); j++) {
                                         if (num.get(i).equals(num.get(j))) {
                                             if (names.get(i).compareTo(names.get(j)) > 0) {
                                                 Double m = num.get(i);
                                                 num.set(i, num.get(j));
                                                 num.set(j, m);
                                                 String s = names.get(i);
                                                 names.set(i, names.get(j));
                                                 names.set(j, s);
                                             }
                                         }
                                     }
                                 }
                                 List<String> show = new ArrayList<>();
                                 if (value.number > names.size()) {
                                     arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "Query result: " + names));
                                 } else {
                                     for (int i = 0; i < value.number; i++) {
                                         show.add(names.get(i));
                                     }
                                     arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "Query result: " + show));
                                 }
                             }
                         }
                }
                //query-ul movies trebuie sa creeze liste pentru
                //filmele cu cele mai mari rating-uri
                //filmele ce apar in cele mai multe sectiuni de favorite ale userilor
                //cele mai lungi filme
                //cele mai urmarite filme
                if (value.objectType.equals("movies")) {
                         List<String> names = new ArrayList<>();
                         List<Double> num = new ArrayList<>();
                         if (value.criteria.equals("ratings")) {
                                   int ok = 1;
                             for (Movies item : moviesList) {
                                 for (int j = 0; j < value.filters.get(1).size(); j++) {
                                     if (!item.genres.contains(value.filters.get(1).get(j))) {
                                         ok = 0;
                                         break;
                                     }
                                 }
                                 if (ok == 1 && value.filters.get(0).get(0).equals(String.valueOf(item.year))) {
                                     Double n = 0.0;
                                     for (int k = 0; k < item.ratings.size(); k++) {
                                         n += item.ratings.get(k);
                                     }
                                     if (item.ratings.size() != 0) {
                                         n = n / item.ratings.size();
                                         names.add(item.name);
                                         num.add(n);
                                     }
                                 }
                             }
                             arrayResult.add(fileWriter.writeFile(value.actionId, filePath2, "Query result: " + names));
                         }
                }
            }
        }

        fileWriter.closeJSON(arrayResult);
    }
}
