package ru.otus.levina.hw05.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.levina.hw05.domain.Author;
import ru.otus.levina.hw05.domain.Book;
import ru.otus.levina.hw05.domain.Genre;
import ru.otus.levina.hw05.repository.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("BookDaoTest")
@JdbcTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
public class BookDaoTest {
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private GenreDao genreDao;

    private static final Author expectedAuthor = new Author(1001, "Александр", "Пушкин", "Сергеевич");
    private static final Genre expectedGenre = new Genre(1002, "Русская классика");

    @DisplayName("find book by id")
    @Test
    void testBookById() {
        int id = 1001;
        Book actualBook = bookDao.getById(id).orElse(null);
        assertNotNull(actualBook);
        Book expectedBook = new Book(id, "Евгений Онегин", List.of(expectedAuthor), List.of(expectedGenre));
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("find book by not existed id")
    @Test
    void testNoBookById() {
        int id = 1001000;
        Book actualBook = bookDao.getById(id).orElse(null);
        assertNull(actualBook);
    }

    @DisplayName("list books")
    @Test
    void testBooksByPage() {
        List<Book> expectedBooks = List.of(
                new Book(1003, "b1003",
                        List.of(
                                new Author(1003, "a1003", null, null),
                                new Author(1004, "a1004", null, null)
                        ),
                        List.of(new Genre(1003, "g1003"))
                ),
                new Book(1004, "b1004",
                        List.of(
                                new Author(1004, "a1004", null, null),
                                new Author(1005, "a1005", null, null)
                        ),
                        List.of(
                                new Genre(1003, "g1003"),
                                new Genre(1004, "g1004"))
                ));

        List<Book> actualBooks = bookDao.list();
        actualBooks.forEach(b -> log.info("b: {}", b));
        Book expectedBook = expectedBooks.get(1);
        assertEquals(expectedBooks.stream().filter(b -> b.getId() == expectedBook.getId()).findFirst().get(), expectedBook);
    }

    @DisplayName("add a book")
    @Test
    void testAddBook() {
        Book expectedBook = new Book("b1", List.of(expectedAuthor), List.of(expectedGenre));
        bookDao.insert(expectedBook);
        long id = expectedBook.getId();
        assertTrue(id > 0);
        Book actualBook = bookDao.getById(id).orElse(null);
        log.info("testAddBook: {}", actualBook);
        assertEquals(expectedBook, actualBook);
    }


    @DisplayName("multi author/genre book")
    @Test
    void testJoins() {
        List<Author> authors = List.of(
                new Author("f1", "l1", "m1"),
                new Author("f2", "l2", "m2"));
        List<Genre> genres = List.of(
                new Genre("g1"),
                new Genre("g2"));
        Book expectedBook = new Book("b3", authors, genres);
        bookDao.insert(expectedBook);
        long id = expectedBook.getId();
        assertTrue(id > 0);
        Book actualBook = bookDao.getById(id).orElse(null);
        log.info("testJoins: {}", actualBook);
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("update a book")
    @Test
    void testUpdateBook() {
        List<Author> authors = List.of(
                new Author("f1", "l1", "m1"),
                new Author("f2", "l2", "m2"));
        List<Genre> genres = List.of(
                new Genre("g1"),
                new Genre("g2"));
        Book origBook = new Book("b2", authors, genres);
        bookDao.insert(origBook);
        long id = origBook.getId();
        assertTrue(id > 0);
        Book actualBook = bookDao.getById(id).orElse(null);
        assertEquals(origBook, actualBook);

        origBook.setTitle("b4");
        origBook.setAuthors(List.of(authors.get(1), new Author("f3", "f4", "f5")));
        origBook.setGenres(List.of(genres.get(1), new Genre("g4")));

        bookDao.update(origBook);
        actualBook = bookDao.getById(id).orElse(null);
        assertEquals(origBook, actualBook);
    }

    @DisplayName("delete a book")
    @Test
    void testDeleteBook() {
        List<Author> authors = List.of(
                new Author("f1", "l1", "m1"),
                new Author("f2", "l2", "m2"));
        List<Genre> genres = List.of(
                new Genre("g1"),
                new Genre("g2"));
        Book expectedBook = new Book("b2", authors, genres);
        bookDao.insert(expectedBook);
        long id = expectedBook.getId();
        assertTrue(id > 0);
        Book actualBook = bookDao.getById(id).orElse(null);
        assertEquals(expectedBook, actualBook);

        List<Author> authors1 = authorDao.getByBookId(id);
        assertEquals(authors, authors1);

        List<Genre> genres1 = genreDao.getByBookId(id);
        assertEquals(genres, genres1);

        bookDao.delete(actualBook);
        actualBook = bookDao.getById(id).orElse(null);
        assertNull(actualBook);

        List<Author> authors2 = authorDao.getByBookId(id);
        assertTrue(authors2.isEmpty());

        List<Genre> genres2 = genreDao.getByBookId(id);
        assertTrue(genres2.isEmpty());
    }
}
