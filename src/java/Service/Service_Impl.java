package Service;

import DAOs.*;
import Models.*;

import java.util.ArrayList;
import java.util.List;

public class Service_Impl implements Service_Interface{
    private final JokeDAO_Interface jokeDAO;
    private final UserDAO_Interface userDAO;
    private final CategoryDAO_Interface categoryDAO;
    private final JokeCategoryDAO_Interface jokeCategoryDAO;

    public Service_Impl() {
        this.jokeDAO = new JokeDAO_Impl();
        this.userDAO = new UserDAO_Impl();
        this.categoryDAO = new CategoryDAO_Impl();
        this.jokeCategoryDAO = new JokeCategoryDAO_Impl();
    }
    
    @Override
    public List<Joke> getUserJokes(int userID){return jokeDAO.getAllUserJokes(userID);}
    
    @Override
    public Joke getRandomJoke(List<Integer> categoryIDs){
        Joke randomJoke = null;
        List<Integer> jokeIDs = jokeCategoryDAO.getJokeIdsInCategories(categoryIDs);
        List<Joke> jokesInCategory = new ArrayList<>();
        if (!jokeIDs.isEmpty()) {
            for (Integer id:jokeIDs) {
                jokesInCategory.add(jokeDAO.getJoke(id));
            }
            randomJoke = jokesInCategory.get((int)(Math.random()*jokesInCategory.size()));
        }
        return randomJoke;
    }
    
    @Override
    public synchronized String addJoke(Joke joke){
        notifyAll();
        String message = "Failed to add joke to the server.";
        if (jokeDAO.addJoke(joke)) {
            message = "Your joke has been added to the server.";
        }
        return message;
    }
    
    @Override
    public String updateJoke(Joke joke){
        if (joke==null) {
            return "Joke was not selected.";
        }
        
        String message = "Failed to update joke.";
        if (jokeDAO.updateJoke(joke)) {
            message = "Your joke has been updated.";
        }
        return message;
    }
    
    @Override
    public String deleteJoke(Integer jokeId){
        String message = "Failed to delete joke.";
        if (jokeDAO.deleteJoke(jokeId)) {
            message = "Your joke has been deleted.";
        }
        return message;
    }
    
    @Override
    public Joke getJoke(int jokeID) {
        return jokeDAO.getJoke(jokeID);
    }


    @Override
    public User getUser(int userID){
        return userDAO.getUser(userID);
    }
    
    @Override
    public boolean searchForUser(int userID){
        return userDAO.searchForUser(userID);
    }
    
    @Override
    public synchronized String addUser(User user){
        notifyAll();
        String message;
        if (userDAO.addUser(user)) {
            message = "You have successfully registered.";
        } else {
            message = "Failed to register.";
        }
        return message;
    }
    
    @Override
    public User login(User user) {
        return userDAO.login(user);
    }
    
    @Override
    public List<Category> getAllCategories(){
     return categoryDAO.getAllCategories();
    }
    
    @Override
    public boolean searchForCategory(Category category){
        return categoryDAO.searchForCategory(category);
    }
    
    @Override
    public synchronized String addCategory(Category category){
        notifyAll();
        if (searchForCategory(category)) {
            return "Category added: false";
        }
        String message = "Failed to add the category created.";
        if (categoryDAO.addCategory(category)) {
            message = "Category has been created and added to the server.";
        }
        return message;
    }
}
