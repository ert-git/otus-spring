package ru.otus.levina.hw05.repository;

import ru.otus.levina.hw05.domain.Author;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AuthorDao {

    Optional<Author> getById(long id);

    List<Author> getAll();

    Optional<Author> insert(Author author);

    void delete(Author author);

    void update(Author author);

    List<Author> getByIdList(List<Long> idList);

    List<Author> getByBookId(long bookId);

    Map<Long, List<Author>> getByBookIdList(List<Long> bookIds);
}
