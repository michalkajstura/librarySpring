package lib.penalty;

import lib.account.User;
import lib.account.UserRepository;
import lib.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PenaltyService {
    public final static long MAX_RENTAL_DAYS = 34;
    public final static long PENALTY_PER_DAY = 50;

    public void updatePenalties(List<User> users) {
        users.forEach(this::increasePenaltiesIfExceeded);
    }

    private long penaltyForBook(Book book) {
        Optional<Date> date = book.getRentedAt();
        if (!date.isPresent())
            return 0;

        long days = daysFromRental(date.get());
        return (days > MAX_RENTAL_DAYS)
                ? (days - MAX_RENTAL_DAYS) * PENALTY_PER_DAY
                : 0;
    }

    private void increasePenaltiesIfExceeded(User user) {
        long penaltyToPay = user.getRentedBooks()
                .stream()
                .map(this::penaltyForBook)
                .reduce(0L, Long::sum);
        user.setPenalty(penaltyToPay);
    }

    private long daysFromRental(Date rentalDate) {
         LocalDate rentalDateLocal = rentalDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
         return ChronoUnit.DAYS.between(rentalDateLocal, LocalDate.now());
    }
}
