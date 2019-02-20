package lib.book;

import lib.account.UserRepository;
import lib.account.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookRentalService {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    public BookRentalService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public boolean tryRentBook(Long bookId, String username) {
         Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book: " + bookId + " not found"));

        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User: " + username + " not found"));

        if (!book.isAvailable()) {
            return false;
        }

        if(user.getPenalty() > 0) {
            return false;
        }

        rentBook(book, user);
        return true;
    }

    private void rentBook(Book book, User user) {
        book.setAvailable(false);
        book.setRentedAt(new Date());
        bookRepository.save(book);

        user.getRentedBooks()
                .add(book);
        userRepository.save(user);
    }

    public void returnBook(Long bookId, String username) {
        Book book = bookRepository
            .findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException("Book: " + bookId + " not found"));

        book.setAvailable(true);
        book.setRentedAt(null);
        bookRepository.save(book);

        User user = userRepository
            .findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User: " + username + " not found"));

        user.getRentedBooks()
                .remove(book);
        userRepository.save(user);
    }

    public List<Book> getBooksRentedByUser(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent())
            return user.get().getRentedBooks();
        else
            return Collections.emptyList();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public List<Book> getBooksContainingPhrase(String phrase) {
        return bookRepository.findByTitleOrAuthorContaining(phrase);
    }
}
