package bookshopsystemapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    List<String> getAllBooksTitleByAgeRestriction() ;

    List<String> getAllGoldenBooksByCopiesLessThen5000();

    List<String> getAllBookWithPriceLowerThan5AndHigherThan40();

    List<String> getAllBooksNotInTheGivenReleaseData();

    List<String> getAllBooksBeforeGivenDate();

    List<String> getAllBooksWithTitleContaingIgnoreCase();

    List<String> getallBooksbyAuthorLastName();

    int findCountBooksWithTitleLongerThenAGivenValue();

    String getReducedBook();

    Integer increaseBookCopiesAfterGivenReleaseDate();

    Integer deleteAllByCopiesIsLessThan();

}
