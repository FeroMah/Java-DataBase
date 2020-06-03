package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car getById(Long id);


    @Query (value = "from cars c order by c.pictures.size DESC , c.make Asc")
    List<Car> findAllCarsOrderByPicturesCountDecsAndMakeAsc ();
}
