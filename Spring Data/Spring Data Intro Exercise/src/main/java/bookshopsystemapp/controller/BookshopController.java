package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... strings) throws Exception {
        //TODO !!! after seeding data in DB dont forget to comment the seeds (next 3 rows)
//        this.authorService.seedAuthors();
//        this.categoryService.seedCategories();
//        this.bookService.seedBooks();


        //1
//        this.getAllBooksTitleByAgeRestriction();
        //2
//        this.getAllGoldenBooksTitleLessThen5000Copies();
        //3
//        this.getAllBookWithPriceLowerThan5AndHigherThan40();
        //4
//        this.getAllBooksNotInTheGivenRealeseData();
        //5
//        this.getAllBooksBeforeGivenDate();
        //6
//        this.authorsFirstNameEndingSearch();
        //7
//        this.getAllBooksWithTitleContaingIgnoreCase();
        //8
//        this.getallBooksbyAuthorLastName();
        //9
//        this.findBooksWithTitleLongerThenAGivenValue();
        //10
//        this.getAuthorsByBooksCopies();
        //11
        this.getReducedBook();
        //12
        //this.increaseBookCopiesAfterGivenReleaseDate();
        //13
//        this.deleteAllByCopiesIsLessThan();

    }

    private void deleteAllByCopiesIsLessThan() {
        System.out.println(this.bookService.deleteAllByCopiesIsLessThan());
    }

    private void increaseBookCopiesAfterGivenReleaseDate() {
        System.out.println(this.bookService.increaseBookCopiesAfterGivenReleaseDate());
    }

    private void getReducedBook() {
        System.out.println("Enter book title... ");
        System.out.println(this.bookService.getReducedBook());
    }

    private void getAuthorsByBooksCopies() {
        this.authorService.getAuthorsByBooksCopies().forEach(System.out::println);
    }

    private void getAllBooksTitleByAgeRestriction() {
        System.out.println("Enter age restriction [ MINOR, TEEN, ADULT  ]: ");
        this.bookService.getAllBooksTitleByAgeRestriction().forEach(System.out::println);
    }

    private void getAllGoldenBooksTitleLessThen5000Copies() {

        this.bookService.getAllGoldenBooksByCopiesLessThen5000().forEach(System.out::println);

    }

    private void getAllBookWithPriceLowerThan5AndHigherThan40() {
        this.bookService.getAllBookWithPriceLowerThan5AndHigherThan40().forEach(System.out::println);
    }

    private void getAllBooksNotInTheGivenRealeseData() {
        this.bookService.getAllBooksNotInTheGivenReleaseData().forEach(System.out::println);
    }

    private void getAllBooksBeforeGivenDate() {
        System.out.println("Enter released before date [12-04-1992]: ");
        this.bookService.getAllBooksBeforeGivenDate().forEach(System.out::println);
    }


    private void authorsFirstNameEndingSearch() {
        System.out.println("First name ends with... ");
        this.authorService.getAuthorsWithFirstNameEndingSearch().forEach(System.out::println);
    }

    private void getAllBooksWithTitleContaingIgnoreCase() {
        System.out.println("The titles of books, which contain... ");
        this.bookService.getAllBooksWithTitleContaingIgnoreCase().forEach(System.out::println);
    }

    private void getallBooksbyAuthorLastName() {
        System.out.println("The titles of books, which are written by authors, whose last name starts with...");
        this.bookService.getallBooksbyAuthorLastName().forEach(System.out::println);

    }

    private void findBooksWithTitleLongerThenAGivenValue() {
        System.out.println("The number of books, whose title is longer than ...");
        System.out.println(this.bookService.findCountBooksWithTitleLongerThenAGivenValue());
    }

}
