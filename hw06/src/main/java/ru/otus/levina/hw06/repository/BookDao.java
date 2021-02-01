package ru.otus.levina.hw06.repository;

import ru.otus.levina.hw06.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Optional<Book> getById(long id);

    List<Book> getAll();

    void delete(Book book);

    void insert(Book book);

    void update(Book book);

    void deleteById(long id);
}
