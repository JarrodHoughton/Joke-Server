package DAOs;

import Models.Category;
import Utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDAO_Impl implements CategoryDAO_Interface {

    private Connection connection;
    private PreparedStatement prepStmt;

    public CategoryDAO_Impl() {}

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        List<Integer> categoryIDs = getCategoryIDs();
        for (Integer ID : categoryIDs) {
            categories.add(getCategory(ID));
        }
        return categories;
    }

    @Override
    public Category getCategory(int categoryID) {
        Category category = null;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT * FROM CategoryTable WHERE Category_ID=?;");
            prepStmt.setInt(1, categoryID);
            ResultSet rs = prepStmt.executeQuery();
            if (rs.next()) {
                category = new Category(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return category;
    }

    @Override
    public boolean searchForCategory(int categoryID) {
        boolean categoryFound = false;
        List<Integer> categoryIDs = getCategoryIDs();
        int i = 0;
        while (i < categoryIDs.size() && !categoryFound) {
            categoryFound = categoryIDs.get(i) == categoryID;
            i++;
        }
        return categoryFound;
    }

    @Override
    public boolean searchForCategory(Category category) {
        boolean categoryFound = false;
        List<Integer> categoryIDs = getCategoryIDs();
        int i = 0;
        while (i < categoryIDs.size() && !categoryFound) {
            categoryFound = getCategory(categoryIDs.get(i)).getName().equals(category.getName());
            i++;
        }
        return categoryFound;
    }

    @Override
    public boolean addCategory(Category category) {
        boolean categoryAdded = false;
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("INSERT INTO CategoryTable(Name) VALUES(?);");
            prepStmt.setString(1, category.getName());
            System.out.println("Row(s) Affected: " + prepStmt.executeUpdate() + ". Category added.");
            categoryAdded = true;
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return categoryAdded;
    }

    public List<Integer> getCategoryIDs() {
        List<Integer> categoryIDs = new ArrayList<>();
        try {
            connection = DBManager.getConnection();
            prepStmt = connection.prepareStatement("SELECT Category_ID FROM CategoryTable;");
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                categoryIDs.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            Logger.getLogger(CategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }

                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDAO_Impl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return categoryIDs;
    }
}
