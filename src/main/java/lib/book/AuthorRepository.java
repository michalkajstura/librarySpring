package lib.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "select * from AUTHOR a where lower(a.AUTHOR_NAME) like lower(:name) ",
            nativeQuery = true)
    List<Author> findByNameLike(@Param("name") String name);
}
