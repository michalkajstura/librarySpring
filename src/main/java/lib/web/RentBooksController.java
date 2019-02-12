package lib.web;

import lib.data.*;

import lib.domain.Book;
import lib.service.BookRentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/books")
public class RentBooksController {

    private BookRentalService bookRentalService;

    @Autowired
    public RentBooksController(BookRentalService bookRentalService) {
        this.bookRentalService = bookRentalService;
    }

    @GetMapping
    public String showAllBooks(Model model, Principal principal) {
        String username = principal.getName();
        List<Book> booksRentedByCurrentUser =
               bookRentalService.getBooksRentedByUser(username);
        model.addAttribute("booksRentedByUser", booksRentedByCurrentUser);

        model.addAttribute("books", bookRentalService.getAllBooks());

        return "books";
    }

    @GetMapping(value = "/rent")
    public String processUserRental(@RequestParam(name = "id") Long id,
                                    Principal principal,
                                    final RedirectAttributes redirectAttributes) {
        log.info("renting book: {}", id);

        String message = "";
        String cssColor = "success";

        try {
            bookRentalService.rentBook(id, principal.getName());
            message = "Book rented";
        } catch (BookNotFoundException | UsernameNotFoundException ex) {
            message = ex.getMessage();
            cssColor = "danger";
        }

        redirectAttributes.addFlashAttribute("msg", message);
        redirectAttributes.addFlashAttribute("css", cssColor);
        return "redirect:/books";
    }

    @GetMapping(value = "/return")
    public String returnBook(@RequestParam(name = "id") Long id,
                             Principal principal,
                             final RedirectAttributes redirectAttributes) {
        log.info("returning book: {}", id);

        String message = "";
        String cssColor = "success";

        try {
            bookRentalService.returnBook(id, principal.getName());
            message = "Book returned";
        } catch (BookNotFoundException | UsernameNotFoundException ex) {
            message = ex.getMessage();
            cssColor = "danger";
        }

        redirectAttributes.addFlashAttribute("msg", message);
        redirectAttributes.addFlashAttribute("css", cssColor);
        return "redirect:/books";
    }

}
