package ru.otus.levina.hw06.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.levina.hw06.domain.Genre;
import ru.otus.levina.hw06.repository.GenreDao;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public Optional<Genre> insert(String name) {
        log.info("insert: name={}", name);
        Genre genre = new Genre(0, name, null);
        genreDao.insert(genre);
        return Optional.ofNullable(genre);
    }

    @Override
    public void update(long genreId, String name) {
        Genre genre = genreDao.getById(genreId).orElseThrow();
        genre.setName(name);
        genreDao.update(genre);
    }

    @Override
    public void delete(long id) {
        Genre genre = genreDao.getById(id).orElseThrow();
        genreDao.delete(genre);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

}
