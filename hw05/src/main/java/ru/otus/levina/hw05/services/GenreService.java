package ru.otus.levina.hw05.services;

import ru.otus.levina.hw05.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

  List<Genre> getAll();

  void delete(long genreId);

  Optional<Genre> insert(String name);

  void update(long genreId, String name);
}
