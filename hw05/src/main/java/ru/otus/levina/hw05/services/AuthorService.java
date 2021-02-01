package ru.otus.levina.hw05.services;


import ru.otus.levina.hw05.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

  List<Author> getAll();

  Optional<Author> insert(String firstName, String lastName, String middleName);

  void delete(long authorId);

  void update(long authorId, String firstName, String lastName, String middleName);
}
