package lib.penalty;

import lib.account.User;
import lib.book.BookOrder;
import lib.book.BookOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class PenaltyService {
    private final static long MAX_RENTAL_DAYS = 34;
    private final static long PENALTY_PER_DAY = 50;

    private BookOrderRepository orderRepository;

    public PenaltyService(BookOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    void updatePenalties(List<User> users) {
        users.forEach(this::increasePenaltiesIfExceeded);
    }

    private long penaltyForBook(BookOrder order) {
        Date orderDate = order.getOrderDate();

        long days = daysFromRental(orderDate);
        return (days > MAX_RENTAL_DAYS)
                ? (days - MAX_RENTAL_DAYS) * PENALTY_PER_DAY
                : 0;
    }

    private void increasePenaltiesIfExceeded(User user) {
        List<BookOrder> orders = orderRepository.findAllByUser(user);
        long penaltyToPay = orders
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
