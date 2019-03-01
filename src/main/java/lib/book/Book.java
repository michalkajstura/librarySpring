package lib.book;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Size(min = 1, message = "Title must be longer")
    private String title;

    @ManyToOne
    private Author author;

    @NotNull(message = "Publication year cannot be blank")
    private Integer publicationYear;

    @ManyToOne
    private Genre genre;

    private boolean isAvailable;
}
