package celcyum.dreamshops.service.category;

import celcyum.dreamshops.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long Id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category, Long Id);
    void deleteCategory(Long id);
}
