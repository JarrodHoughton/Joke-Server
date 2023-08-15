package DAOs;

import java.util.List;

public interface JokeCategoryDAO_Interface {
    List<Integer> getJokeIdsInCategory(int categoryID);
    public List<Integer> getJokeIdsInCategories(List<Integer> categoryIDs);
    List<Integer> getCategoryIdsForJoke(int jokeID);
    boolean addJokeCategory(int jokeID, int categoryID);
    boolean deleteJokeCategory(int jokeID, int categoryID);
}
