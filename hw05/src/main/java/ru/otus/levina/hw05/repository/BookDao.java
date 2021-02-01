package ru.otus.levina.hw05.repository;

import ru.otus.levina.hw05.domain.Author;
import ru.otus.levina.hw05.domain.Book;
import ru.otus.levina.hw05.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Optional<Book> getById(long id);

    void delete(Book book);

    List<Book> list();

    void insert(Book book);

    void update(Book book);

}
