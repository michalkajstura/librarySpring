package lib.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where lower(b.title) like lower(concat('%', :phrase, '%'))")
    List<Book> findByTitleContaining(@Param("phrase") String phrase);

//    @Query("select b from Book b inner join book_authors a on b.bookId = a.bookId")
//    List<Book> findAllByAuthorsContaining(String authorName);

    @Query(value =
            "select * from BOOK " +
                    "where BOOK_ID in " +
                    "(select  BOOK_ID from BOOK_AUTHORS  " +
                    "join  AUTHOR on (BOOK_AUTHORS.AUTHOR_ID = AUTHOR.AUTHOR_ID) " +
                    "where lower(AUTHOR_NAME) like lower(concat('%', :name, '%')))", nativeQuery = true)
    List<Book> findAllByAuthorName(@Param("name") String name);

}
