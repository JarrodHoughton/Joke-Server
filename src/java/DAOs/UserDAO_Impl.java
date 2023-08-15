package DAOs;

import Models.User;
import Utils.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.*;
import java.util.*;

public class UserDAO_Impl implements UserDAO_Interface {

    private Connection connection;
    private PreparedStatement prepStmt;
    private MySQLConnection msc;

    public UserDAO_Impl() {
        this.msc = new MySQLConnection();
    }

    @Override
    public User getUser(int userID) {
        User user = null;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT * FROM UserTable WHERE User_ID=?;");
            prepStmt.setInt(1, userID);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(UserDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return user;
    }

    @Override
    public boolean searchForUser(int userID) {
        boolean userFound = false;
        List<Integer> userIDs = getUserIDs();
        int i = 0;
        while (i < userIDs.size() && !userFound) {
            userFound = userIDs.get(i) == userID;
            i++;
        }
        return userFound;
    }

    public List<Integer> getUserIDs() {
        List<Integer> userIDs = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT User_ID FROM UserTable;");
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                userIDs.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(UserDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userIDs;
    }

    @Override
    public boolean addUser(User user) {
        boolean userAdded = false;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("INSERT INTO UserTable VALUES(?,?,?,?,?,?);");
            prepStmt.setInt(1, user.getUserID());
            prepStmt.setString(2, user.getName());
            prepStmt.setString(3, user.getSurname());
            prepStmt.setString(4, user.getType());
            prepStmt.setString(5, user.getEmail());
            prepStmt.setString(6, user.getPassword());
            System.out.println("Row(s) Affected: " + prepStmt.executeUpdate() + ". User added.");
            userAdded = true;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(UserDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userAdded;
    }

    @Override
    public User login(User user) {
        User loggedInUser = null;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT * FROM UserTable WHERE email=? and password=?");
            prepStmt.setString(1, user.getEmail());
            prepStmt.setString(2, user.getPassword());
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                loggedInUser = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnections();
        }
        return loggedInUser;
    }

    public void closeConnections() {
        try {
            msc.close();

            if (prepStmt != null) {
                prepStmt.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
