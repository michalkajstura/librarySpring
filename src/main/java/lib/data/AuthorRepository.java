package lib.data;

import lib.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByFirstNameAndSecondName(String firstName, String secondName);
}
