package softuni.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.homework.entities.Part;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Part getById(Long id);

    List<Part> findAllByNameAndPrice(String name, BigDecimal price);
}
