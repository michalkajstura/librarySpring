package lib.service;

import lib.data.BookNotFoundException;
import lib.data.BookRepository;
import lib.data.UserRepository;
import lib.domain.Book;
import lib.domain.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookRenalServiceImp implements BookRentalService {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    public BookRenalServiceImp(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public void rentBook(Long bookId, String username)
            throws BookNotFoundException, UsernameNotFoundException {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId + "Not found"));

        book.setAvailable(false);
        book.setRentedAt(new Date());
        bookRepository.save(book);

        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        user.getRentedBooks()
                .add(book);
        userRepository.save(user);
    }

    public void returnBook(Long bookId, String username)
            throws BookNotFoundException, UsernameNotFoundException {
        Book book = bookRepository
            .findById(bookId)
            .orElseThrow(() -> new BookNotFoundException(bookId + "Not found"));

        book.setAvailable(true);
        book.setRentedAt(null);
        bookRepository.save(book);

        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));

        user.getRentedBooks()
                .remove(book);
        userRepository.save(user);
    }

    @Override
    public List<Book> getBooksRentedByUser(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent())
            return user.get().getRentedBooks();
        else
            return Collections.emptyList();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
