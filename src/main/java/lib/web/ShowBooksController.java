package lib.web;

import lib.book.Book;
import lib.book.BookRentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/books")
public class ShowBooksController {

    private BookRentalService bookRentalService;

    @Autowired
    public ShowBooksController(BookRentalService bookRentalService) {
        this.bookRentalService = bookRentalService;
    }

    @GetMapping
    public String showAllBooks(Model model, Principal principal) {
        addBookRentedByUser(model, principal.getName());

        model.addAttribute("books", bookRentalService.getAllBooks());
        return "books";
    }

    @PostMapping("/search")
    public String showSearchedBooks(Model model,
                                    Principal principal,
                                    @RequestParam("searchPhrase") String searchPhrase) {

        addBookRentedByUser(model, principal.getName());

        model.addAttribute("books",
                bookRentalService.getBooksContainingPhrase(searchPhrase));

        return "books";
    }

    private void addBookRentedByUser(Model model, String username) {
        List<Book> booksRentedByCurrentUser =
                bookRentalService.getBooksRentedByUser(username);
        model.addAttribute("booksRentedByUser", booksRentedByCurrentUser);
    }

}
