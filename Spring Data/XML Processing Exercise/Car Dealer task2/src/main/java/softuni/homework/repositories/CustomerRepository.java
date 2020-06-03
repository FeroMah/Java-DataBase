package softuni.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.homework.entities.Customer;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository <Customer,Long> {

    List<Customer> findAllByNameAndDateOfBirth(String name,LocalDateTime dateOfBirth);

    Customer findById (long id);

    @Query("FROM Customer ORDER BY dateOfBirth, isYoungDriver DESC")
    List<Customer> findAllDriverOrderByDateOfBirthAndIsYoungDriverDesc();
}
