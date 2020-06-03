package softuni.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.homework.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Sale findById(long id);
}
