package ru.otus.levina.hw06.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "authors")
@EqualsAndHashCode(exclude = "books")
@ToString(exclude = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;

    public Author(String firstName, String lastName, String middleName) {
       this(0, firstName, lastName, middleName);
    }

    public Author(long id, String firstName, String lastName, String middleName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getAuthorFullName() {
        return (Optional.ofNullable(getLastName()).orElse("")
                + " " + Optional.ofNullable(getFirstName()).orElse("")
                + " " + Optional.ofNullable(getMiddleName()).orElse(""))
                .trim();
    }

}
