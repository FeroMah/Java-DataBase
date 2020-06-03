package softuni.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import softuni.homework.entities.Supplier;

import java.util.List;


public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findAllByName(String name);

    @Query("FROM Supplier WHERE isImporter=false")
    List<Supplier> findAllByIsImporterFalse();
}
