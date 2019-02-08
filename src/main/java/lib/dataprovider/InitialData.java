package lib.dataprovider;

import lib.data.BookRepository;
import lib.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitialData {

    private final BookRepository bookRepository;

    @Autowired
    public InitialData(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void addBooksToDB() {
        log.info("Persisting books");
        bookRepository.save(
                new Book("Name of the Rose",
                        "Umberto Eco",
                        1980, "Historic"));

        bookRepository.save(new Book("Krew elfów",
                            "Andrzej Sapkowski",
                            1994, "Fantasy"));

        bookRepository.save(new Book("Czas pogardy",
                           "Andrzej Sapkowski",
                           1995, "Fantasy"));
    }
}
