package softuni.json_processing_exercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.json_processing_exercise.dtos.CategoryExportDTO;
import softuni.json_processing_exercise.entities.Category;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findCategoryByName(String name);

//    @Query (value = "SELECT c.name as category, COUNT(c.name) as productsCount, AVG(p.price) as averagePrice, SUM(p.price) as totalRevenue\n" +
//            "FROM products_categories as p_c\n" +
//            "         inner join categories c on p_c.category_id = c.id\n" +
//            "         inner join products p on p_c.product_id = p.id\n" +
//            "GROUP BY c.name\n" +
//            "ORDER BY COUNT(c.name) DESC",nativeQuery = true)
//    Set<Category> getCategoriesByProductsCount();
}


