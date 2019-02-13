package lib.web;

import lib.booksRental.BookRepository;
import lib.booksRental.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping(value = "/addBooks")
public class AddBooksController {

    private final BookRepository bookRepository;

    public AddBooksController(BookRepository bookRepository
                              ) {
        this.bookRepository = bookRepository;
    }

    @ModelAttribute
    public Book book() {
        return new Book();
    }

    @GetMapping
    public String addBooks() {
        return "addBooks";
    }

    @PostMapping
    public String processAddedBook(@Valid Book newBook, Errors errors) {
        if (errors.hasErrors())
            return "addBooks";

        bookRepository.save(newBook);

        return "redirect:/addBooks";
    }
}
