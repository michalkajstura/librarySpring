package lib.data;

import lib.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where lower(b.title) like lower(concat('%', :phrase, '%'))"+
            "or lower(b.author) like lower(concat('%', :phrase, '%'))")
    List<Book> findByTitleOrAuthorContaining(@Param("phrase") String phrase);

}
