package lib.dataprovider;

import lib.data.AuthorRepository;
import lib.data.BookRepository;
import lib.domain.Author;
import lib.domain.Book;
import lib.domain.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitialData {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public InitialData(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void addBooksToDB() {
        log.info("Persisting books");
        bookRepository.save(
                new Book("Name of the Rose",
                        authorRepository.findByFirstNameAndSecondName("Umberto", "Eco"),
                        1980, "Historic"));

        bookRepository.save(new Book("Krew elfów",
                            authorRepository.findByFirstNameAndSecondName("Andrzej", "Sapkowski"),
                            1994, "Fantasy"));

        bookRepository.save(new Book("Czas pogardy",
                    authorRepository.findByFirstNameAndSecondName("Andrzej", "Sapkowski"),
                           1995, "Fantasy"));
    }
}
