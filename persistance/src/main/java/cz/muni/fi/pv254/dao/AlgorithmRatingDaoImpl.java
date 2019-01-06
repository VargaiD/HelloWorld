package cz.muni.fi.pv254.dao;

import cz.muni.fi.pv254.entity.AlgorithmRating;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.enums.AlgorithmType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class AlgorithmRatingDaoImpl implements AlgorithmRatingDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void remove(AlgorithmRating algorithmRating) {
        em.remove(em.contains(algorithmRating) ? algorithmRating : em.merge(algorithmRating));
    }

    @Override
    public AlgorithmRating add(AlgorithmRating algorithmRating) {
        return em.merge(algorithmRating);
    }

    @Override
    public void update(AlgorithmRating algorithmRating) {
        em.merge(algorithmRating);
    }

    @Override
    public List<AlgorithmRating> findAll() {
        return em.createQuery("SELECT rating FROM AlgorithmRating rating", AlgorithmRating.class).getResultList();
    }

    @Override
    public AlgorithmRating findByAuthorAndType(User author, AlgorithmType type) {
        try {
            return em.createQuery("SELECT rating FROM AlgorithmRating rating WHERE rating.algorithmType = :algoType " +
                    "AND rating.author.id= :authorId", AlgorithmRating.class)
                    .setParameter("algoType", type)
                    .setParameter("authorId", author.getId())
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
