package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Passenger;

import java.util.List;

@Repository
//TODO
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Passenger getByEmail(String email);

    @Query(value = "From passengers p order by p.tickets.size DESC , p.email ASC")
    List<Passenger> getAllOrderedByTicketsSizeDecsAndEmailAsc();


    @Query(value = "SELECT DISTINCT p.first_name , p.last_name , p.email, p.phone_number, COUNT(t.id)\n" +
            "FROM passengers p\n" +
            "         LEFT JOIN tickets t on p.id = t.passenger_id\n" +
            "GROUP BY p.first_name, p.last_name, p.email, p.phone_number\n" +
            "ORDER BY COUNT(t.id) DESC, p.email ASC;", nativeQuery = true)
    List<Object> getAllPassengersByNativeQuery();

}
