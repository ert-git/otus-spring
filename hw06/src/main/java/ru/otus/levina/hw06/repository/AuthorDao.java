package ru.otus.levina.hw06.repository;

import ru.otus.levina.hw06.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

  Optional<Author> getById(long id);

  List<Author> getAll();

  void insert(Author author);

  void delete(Author author);
  void deleteById(long id);

  void update(Author author);

}
