package lib.service;

import lib.data.BookNotFoundException;
import lib.domain.Book;
import lib.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface BookRentalService {

    void rentBook(Long bookId, String username) throws BookNotFoundException, UsernameNotFoundException;

    void returnBook(Long bookId, String username) throws BookNotFoundException, UsernameNotFoundException;

    List<Book> getBooksRentedByUser(String username);

    List<Book> getAllBooks();

}
