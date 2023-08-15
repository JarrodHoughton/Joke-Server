package DAOs;

import Models.Joke;
import Utils.DBManager;
import Utils.MySQLConnection;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JokeDAO_Impl implements JokeDAO_Interface{
    private Connection connection;
    private JokeCategoryDAO_Interface JCIntersectDAO; //JokeCategory Intersect DAO
    private PreparedStatement prepStmt;
    private MySQLConnection msc;

    public JokeDAO_Impl() {
        JCIntersectDAO = new JokeCategoryDAO_Impl();
        msc = new MySQLConnection();
   }

    @Override
    public List<Joke> getAllJokes() {
        List<Joke> jokes = new ArrayList<>();
        List<Integer> jokeIDs = getAllJokeIds();
        for (Integer id:jokeIDs) {
            jokes.add(getJoke(id));
        }
        return jokes;
    }

    @Override
    public List<Joke> getAllUserJokes(int userID) {
        List<Joke> userJokes = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT Joke_ID FROM JokeTable WHERE User_ID=?;");
            prepStmt.setInt(1, userID);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                userJokes.add(getJoke(rs.getInt(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                msc.close();
                
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userJokes;
    }

    public List<Integer> getAllJokeIds() {
        List<Integer> jokeIds = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT Joke_ID FROM JokeTable;");
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                jokeIds.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           try {
               msc.close();
               
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jokeIds;
    }

    @Override
    public Joke getJoke(int jokeID) {
        Joke joke = null;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT * FROM JokeTable WHERE Joke_ID=?;");
            prepStmt.setInt(1, jokeID);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                joke = new Joke(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        JCIntersectDAO.getCategoryIdsForJoke(jokeID)
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                msc.close();
                
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return joke;
    }


    @Override
    public boolean addJoke(Joke joke) {
        boolean jokeAdded = false;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("INSERT INTO JokeTable(Joke, User_ID) VALUES(?,?);");
            prepStmt.setString(1, joke.getJoke());
            prepStmt.setInt(2, joke.getUserID());
            System.out.println("Row(s) Affected: " + prepStmt.executeUpdate() + ". Joke added to database.");
            for (Integer categoryID:joke.getCategoryIDs()) {
                JCIntersectDAO.addJokeCategory(getJokeID(joke), categoryID);
            }
            jokeAdded = true;
        } catch (SQLException e) {
            Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                msc.close();
                
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jokeAdded;
    }

    @Override
    public boolean searchForJoke(int jokeID) {
        boolean jokeFound = false;
        List<Integer> jokeIDs = getAllJokeIds();
        int i = 0;
        while (i<jokeIDs.size()&&!jokeFound) {
            jokeFound = jokeIDs.get(i)==jokeID;
            i++;
        }
        return jokeFound;
    }

    public int getJokeID(Joke joke) {
        int jokeID = 0;
        List<Integer> jokeIDs = getAllJokeIds();
        int i = 0;
        boolean jokeFound = false;
        while (i<jokeIDs.size()&&!jokeFound) {
            Joke currentJoke = getJoke(jokeIDs.get(i));
            jokeFound = currentJoke.getJoke().equals(joke.getJoke());
            jokeID = currentJoke.getJokeID();
            i++;
        }
        return jokeID;
    }

    @Override
    public boolean updateJoke(Joke joke) {
        boolean jokeUpdated = false;
        try {
            jokeUpdated = updateJokeCategories(joke.getJokeID(), joke.getCategoryIDs());
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("UPDATE JokeTable SET Joke=? WHERE Joke_ID=?;");
            prepStmt.setString(1, joke.getJoke());
            prepStmt.setInt(2, joke.getJokeID());
            System.out.println("Row(s):" + prepStmt.executeUpdate() + " Joke updated.");
        } catch (SQLException e) {
            jokeUpdated = false;
            Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                msc.close();
                
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jokeUpdated;
    }

    public boolean updateJokeCategories(int jokeID, List<Integer> updatedCategoryIds) {
        boolean jokeCategoriesUpdated = true;
        List<Integer> currentCategoryIds = JCIntersectDAO.getCategoryIdsForJoke(jokeID);
        //remove categories that the joke is no longer associated with
        for (Integer categoryID : currentCategoryIds) {
            if (!updatedCategoryIds.contains(categoryID)) {
                if (!JCIntersectDAO.deleteJokeCategory(jokeID, categoryID)) {
                    jokeCategoriesUpdated = false;
                }
            }
        }

        //add any associations to categories that did not exist before
        for (Integer categoryID : updatedCategoryIds) {
            if (!currentCategoryIds.contains(categoryID)) {
                if (!JCIntersectDAO.addJokeCategory(jokeID, categoryID)) {
                    jokeCategoriesUpdated = false;
                }
            }
        }
        return jokeCategoriesUpdated;
    }

    @Override
    public boolean deleteJoke(int jokeID) {
        boolean jokeDeleted;
        try {
            jokeDeleted = deleteJokeCategories(jokeID, JCIntersectDAO.getCategoryIdsForJoke(jokeID));
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("DELETE FROM JokeTable WHERE Joke_ID=?;");
            prepStmt.setInt(1, jokeID);
            System.out.println("Row(s) Affected: " +  prepStmt.executeUpdate() + ". Joke was deleted.");
        } catch (SQLException e) {
            jokeDeleted = false;
            Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                msc.close();
                
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jokeDeleted;
    }

    public boolean deleteJokeCategories(int jokeID, List<Integer> categoryIDs) {
        boolean jokeCategoriesDeleted = true;
        for (Integer categoryID:categoryIDs) {
            if (!JCIntersectDAO.deleteJokeCategory(jokeID, categoryID)) {
                jokeCategoriesDeleted = false;
            }
        }
        return jokeCategoriesDeleted;
    }
}
