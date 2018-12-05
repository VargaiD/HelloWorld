package cz.muni.fi.pv254.dao;

import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Genre;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class GenreDaoImpl implements GenreDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void remove(Genre genre) {
        em.remove(em.contains(genre) ? genre : em.merge(genre));
    }

    @Override
    public Genre add(Genre genre) {
        return em.merge(genre);
    }

    @Override
    public void update(Genre genre) {
        em.merge(genre);
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("SELECT g FROM Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public Genre findById(Long id) {
        Genre genre = em.find(Genre.class, id);
        return genre;
    }

    @Override
    public Genre findByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Cannot search for null name");

        try {
            Genre genre = em.createQuery("SELECT g FROM Genre g where g.name =:name",
                    Genre.class).setParameter("name", name).getSingleResult();
            return genre;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
