package DAOs;

import Models.Category;
import java.util.List;

public interface CategoryDAO_Interface {
    List<Category> getAllCategories();
    Category getCategory(int categoryID);
    boolean searchForCategory(int categoryID);
    boolean searchForCategory(Category category);
    boolean addCategory(Category category);
}
