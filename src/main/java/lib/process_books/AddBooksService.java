package lib.process_books;

import lib.book.*;
import org.springframework.stereotype.Service;

@Service
public class AddBooksService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private GenreRepository genreRepository;

    public AddBooksService(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public void addBook(Book book) {
//        Author author = Book
        bookRepository.save(book);
    }


}
