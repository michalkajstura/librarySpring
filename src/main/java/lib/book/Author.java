package lib.book;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long authorId;

    @Size(min = 1, message = "Author name must be longer")
    String authorName;

    Integer yearOfBirth;

    @Override
    public String toString() {
        return authorName;
    }
}
