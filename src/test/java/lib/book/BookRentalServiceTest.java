//package lib.book;
//
//import lib.account.User;
//import lib.account.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//
//import static org.junit.Assert.*;
//
//public class BookRentalServiceTest {
//
//    @Mock
//    UserRepository userRepository;
//
//    @Mock
//    BookRepository bookRepository;
//
//    @Mock
//    BookOrderRepository orderRepository;
//
//    @Before
//    public void init() {
//        userRepository = mock(UserRepository.class);
//        when(userRepository.findByUsername("test")).
//                thenReturn(Optional.of(new User("test", "", "")));
//
//        bookRepository = mock(BookRepository.class);
//        orderRepository = mock(BookOrderRepository.class);
//    }
//
//    @Test
//    public void rentNotAvailableBookTest() {
////        Book book = new Book(0L, "", "",  1234, false, "", null);
//        Book book = mock(Book.class);
//        when(bookRepository.findById(0L)).
//                thenReturn(Optional.of(book));
//
//        BookRentalService bookRentalService = new BookRentalService(bookRepository, userRepository, orderRepository);
//
//        boolean success = bookRentalService.tryRentBook(0L, "test");
//        assertFalse(success);
//    }
//
//    @Test
//    public void changeAvailabilityAfterRent() {
//        Book book = new Book(0L, "", "",  1234, true, "", null);
//        when(bookRepository.findById(0L)).
//                thenReturn(Optional.of(book));
//
//        BookRentalService bookRentalService = new BookRentalService(bookRepository, userRepository);
//
//        boolean success = bookRentalService.tryRentBook(0L, "test");
//
//        assertTrue(success);
//        assertFalse(book.isAvailable());
//    }
//}