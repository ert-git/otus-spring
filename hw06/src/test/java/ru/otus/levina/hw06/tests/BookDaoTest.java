package ru.otus.levina.hw06.tests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.levina.hw06.domain.Author;
import ru.otus.levina.hw06.domain.Book;
import ru.otus.levina.hw06.domain.Comment;
import ru.otus.levina.hw06.domain.Genre;
import ru.otus.levina.hw06.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("BookDaoTest")
@DataJpaTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao aDao;
    @Autowired
    private GenreDao gDao;
    private static final Author existedAuthor = new Author(1001, "Александр", "Пушкин", "Сергеевич", null);
    private static final Genre existedGenre = new Genre(1002, "Русская классика", null);

    @DisplayName("find book by id")
    @Test
    void testBookById() {
        int id = 1001;
        Book actualBook = bookDao.getById(id).orElse(null);
        assertNotNull(actualBook);
        Book expectedBook = new Book(id, "Евгений Онегин",
                Collections.singletonList(existedAuthor),
                Collections.singletonList(existedGenre), null);
        expectedBook.setComments(Collections.EMPTY_LIST);
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
        Book expectedBook = new Book("b1",
                new ArrayList<>(Collections.singletonList(existedAuthor)),
                new ArrayList<>(Collections.singletonList(existedGenre)));
        bookDao.insert(expectedBook);
        long id = expectedBook.getId();
        assertTrue(id > 0);
        Book actualBook = bookDao.getById(id).orElse(null);
        log.info("expectedBook: {}", expectedBook);
        log.info("actualBook: {}", actualBook);
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("insert multi author/genre book")
    @Test
    void testJoins() {
        List<Author> authors = Arrays.asList(new Author(0, "f21", "l21", "m21", null),
                new Author(0, "f22", "l22", "m22", null));
        List<Genre> genres = Arrays.asList(new Genre(0, "g21", null),
                new Genre(0, "g22", null));
        Book expectedBook = new Book("b2", authors, genres);
        bookDao.insert(expectedBook);
        long id = expectedBook.getId();
        assertTrue(id > 0);
        Book actualBook = bookDao.getById(id).orElse(null);
        checkBook(expectedBook, actualBook);
        log.info("actualBook: {}", actualBook);
    }

    private void checkBook(Book expectedBook, Book actualBook) {
        log.info("expectedBook: {}", expectedBook);
        log.info("actualBook: {}", actualBook);

        assertEquals(expectedBook.getAuthors().size(), actualBook.getAuthors().size());
        actualBook.getAuthors().forEach(a -> {
            assertTrue(a.getId() > 0);
            assertEquals(aDao.getById(a.getId()).get(), a);
        });
        assertEquals(expectedBook.getGenres().size(), actualBook.getGenres().size());
        actualBook.getGenres().forEach(g -> {
            assertTrue(g.getId() > 0);
            assertEquals(gDao.getById(g.getId()).get(), g);
        });
        expectedBook.setAuthors(actualBook.getAuthors());
        expectedBook.setGenres(actualBook.getGenres());
        assertEquals(expectedBook, actualBook);
    }

    @DisplayName("update a book")
    @Test
    void tesUpdateBook() {
        List<Author> authors = Arrays.asList(
                new Author("f31", "l31", "m31"),
                new Author("f32", "l32", "m32"));
        List<Genre> genres = Arrays.asList(
                new Genre("g31"),
                new Genre("g32"));
        Book origBook = new Book("b31", new ArrayList<>(authors), new ArrayList<>(genres));
        bookDao.insert(origBook);
        long id = origBook.getId();
        assertTrue(id > 0);

        Book actualBook = bookDao.getById(id).orElse(null);
        checkBook(origBook, actualBook);

        authors = Arrays.asList(authors.get(0),
                new Author("f33", "l33", "m33"));
        genres = Arrays.asList(genres.get(0),
                new Genre("g33"));
        origBook = new Book(actualBook.getId(), "b32", new ArrayList<>(authors), new ArrayList<>(genres), null);

        bookDao.update(origBook);
        actualBook = bookDao.getById(id).orElse(null);

        checkBook(origBook, actualBook);
    }

    @DisplayName("delete a book")
    @Test
    void testDeleteBook() {
        List<Author> authors = Arrays.asList(new Author(0, "f1", "l1", "m1", null),
                new Author(0, "f2", "l2", "m2", null));
        List<Genre> genres = Arrays.asList(new Genre(0, "g1", null),
                new Genre(0, "g2", null));
        Book expectedBook = new Book("b2", authors, genres);
        bookDao.insert(expectedBook);
        long id = expectedBook.getId();
        assertTrue(id > 0);
        Book actualBook = bookDao.getById(id).orElse(null);
        assertEquals(expectedBook, actualBook);
        bookDao.delete(actualBook);
        actualBook = bookDao.getById(id).orElse(null);
        assertNull(actualBook);
    }

    @DisplayName("add a comment")
    @Test
    void testAddCommentToBook() {
        Book expectedBook = new Book("b1",
                new ArrayList<>(Collections.singletonList(existedAuthor)),
                new ArrayList<>(Collections.singletonList(existedGenre)));
        bookDao.insert(expectedBook);
        long id = expectedBook.getId();
        assertTrue(id > 0);
        Book actualBook = bookDao.getById(id).orElse(null);
        assertEquals(expectedBook, actualBook);

        expectedBook.setComments(new ArrayList<>());
        expectedBook.getComments().add(new Comment(0, "c1", expectedBook));
        bookDao.update(expectedBook);

        actualBook = bookDao.getById(id).orElse(null);
        assertNotNull(actualBook);
        assertEquals(expectedBook, actualBook);
        assertTrue(actualBook.getComments().get(0).getId() > 0);
    }

    @DisplayName("list books")
    @Test
    void testListBooks() {
        List<Book> actualList = bookDao.getAll();
        List<Author> authors = Arrays.asList(
                new Author(1001, "Александр", "Пушкин", "Сергеевич", null),
                new Author(1002, "Гомер", null, null, null),
                new Author(1003, "a1003", null, null, null),
                new Author(1004, "a1004", null, null, null),
                new Author(1005, "a1005", null, null, null));
        List<Genre> genres = Arrays.asList(
                new Genre(1001, "Античная литература", null),
                new Genre(1002, "Русская классика", null),
                new Genre(1003, "g1003", null),
                new Genre(1004, "g1004", null));
        List<Book> expectedList = Arrays.asList(
                new Book(1001, "Евгений Онегин", Arrays.asList(authors.get(0)), Arrays.asList(genres.get(1)), new ArrayList<Comment>()),
                new Book(1002, "Одиссея", Arrays.asList(authors.get(1)), Arrays.asList(genres.get(0)), new ArrayList<Comment>()),
                new Book(1003, "b1003", Arrays.asList(authors.get(2), authors.get(3)), Arrays.asList(genres.get(2)), new ArrayList<Comment>()),
                new Book(1004, "b1004", Arrays.asList(authors.get(3), authors.get(4)), Arrays.asList(genres.get(2), genres.get(3)), new ArrayList<Comment>()),
                new Book(1005, "b1005", Arrays.asList(authors.get(2)), Arrays.asList(genres.get(2)), new ArrayList<Comment>())
        );
        assertEquals(expectedList, actualList);
    }


    @DisplayName("multi author/genre book")
    @Test
    void testInsertMultiAuthorsGenresBook() {
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

}
