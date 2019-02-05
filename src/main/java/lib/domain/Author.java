package lib.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String secondName;

    private int yearOfBirth;

    @ManyToMany(targetEntity = Genre.class)
    private List<Genre> genres;

}
