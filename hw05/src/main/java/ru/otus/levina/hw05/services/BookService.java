package ru.otus.levina.hw05.services;


import ru.otus.levina.hw05.domain.Book;

import java.util.Optional;

public interface BookService {

    void update(long bookId, String title);

    void addAuthor(long bookId, long authorId);

    void delete(long id);

    Optional<Book> insert(String title, long authorId, long genreId);

}
