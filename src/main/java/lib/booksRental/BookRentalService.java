package lib.booksRental;

import lib.data.BookNotFoundException;
import lib.booksRental.Book;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface BookRentalService {

    void rentBook(Long bookId, String username);

    void returnBook(Long bookId, String username);

    List<Book> getBooksRentedByUser(String username);

    List<Book> getAllBooks();

    List<Book> getBooksContainingPhrase(String phrase);
}
