package bookshopsystemapp.service;

import bookshopsystemapp.dataTransferObjects.ReducedBook;
import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.ConsoleReader;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final ConsoleReader reader;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil, ConsoleReader reader) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.reader = reader;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public Integer deleteAllByCopiesIsLessThan() {
        int copies = Integer.parseInt(this.reader.read());

        Integer deletedCount = this.bookRepository.deleteAllByCopiesIsLessThan(copies);
        return deletedCount;
    }

    @Override
    public Integer increaseBookCopiesAfterGivenReleaseDate() {
        String[] date = reader.read().split("\\s+");
        int bookCopies = Integer.parseInt(reader.read());

        String day = date[0];
        String year = date[2];

        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM")
                .withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(date[1]);
        String month =String.valueOf(accessor.get(ChronoField.MONTH_OF_YEAR)) ;
        if (month.length()==1){
            month= "0"+month;
        }
        final LocalDate afterDate = LocalDate.parse(String.format("%s-%s-%s", year,month,day));

        Integer number = this.bookRepository.increaseBookCopiesAfterGivenReleaseDate(bookCopies, afterDate);


        return number*bookCopies;
    }

    public String getReducedBook(){
        String inputTitle = reader.read().trim();

        ReducedBook reducedBook = this.bookRepository.getBookByTitle(inputTitle);


        return reducedBook.toString();
    };

    public int findCountBooksWithTitleLongerThenAGivenValue(){
      int length =Integer.parseInt(this.reader.read());
        int bookCount = this.bookRepository.findBooksWithTitleLongerThenAGivenValue(length).size();

        return bookCount;
    };

    public List<String> getAllBooksTitleByAgeRestriction() {
        String inputAgeRestr = this.reader.read().toUpperCase();
        AgeRestriction ageRestriction = AgeRestriction.valueOf(inputAgeRestr);
        List<String> bookTitleByAgeRestr = this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        return bookTitleByAgeRestr;
    }

    public List<String> getAllGoldenBooksByCopiesLessThen5000() {
        final int copies = 5000;

        List<String> goldenBooks = this.bookRepository.findAllByCopiesIsLessThan(copies)
                .stream()
                .filter(book -> book.getEditionType().toString().equals("GOLD"))
                .map(Book::getTitle)
                .collect(Collectors.toList());

        return goldenBooks;
    }

    ;

    public List<String> getAllBookWithPriceLowerThan5AndHigherThan40() {
        final BigDecimal lower = new BigDecimal(5);
        final BigDecimal higher = new BigDecimal(40);

        List<String> books = this.bookRepository.findAllByPriceIsLessThanOrPriceGreaterThan(lower, higher)
                .stream()
                .map(book -> String.format("%s - $%.2f", book.getTitle(), book.getPrice()))
                .collect(Collectors.toList());
        return books;
    }

    ;

    public List<String> getAllBooksNotInTheGivenReleaseData() {
        String year = this.reader.read();
        final LocalDate beforeDate = LocalDate.parse(String.format("%s-01-01", year));
        final LocalDate afterDate = LocalDate.parse(String.format("%s-12-31", year));

        List<Book> books = this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(beforeDate,afterDate);

        return books.stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

    };
    @Override
    public List<String> getAllBooksBeforeGivenDate(){
        String inputDate = this.reader.read();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(inputDate, formatter);

        List<String> books = this.bookRepository.findAllByReleaseDateBefore(localDate)
                .stream()
                .map(book -> String.format(
                        "%s %s %.2f"
                        ,book.getTitle()
                        ,book.getEditionType().toString()
                        ,book.getPrice().doubleValue()))
                .collect(Collectors.toList());
        return books;
    };

    @Override
    public List<String> getAllBooksWithTitleContaingIgnoreCase(){
      String inputText = this.reader.read();

      List<String> books = this.bookRepository.findAllByTitleContainingIgnoreCase(inputText)
              .stream()
              .map(Book::getTitle)
              .collect(Collectors.toList());
        return books;
    };


    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    public  List<String> getallBooksbyAuthorLastName(){
        String startWith = this.reader.read()+"%";

        List<String> books = this.bookRepository.findByAuthorIn(startWith)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        return books;
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));

        return books.stream().map(b -> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor().getLastName())).collect(Collectors.toSet());
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }
}
