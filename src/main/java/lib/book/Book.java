package lib.book;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Optional;

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

    @Size(min = 1, message = "Author name must be longer")
    private String author;

    @NotNull(message = "Publication year cannot be blank")
    @Column(name="publication_year")
    private Integer publicationYear;

    private boolean available = true;

    @Size(min = 1, message = "Genre name must be longer")
    private String genre;

    private Date rentedAt;

    public Optional<Date> getRentedAt() {
        return Optional.ofNullable(rentedAt);
    }
}
