package ru.otus.levina.hw05.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.levina.hw05.domain.Author;
import ru.otus.levina.hw05.domain.Book;
import ru.otus.levina.hw05.domain.Genre;
import ru.otus.levina.hw05.repository.AuthorDaoImpl;
import ru.otus.levina.hw05.repository.BookDao;
import ru.otus.levina.hw05.repository.BookDaoImpl;
import ru.otus.levina.hw05.repository.GenreDaoImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BookDaoTest")
@JdbcTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;
    private static final Author expectedAuthor = new Author(1001, "Александр", "Пушкин", "Сергеевич");
    private static final Genre expectedGenre = new Genre(1002, "Русская классика");

    @DisplayName("find book by id")
    @Test
    void testBookById() {
        int id = 1001;
        Book actualBook = bookDao.getById(id).orElse(null);
        assertNotNull(actualBook);
        Book expectedBook = new Book(id, "Евгений Онегин", Arrays.asList(new Author[] {expectedAuthor}), Arrays.asList(new Genre[] {expectedGenre}));
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("find book by not existed id")
    @Test
    void testNoBookById() {
        int id = 1001000;
        Book actualBook = bookDao.getById(id).orElse(null);
        assertNull(actualBook);
    }

    @DisplayName("add a book")
    @Test
    void testAddBook() {
        Book expectedBook = new Book( "b1", Arrays.asList(new Author[] {expectedAuthor}), Arrays.asList(new Genre[] {expectedGenre}));
        Long id = bookDao.insert(expectedBook).map(a -> a.getId()).orElseGet(null);
        assertNotNull(id);
        Book actualBook = bookDao.getById(id).orElse(null);
        assertEquals(expectedBook, actualBook);
    }


    @DisplayName("multi author/genre book")
    @Test
    void testJoins() {
        List<Author> authors = Arrays.asList(new Author[] {
                new Author("f1", "l1", "m1"),
                new Author("f2", "l2", "m2")});
        List<Genre> genres = Arrays.asList(new Genre[] {
                new Genre("g1"),
                new Genre("g2")});
        Book expectedBook = new Book( "b3", authors, genres);
        Long id = bookDao.insert(expectedBook).map(a -> a.getId()).orElseGet(null);
        assertNotNull(id);
        Book actualBook = bookDao.getById(id).orElse(null);
        System.out.println(actualBook);
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("update a book")
    @Test
    void testUpdateBook() {
        List<Author> authors = Arrays.asList(new Author[] {
                new Author("f1", "l1", "m1"),
                new Author("f2", "l2", "m2")});
        List<Genre> genres = Arrays.asList(new Genre[] {
                new Genre("g1"),
                new Genre("g2")});
        Book origBook = new Book( "b2", authors, genres);
        Long id = bookDao.insert(origBook).map(a -> a.getId()).orElseGet(null);
        assertNotNull(id);
        Book actualBook = bookDao.getById(id).orElse(null);
        assertEquals(origBook, actualBook);

        origBook.setTitle("b4");
        origBook.setAuthors(Arrays.asList(new Author[]{authors.get(1), new Author("f3", "f4", "f5")}));
        origBook.setGenres(Arrays.asList(new Genre[]{genres.get(1), new Genre("g4")}));

        bookDao.update(origBook);
        actualBook = bookDao.getById(id).orElse(null);
        System.out.println(actualBook);
        assertEquals(origBook, actualBook);
    }

    @DisplayName("delete a book")
    @Test
    void testDeleteBook() {
        List<Author> authors = Arrays.asList(new Author[] {
                new Author("f1", "l1", "m1"),
                new Author("f2", "l2", "m2")});
        List<Genre> genres = Arrays.asList(new Genre[] {
                new Genre("g1"),
                new Genre("g2")});
        Book expectedBook = new Book( "b2", authors, genres);
        Long id = bookDao.insert(expectedBook).map(a -> a.getId()).orElseGet(null);
        assertNotNull(id);
        Book actualBook = bookDao.getById(id).orElse(null);
        assertEquals(expectedBook, actualBook);

        List<Author> authors1 = bookDao.getBookAuthors(id);
        assertEquals(authors, authors1);

        List<Genre> genres1 = bookDao.getBookGenres(id);
        assertEquals(genres, genres1);

        bookDao.delete(actualBook);
        actualBook = bookDao.getById(id).orElse(null);
        assertNull(actualBook);

        List<Author> authors2 = bookDao.getBookAuthors(id);
        assertTrue(authors2.isEmpty());

        List<Genre> genres2 = bookDao.getBookGenres(id);
        assertTrue(genres2.isEmpty());
    }
}
