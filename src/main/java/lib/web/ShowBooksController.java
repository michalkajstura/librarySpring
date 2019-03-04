package lib.web;

import lib.book.Book;
import lib.process_books.BookRentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/books")
public class ShowBooksController {

    private final static String BOOKS_TO_SHOW = "books_list";
    private final static String RETURNING_BOOK = "returning_book";

    private BookRentalService bookRentalService;

    public ShowBooksController(BookRentalService bookRentalService) {
        this.bookRentalService = bookRentalService;
    }

    @GetMapping("/all")
    public String showAllBooks(Model model) {
        model.addAttribute(RETURNING_BOOK, false);
        model.addAttribute(BOOKS_TO_SHOW, bookRentalService.getAllBooks());
        return "books";
    }

    @GetMapping("/search")
    public String showSearchedBooks(Model model,
                                    @RequestParam("search_phrase") String searchPhrase) {
        model.addAttribute(RETURNING_BOOK, false);
        model.addAttribute(BOOKS_TO_SHOW,
                bookRentalService.getBooksContainingPhrase(searchPhrase));

        return "books";
    }

    @GetMapping("/rented")
    public String showRentedBooks(Model model,
                                  Principal principal) {
        List<Book> booksRentedByCurrentUser =
                bookRentalService.getBooksRentedByUser(principal.getName());
        model.addAttribute(RETURNING_BOOK, true);
        model.addAttribute(BOOKS_TO_SHOW, booksRentedByCurrentUser);

        return "books";
    }
}
