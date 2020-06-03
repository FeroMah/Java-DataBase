package softuni.json_processing_exercise.services;

import softuni.json_processing_exercise.dtos.CategoryExportDTO;
import softuni.json_processing_exercise.dtos.CategorySeedDTO;
import softuni.json_processing_exercise.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedCategories(CategorySeedDTO[] categorySeedDTOS);

    Set<Category> getRandomCategory();

    List<CategoryExportDTO> getCategoriesByProductsCount();
}
