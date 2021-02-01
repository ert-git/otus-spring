package ru.otus.levina.hw06.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.levina.hw06.domain.Author;
import ru.otus.levina.hw06.domain.Book;
import ru.otus.levina.hw06.domain.Genre;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> getById(long id) {
        try {
            return Optional.ofNullable(em.find(Book.class, id));
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return  em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    public void delete(Book book) {
        em.remove(book);
    }

    @Override
    public void insert(Book book) {
        List<Author> existedAuthors = book.getAuthors().stream().filter(a -> a.getId() > 0).collect(Collectors.toList());
        List<Author> newAuthors = book.getAuthors().stream().filter(a -> a.getId() == 0).collect(Collectors.toList());
        List<Genre> existedGenres = book.getGenres().stream().filter(a -> a.getId() > 0).collect(Collectors.toList());
        List<Genre> newGenres = book.getGenres().stream().filter(a -> a.getId() == 0).collect(Collectors.toList());
        book.setGenres(newGenres);
        book.setAuthors(newAuthors);
        em.persist(book);
        if (!existedGenres.isEmpty() || !existedAuthors.isEmpty()) {
            book.getGenres().addAll(existedGenres);
            book.getAuthors().addAll(existedAuthors);
            em.merge(book);
        }
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }


    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

}
