package lib.web;

import lib.data.*;

import lib.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/books")
public class BooksController {

    private BookRepository bookRepository;

    @Autowired
    public BooksController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public String showAllBooks(Model model) {

        model.addAttribute("books", bookRepository.findAll());
        return "books";
    }

    @GetMapping(value = "/rent")
    public String rentBook(@RequestParam(name = "id") Long id,
                           final RedirectAttributes redirectAttributes) {
        log.info("renting book: {}", id);

        Optional<Book> bookOptional = bookRepository.findById(id);

        String message = "";
        String cssColor = "success";

        if (!bookOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("msg", "The book doesnt exist");
            redirectAttributes.addFlashAttribute("css", "danger");
            return "redirect:/books";
        }

        Book book = bookOptional.get();
        if (book.isAvailable()) {
            rent(book);
            message = "Book rented!";
        } else {
            message = "Book is already rented";
            cssColor = "danger";
        }

        redirectAttributes.addFlashAttribute("msg", message);
        redirectAttributes.addFlashAttribute("css", cssColor);
        return "redirect:/books";
    }

    private void rent(Book book) {
        book.setAvailable(false);
        book.setRentedAt(new Date());
        bookRepository.save(book);
    }
}
