package ru.otus.levina.hw05.repository;


import ru.otus.levina.hw05.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Optional<Genre> getById(long id);

    List<Genre> getAll();

    void delete(Genre genre);

    void insert(Genre genre);

    void update(Genre genre);

    List<Genre> getByIdList(List<Long> joinedIdListByBookId);

    List<Genre> getByBookId(long bookId);

}
