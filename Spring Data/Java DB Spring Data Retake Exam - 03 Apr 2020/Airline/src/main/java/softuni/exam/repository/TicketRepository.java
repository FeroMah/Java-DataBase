package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Ticket;

//ToDo
@Repository
public interface TicketRepository   extends JpaRepository<Ticket,Long> {

    Ticket getBySerialNumber(String serialNumber);
}
