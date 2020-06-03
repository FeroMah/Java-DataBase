package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.util.ConsoleReader;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final static String AUTHORS_FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;
    private final ConsoleReader reader;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil, ConsoleReader reader) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
        this.reader = reader;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authorFileContent = this.fileUtil.getFileContent(AUTHORS_FILE_PATH);
        for (String line : authorFileContent) {
            String[] names = line.split("\\s+");

            Author author = new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);
        }
    }

    public List<String> getAuthorsWithFirstNameEndingSearch(){
      String endString = this.reader.read();

      List<String> authors = this.authorRepository.findAllByFirstNameEndingWith(endString)
              .stream()
              .map(author -> String.format("%s %s",author.getFirstName(),author.getLastName()))
              .collect(Collectors.toList());

      return  authors;
    };

    public List<String> getAuthorsByBooksCopies(){
        return this.authorRepository
                .getAuthorsByBooksCopies()
                .stream()
                .map(obj -> (String) obj)
                .collect(Collectors.toList());

    }
}
