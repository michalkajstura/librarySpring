package lib.web;

import lib.process_books.BookRentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/books/process")
public class ProcessBooksController {

    private BookRentalService bookRentalService;

    @Autowired
    public ProcessBooksController(BookRentalService bookRentalService) {
        this.bookRentalService = bookRentalService;
    }

    @GetMapping(value = "/rent")
    public String rentBook(@RequestParam(name = "id") Long id,
                                    Principal principal,
                                    final RedirectAttributes redirectAttributes) {
        log.info("renting book: {} by user: {}", id, principal.getName());

        try {
            boolean success = bookRentalService.tryRentBook(id, principal.getName());
            if (!success) {
                redirectAttributes.addFlashAttribute("msg", "Book is not available");
                redirectAttributes.addFlashAttribute("css", "danger");
            }
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("msg", ex.getMessage());
            redirectAttributes.addFlashAttribute("css", "danger");
        }


        return "redirect:/books/all";
    }

    @GetMapping(value = "/return")
    public String returnBook(@RequestParam(name = "id") Long id,
                             final RedirectAttributes redirectAttributes) {
        log.info("returning book: {}", id);

        try {
            bookRentalService.returnBook(id);
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("msg", ex.getMessage());
            redirectAttributes.addFlashAttribute("css", "danger");
        }

        return "redirect:/books/rented";
    }
}
