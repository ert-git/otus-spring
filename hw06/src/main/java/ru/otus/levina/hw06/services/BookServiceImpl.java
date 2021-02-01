package ru.otus.levina.hw06.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.levina.hw06.domain.Author;
import ru.otus.levina.hw06.domain.Book;
import ru.otus.levina.hw06.domain.Comment;
import ru.otus.levina.hw06.domain.Genre;
import ru.otus.levina.hw06.repository.AuthorDao;
import ru.otus.levina.hw06.repository.BookDao;
import ru.otus.levina.hw06.repository.GenreDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        Book book = new Book(title, Collections.singletonList(author), Collections.singletonList(genre));
        bookDao.insert(book);
        return Optional.ofNullable(book);
    }


    @Override
    public void update(long bookId, String title) {
      Book book =  bookDao.getById(bookId).orElseThrow();
      bookDao.update(book);
    }

    @Override
    public void delete(long bookId) {
      Book book =  bookDao.getById(bookId).orElseThrow();
      bookDao.delete(book);
    }


    @Override
    public void addComment(long bookId, String text) {
        Book book =  bookDao.getById(bookId).orElseThrow();
        if (book.getComments()==null) {
            book.setComments(new ArrayList<>());
        }
        book.getComments().add(new Comment(0, text, book));
    }

    @Override
    @Transactional
    public Book getBook(long bookId) {
        Book book =  bookDao.getById(bookId).orElseThrow();
        book.setAuthors(new ArrayList<>(book.getAuthors()));
        book.setGenres(new ArrayList<>(book.getGenres()));
        book.setComments(new ArrayList<>(book.getComments()));
        return book;
    }

    @Override
    public List<Book> getBooks() {
        return bookDao.getAll();
    }

}
