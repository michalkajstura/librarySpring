package lib.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, message = "Title must be longer")
    private String title;

//    @NotNull
//    @ManyToOne
//    @JoinColumn
//    private Author author;
    @Size(min = 1, message = "Author name must be longer")
    private String author;


    @NotNull(message = "Publication year cannot be blank")
    @Column(name="publication_year")
    private Integer publicationYear;

    private boolean available = true;

//    private Genre genre;
    @Size(min = 1, message = "Genre name must be longer")
    private String genre;

    private Date rentedAt;

    public Book(String title, String author, int publicationYear, String genre) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
    }
}
