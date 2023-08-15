package DAOs;

import Utils.DBManager;
import Utils.MySQLConnection;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JokeCategoryDAO_Impl implements JokeCategoryDAO_Interface{
    private Connection connection;
    private PreparedStatement prepStmt;

    public JokeCategoryDAO_Impl() {}

    @Override
    public List<Integer> getJokeIdsInCategory(int categoryID) {
        List<Integer> jokeIds = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT Joke_ID FROM JokeCategory WHERE Category_ID=?;");
            prepStmt.setInt(1, categoryID);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                jokeIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jokeIds;
    }

    public List<Integer> getJokeIdsInCategories(List<Integer> categoryIDs) {
        List<Integer> jokeIds = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            String conditionStr = generateSqlStatementForMultipleCategories(categoryIDs);
            System.out.println(conditionStr);
            prepStmt = connection.prepareStatement(conditionStr);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                jokeIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jokeIds;
    }

    public String generateSqlStatementForMultipleCategories(List<Integer> categoryIDs) {
        String sql = "SELECT DISTINCT JT.Joke_ID FROM joketable AS JT INNER JOIN " +
                "jokecategory AS JC ON JT.Joke_ID=JC.Joke_ID WHERE Category_ID=" + categoryIDs.get(0);
        int pos = 1;
        while (pos<categoryIDs.size()) {
            sql = "SELECT DISTINCT JT.Joke_ID FROM (" +
                    sql
                    + ") AS JT INNER JOIN " +
                    "jokecategory AS JC ON JT.Joke_ID=JC.Joke_ID WHERE Category_ID=" + categoryIDs.get(pos);
            pos++;
        }
        return sql;
    }

    @Override
    public List<Integer> getCategoryIdsForJoke(int jokeID) {
        List<Integer> categoryIds = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT Category_ID FROM JokeCategory WHERE Joke_ID=?;");
            prepStmt.setInt(1, jokeID);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                categoryIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return categoryIds;
    }

    @Override
    public boolean addJokeCategory(int jokeID, int categoryID) {
        boolean jokeCategoryAdded = false;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("INSERT INTO JokeCategory(Joke_ID, Category_ID) VALUES(?,?);");
            prepStmt.setInt(1, jokeID);
            prepStmt.setInt(2, categoryID);
            System.out.println("Row(s) Affected: " + prepStmt.executeUpdate() + ". JokeCategory Added.");
            jokeCategoryAdded = true;
        } catch (SQLException e) {
            Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jokeCategoryAdded;
    }

    @Override
    public boolean deleteJokeCategory(int jokeID, int categoryID) {
        boolean jokeCategoryDeleted = false;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("DELETE FROM JokeCategory WHERE Joke_ID=? AND Category_ID=?;");
            prepStmt.setInt(1, jokeID);
            prepStmt.setInt(2, categoryID);
            System.out.println("Row(s) Affected: " + prepStmt.executeUpdate() + ". JokeCategory Deleted.");
            jokeCategoryDeleted = true;
        } catch (SQLException e) {
            Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(JokeCategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return jokeCategoryDeleted;
    }
}
