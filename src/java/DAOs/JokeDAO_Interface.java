package DAOs;

import Models.Joke;

import java.util.List;

public interface JokeDAO_Interface {
    List<Joke> getAllJokes();
    List<Joke> getAllUserJokes(int userID);
    Joke getJoke(int jokeID);
    boolean addJoke(Joke joke);
    boolean searchForJoke(int jokeID);
    boolean updateJoke(Joke joke);
    boolean deleteJoke(int jokeID);
    int getJokeID(Joke joke);
}
