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
import ru.otus.levina.hw06.domain.Genre;
import ru.otus.levina.hw06.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class GenreDaoImpl implements GenreDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return Optional.ofNullable(em.find(Genre.class, id));
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getAll()  {
        return em.createQuery("select a from Genre a", Genre.class).getResultList();
    }

    @Override
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    public void delete(Genre genre) {
        em.remove(genre);
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Genre a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(Genre genre) {
        em.merge(genre);
    }
}
