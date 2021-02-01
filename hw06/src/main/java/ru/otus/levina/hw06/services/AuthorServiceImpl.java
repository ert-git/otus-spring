package ru.otus.levina.hw06.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw06.domain.Author;
import ru.otus.levina.hw06.repository.AuthorDao;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor()
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public Optional<Author> insert(String firstName, String lastName, String middleName) {
        Author author = new Author(0, firstName, lastName, middleName, null);
        authorDao.insert(author);
        return Optional.ofNullable(author);
    }

    @Override
    public void update(long authorId, String firstName, String lastName, String middleName) {
        Author author = authorDao.getById(authorId).orElseThrow();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setMiddleName(middleName);
        authorDao.update(author);
    }

    @Override
    public void delete(long id) {
        Author author = authorDao.getById(id).orElseThrow();
        authorDao.delete(author);
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }
}
