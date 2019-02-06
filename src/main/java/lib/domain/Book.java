package lib.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private boolean available;

    @ManyToOne
    @JoinColumn
    private Author author;

    @Column(name="publication_year")
    private int publicationYear;

    @ManyToOne
    @JoinColumn
    private Genre genre;

}
