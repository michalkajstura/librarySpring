package lib.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;

    private final String name;

    @OneToMany
    private Set<Book> books;


}
