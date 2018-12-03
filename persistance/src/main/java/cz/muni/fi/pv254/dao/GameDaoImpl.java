/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv254.dao;

import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Genre;
import cz.muni.fi.pv254.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class GameDaoImpl implements GameDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void remove(Game game) {
        em.remove(em.contains(game) ? game : em.merge(game));
    }

    @Override
    public Game add(Game game) {
        return em.merge(game);
    }

    @Override
    public void update(Game game) {
        em.merge(game);
    }

    @Override
    public List<Game> findAll() {
        return em.createQuery("SELECT game FROM Game game", Game.class)
                .getResultList();
    }

    @Override
    public Game findById(Long id) {
        Game game = em.find(Game.class, id);
        PopulateGenres(game);
        return game;
    }

    @Override
    public Game findByName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Cannot search for null name");

        try {
            Game game = em.createQuery("SELECT game FROM Game game WHERE game.name =:name",
                        Game.class).setParameter("name", name).getSingleResult();
            PopulateGenres(game);
            return game;
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Game findBySteamId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot search for steam id null");
        }
        try {
            Game game = em.createQuery("Select game From Game game Where game.steamId = :id",
                    Game.class).setParameter("id", id).getSingleResult();
            PopulateGenres(game);
            return game;
        }
        catch (NoResultException e) {
            return null;
        }
    }

    public List<Game>findRecommendedByUser(User author){
        if (author == null || author.getId() == null)
            throw new IllegalArgumentException("Cannot search for null user");

        try {
            return em.createQuery("SELECT game from Game game INNER JOIN" +
                    " Recommendation recommendation on game.id = recommendation.game.id where recommendation.author.id= :author", Game.class)
                    .setParameter("author", author.getId()).getResultList();
        }
        catch (NoResultException e){
            return null;
        }
    }


    private void PopulateGenres(Game game){
        Set<Genre> genres =
                new HashSet<>(
                        em.createQuery(
                                "SELECT genre FROM Genre genre INNER JOIN genre.games game WHERE game.id= :id", Genre.class)
                                .setParameter("id", game.getId()).getResultList());

        game.setGenres(genres);
    }
    
}
