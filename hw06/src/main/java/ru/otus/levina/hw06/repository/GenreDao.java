package ru.otus.levina.hw06.repository;


import ru.otus.levina.hw06.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

  Optional<Genre> getById(long id);

  List<Genre> getAll();

  void delete(Genre genre);

  void insert(Genre genre);

  void deleteById(long id);

  void update(Genre genre);

}
