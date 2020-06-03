package softuni.json_processing_exercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.json_processing_exercise.entities.User;


import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findAllByFirstNameAndLastNameAndAge(String firstName, String lastName, long age);

    @Query(value = "from User u Inner Join Product p on u.id = p.seller.id " +
            "where p.buyer Is not null " +
            "GROUP BY u.id " +
            "ORDER BY u.lastName asc , u.firstName asc ")
    List<User> getUsersWithAtLeastOneProductSold();

//    made by vlado
//    List<User> findDistinctBySold_BuyerIsNotNullOrderByLastNameAscFirstNameAsc();

}
