/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv254.dao;

import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Recommendation;
import cz.muni.fi.pv254.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class RecommendationDaoImpl implements RecommendationDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void remove(Recommendation recommendation) {
        em.remove(em.contains(recommendation) ? recommendation : em.merge(recommendation));
    }

    @Override
    public Recommendation add(Recommendation recommendation) {
        return em.merge(recommendation);
    }

    @Override
    public void update(Recommendation recommendation) {
        em.merge(recommendation);
    }

    @Override
    public List<Recommendation> findAll() {
        return em.createQuery("SELECT rec FROM Recommendation rec", Recommendation.class)
                .getResultList();
    }

    @Override
    public Recommendation findById(Long id) {
        return em.find(Recommendation.class, id);
    }

    @Override
    public Recommendation findBySteamId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot search for steam id null");
        }
        try {
            return em.createQuery("Select recommendation From Recommendation recommendation Where recommendation.steamId = :id",
                    Recommendation.class).setParameter("id", id).getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Recommendation findByAuthorAndGame(User author, Game game) {
        if (author == null || author.getId() == null || game == null || game.getId() == null)
            throw new IllegalArgumentException("Author or game is null");

        try {
            return em.createQuery("SELECT recommendation from Recommendation  recommendation WHERE" +
                    " recommendation.game.id = :gameid AND recommendation.author.id = :authorid", Recommendation.class)
                    .setParameter("gameid", game.getId()).setParameter("authorid", author.getId()).getSingleResult();
        }
        catch (NoResultException ex){
            return null;
        }
    }

    @Override
    public List<Recommendation> findPositive() {
        return em.createQuery("SELECT rec FROM Recommendation rec WHERE rec.votedUp = true", Recommendation.class)
                .getResultList();
    }

    @Override
    public List<Recommendation> findNegative() {
        return em.createQuery("SELECT rec FROM Recommendation rec WHERE rec.votedUp = false", Recommendation.class)
                .getResultList();
    }

}
