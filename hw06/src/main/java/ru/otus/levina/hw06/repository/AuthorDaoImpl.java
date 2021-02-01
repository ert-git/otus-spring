package ru.otus.levina.hw06.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import ru.otus.levina.hw06.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorDaoImpl implements AuthorDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Author> getById(long id) {
        try {
            return Optional.ofNullable(em.find(Author.class, id));
        } catch (EmptyResultDataAccessException e) {
            log.debug("getById: {} for id={}", e, id);
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    public void delete(Author author) {
        em.remove(author);
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }

}
