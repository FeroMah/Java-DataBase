package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Town;

//ToDo
@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    Town getByName (String name);

}
