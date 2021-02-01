package ru.otus.levina.hw06.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
@EqualsAndHashCode(exclude = "books")
@ToString(exclude = "books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<Book> books;

    public Genre(String name) {
        this(0, name);
    }

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
