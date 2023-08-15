package Models;

import java.util.List;

public class Joke {
    private int jokeID, userID;
    private String joke;
    private List<Integer> categoryIDs;

    public Joke() {
    }
    

    public Joke(int jokeID, String joke, int userID, List<Integer> categoryIDs) {
        this.jokeID = jokeID;
        this.userID = userID;
        this.joke = joke;
        this.categoryIDs = categoryIDs;
    }

    public Joke(String joke, int userID, List<Integer> categoryIDs) {
        this.jokeID = 0;
        this.userID = userID;
        this.joke = joke;
        this.categoryIDs = categoryIDs;
    }

    public int getJokeID() {
        return jokeID;
    }

    public void setJokeID(int jokeID) {
        this.jokeID = jokeID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public List<Integer> getCategoryIDs() {
        return categoryIDs;
    }

    public void setCategoryIDs(List<Integer> categoryIDs) {
        this.categoryIDs = categoryIDs;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "jokeID=" + jokeID +
                ", userID=" + userID +
                ", joke='" + joke + '\'' +
                ", categoryIDs=" + categoryIDs +
                '}';
    }
}
