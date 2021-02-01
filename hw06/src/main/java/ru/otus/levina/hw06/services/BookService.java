package ru.otus.levina.hw06.services;


import ru.otus.levina.hw06.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

  void delete(long id);

  Optional<Book> insert(String title, long authorId, long genreId);

  void update(long bookId, String title);

  void addComment(long bookId, String text);

  Book getBook(long bookId);

  List<Book> getBooks();
}
