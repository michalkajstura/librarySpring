package lib.dataprovider;

import lib.book.Book;
import lib.book.BookRepository;
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
//        bookRepository.save(
//                new Book(0L, "Name of the Rose",
//                        "Umberto Eco",
//                        1980,
//                        true,
//                        "Historic",null));
//
//         bookRepository.save(
//                new Book(0L, "Krew elfów",
//                        "Andrzej Sapkowski",
//                        1994,
//                        true,
//                        "Fantasy",null));
//
//
//        bookRepository.save(new Book(0L, "Czas pogardy",
//                           "Andrzej Sapkowski",
//                           1995,
//                true, "Fantasy", null));
    }
}
