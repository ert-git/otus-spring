package ru.otus.levina.hw06.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.levina.hw06.domain.Author;
import ru.otus.levina.hw06.domain.Book;
import ru.otus.levina.hw06.domain.Genre;
import ru.otus.levina.hw06.services.AuthorService;
import ru.otus.levina.hw06.services.BookService;
import ru.otus.levina.hw06.services.GenreService;
import ru.otus.levina.hw06.services.UserIO;

import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final UserIO io;

    @ShellMethod(value = "list authors", key = {"la", "list-authors"})
    public String listAuthors() {
        return authorService.getAll()
                .stream()
                .map(a -> a.getId() + " - " + a.getAuthorFullName() + "\r\n")
                .collect(Collectors.joining());
    }

    @ShellMethod(value = "list genres", key = {"lg", "list-genres"})
    public String listGenres() {
        return genreService.getAll().stream().map(g -> g.getId() + " - " + g.getName() + "\r\n").collect(Collectors.joining());
    }

    @ShellMethod(value = "list books", key = {"lb", "list-books"})
    public String listBooks() {
        return bookService.getBooks().stream().map(g -> g.getId() + " - " + g.getTitle() + "\r\n").collect(Collectors.joining());
    }

    @ShellMethod(value = "get book", key = {"gb", "get-books"})
    public String getBook() {
        io.print("Enter book id:");
        String id = readNoEmpty();
        Book book = bookService.getBook(Long.parseLong(id));
        return book.getId() + " - " + book.getTitle()
                + "\r\nauthors: " + book.getAuthors()
                + "\r\ngenres: " + book.getGenres();
    }

    @ShellMethod(value = "add genre", key = {"ag", "add-genre"})
    public String addGenre() {
        io.print("Enter genre name:");
        String name = readNoEmpty();
        Optional<Genre> g = genreService.insert(name);
        return g.isPresent() ? "Genre created: " + g.toString() : "Failed to create genre";
    }

    @ShellMethod(value = "add author", key = {"aa", "add-author"})
    public String addAuthor() {
        io.print("Enter author firstname:");
        String firstName = readNoEmpty();
        io.print("Enter author lastname:");
        String lastName = readNoEmpty();
        io.print("Enter author middlename:");
        String middleName = readNoEmpty();
        Optional<Author> a = authorService.insert(firstName, lastName, middleName);
        return a.isPresent() ? "Author created: " + a.toString() : "Failed to create author";
    }


    @ShellMethod(value = "add book", key = {"ab", "add-book"})
    public String addBook() {
        io.print("Enter book title:");
        String title = readNoEmpty();
        io.print("Enter author id:");
        String authorId = readNoEmpty();
        io.print("Enter genre id:");
        String genreId = readNoEmpty();
        Optional<Book> b = bookService.insert(title, Long.parseLong(authorId), Long.parseLong(genreId));
        return b.isPresent() ? "Book created: " + b.toString() : "Failed to create book";
    }

    private String readNoEmpty() {
        String input = io.read();
        while (input == null || input.trim().isEmpty()) {
            io.print("You have to enter a value");
            input = io.read();
        }
        return input;
    }

}
