package bookshopsystemapp.repository;

import bookshopsystemapp.dataTransferObjects.ReducedBook;
import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByCopiesIsLessThan(Integer copies);

    List<Book> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal higher);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate beforeDate, LocalDate afterDate);

    List<Book> findAllByTitleContainingIgnoreCase(String str);

    @Query(value = "select b from Book as b inner join b.author as a where a.lastName LIKE ?1")
    List<Book> findByAuthorIn(String str);

    @Query(value = "select b from Book as b where length(b.title) >?1")
    List<Book> findBooksWithTitleLongerThenAGivenValue(Integer num);


    ReducedBook getBookByTitle(String title);

    @Modifying
    @Query("UPDATE Book as b set b.copies = b.copies + ?1 where b.releaseDate > ?2")
    Integer increaseBookCopiesAfterGivenReleaseDate(Integer num, LocalDate date);



//    @Modifying
//    @Query("DELETE FROM Book AS b WHERE b.copies < :copies")
//    Integer removeBooksWithCopiesLessThan(@Param("copies") int minCopies);
    @Modifying
    Integer deleteAllByCopiesIsLessThan(Integer copies);

}
