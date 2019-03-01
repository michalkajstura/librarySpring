//package lib.penalty;
//
//import lib.account.User;
//import lib.book.Book;
//import org.junit.Test;
//
//import java.time.LocalDate;
//import java.time.ZoneOffset;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class PenaltyServiceTest {
//
//    @Test
//    public void noPenaltyTest() {
//        User user1 = createUser(
//                createBook(false, new Date()),
//                createBook(true, null)
//        );
//        User user2 = createUser(
//                createBook(false, subtractDaysFromDate(5)),
//                createBook(false, subtractDaysFromDate(30))
//        );
//        List<User> users = Arrays.asList(user1, user2);
//
//        PenaltyService penaltyService = new PenaltyService();
//        penaltyService.updatePenalties(users);
//
//        long penaltySum = users.stream()
//                .map(User::getPenalty)
//                .reduce(0L, Long::sum);
//
//        assertEquals(0, penaltySum);
//    }
//
//    private Book createBook(boolean available, Date date) {
//        return new Book(0L, "", "", 1234, available, "", date);
//    }
//
//    private User createUser(Book... books) {
//        User user = new User("", "", "");
//        user.setRentedBooks(Arrays.asList(books));
//        return user;
//    }
//
//    private Date subtractDaysFromDate(long days) {
//        LocalDate nowMinusDays = LocalDate.now().minusDays(days);
//        return Date.from(nowMinusDays.atStartOfDay().toInstant(ZoneOffset.UTC));
//    }
//
//     @Test
//    public void somePenaltyTest() {
//        User user1 = createUser(
//                createBook(false, subtractDaysFromDate(PenaltyService.MAX_RENTAL_DAYS + 1)),
//                createBook(false, subtractDaysFromDate(PenaltyService.MAX_RENTAL_DAYS + 9))
//        );
//        User user2 = createUser(
//                createBook(false, subtractDaysFromDate(PenaltyService.MAX_RENTAL_DAYS + 10))
//        );
//        List<User> users = Arrays.asList(user1, user2);
//
//        PenaltyService penaltyService = new PenaltyService();
//        penaltyService.updatePenalties(users);
//
//        long penaltySum = users.stream()
//                .map(User::getPenalty)
//                .reduce(0L, Long::sum);
//
//        assertEquals(20 * PenaltyService.PENALTY_PER_DAY, penaltySum);
//    }
//}