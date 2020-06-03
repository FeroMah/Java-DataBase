package softuni.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.homework.entities.Car;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByMakeAndModelAndTravelledDistance(String make, String model, Long travelledDistance);

    Car findById(long id);

    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}