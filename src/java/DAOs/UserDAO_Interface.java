package DAOs;

import Models.User;


public interface UserDAO_Interface {
    User getUser(int userID);
    boolean searchForUser(int userID);
    boolean addUser(User user);
    User login(User user);
}
