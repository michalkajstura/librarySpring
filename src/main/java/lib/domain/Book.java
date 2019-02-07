package lib.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn
    private Author author;

    @Column(name="publication_year")
    private int publicationYear;

    private boolean available;

//    private Genre genre;
    private String genre;

    public Book(String title, Author author, int publicationYear, String genre) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
    }
}
