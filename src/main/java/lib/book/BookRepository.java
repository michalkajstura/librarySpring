package lib.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where lower(b.title) like lower(concat('%', :phrase, '%'))"+
            "or lower(b.author.authorName) like lower(concat('%', :phrase, '%'))")
    List<Book> findByTitleOrAuthorContaining(@Param("phrase") String phrase);

}
