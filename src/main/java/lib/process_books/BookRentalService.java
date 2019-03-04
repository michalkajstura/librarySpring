package lib.process_books;

import lib.account.UserRepository;
import lib.account.User;
import lib.book.Book;
import lib.book.BookRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookRentalService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BookOrderRepository orderRepository;

    public BookRentalService(BookRepository bookRepository,
                             UserRepository userRepository,
                             BookOrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public boolean tryRentBook(Long bookId, String username) {
        Book book = getBook(bookId);

        // If book is already rented
        if (!book.isAvailable()) {
            return false;
        }

        // User with penalty cannot rent books
        User user = getUser(username);
        if(user.getPenalty() > 0) {
            return false;
        }

        rentBook(book, user);
        return true;
    }

    private void rentBook(Book book, User user) {
        book.setAvailable(false);
        BookOrder order = new BookOrder(null, book, user, new Date());
        orderRepository.save(order);
    }

    public void returnBook(Long bookId) {
        Book book = getBook(bookId);
        book.setAvailable(true);
        BookOrder order = orderRepository.findByRentedBook(book);
        orderRepository.delete(order);
    }

    private Book getBook(Long bookId) {
        return bookRepository
                .findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book: " + bookId + " not found"));
    }

    private User getUser(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User: " + username + " not found"));
    }

    public List<Book> getBooksRentedByUser(String username) throws UsernameNotFoundException {
        List<BookOrder> orders = orderRepository.findAllByUserUsername(username);
        return orders.stream()
                .map(BookOrder::getRentedBook)
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksContainingPhrase(String phrase) {
        List<Book> similarTitles = bookRepository.findByTitleContaining(phrase);
        List<Book> similarAuthors = bookRepository.findAllByAuthorName(phrase);
        return Stream.concat(
                similarTitles.stream(),
                similarAuthors.stream()
        ).distinct()
                .collect(Collectors.toList());
    }
}
