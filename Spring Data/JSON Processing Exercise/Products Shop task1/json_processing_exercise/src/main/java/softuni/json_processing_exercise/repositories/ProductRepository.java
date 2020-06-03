package softuni.json_processing_exercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import softuni.json_processing_exercise.entities.Product;
import softuni.json_processing_exercise.entities.User;


import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> getAllByPriceBetweenAndBuyerIsNull(BigDecimal low , BigDecimal high);


}
