package ru.otus.levina.hw05.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw05.domain.Author;
import ru.otus.levina.hw05.domain.Book;
import ru.otus.levina.hw05.domain.Genre;
import ru.otus.levina.hw05.repository.AuthorDao;
import ru.otus.levina.hw05.repository.BookDao;
import ru.otus.levina.hw05.repository.GenreDao;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public Optional<Book> insert(String title, long authorId, long genreId) {
        Author author = authorDao.getById(authorId).orElseThrow();
        Genre genre = genreDao.getById(genreId).orElseThrow();
        return bookDao.insert(new Book(title, Collections.singletonList(author), Collections.singletonList(genre)));
    }


    @Override
    public void update(long bookId, String title) {
        Book book = bookDao.getById(bookId).orElseThrow();
        book.setTitle(title);
        bookDao.update(book);
    }

    @Override
    public void addAuthor(long bookId, long authorId) {
        Book book = bookDao.getById(bookId).orElseThrow();
        Author author = authorDao.getById(authorId).orElseThrow();
        if (book.getAuthors().stream().noneMatch(a -> a.getId() == authorId)) {
            book.getAuthors().add(author);
        }
        bookDao.update(book);
    }

    @Override
    public void delete(long bookId) {
        Book book = bookDao.getById(bookId).orElseThrow();
        bookDao.delete(book);
    }


}
