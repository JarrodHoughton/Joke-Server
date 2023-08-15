package Service;

import Models.*;

import java.util.List;

public interface Service_Interface {
    //Joke Class methods
    List<Joke> getUserJokes(int userID);
    Joke getRandomJoke(List<Integer> categoryIDs);
    String addJoke(Joke joke);
    String updateJoke(Joke joke);
    String deleteJoke(Integer jokeId);
    Joke getJoke(int jokeID);

    //User Class methods
    User getUser(int userID);
    boolean searchForUser(int userID);
    String addUser(User user);
    User login(User user);

    //Category Class methods
    List<Category> getAllCategories();
    boolean searchForCategory(Category category);
    String addCategory(Category category);
}
