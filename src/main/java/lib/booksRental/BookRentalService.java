package lib.booksRental;

import lib.data.BookNotFoundException;
import lib.booksRental.Book;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface BookRentalService {

    void rentBook(Long bookId, String username) throws BookNotFoundException, UsernameNotFoundException;

    void returnBook(Long bookId, String username) throws BookNotFoundException, UsernameNotFoundException;

    List<Book> getBooksRentedByUser(String username);

    List<Book> getAllBooks();

    List<Book> getBooksContainingPhrase(String phrase);
}
