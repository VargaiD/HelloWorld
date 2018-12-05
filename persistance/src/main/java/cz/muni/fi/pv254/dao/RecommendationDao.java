/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv254.dao;

import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Recommendation;
import cz.muni.fi.pv254.entity.User;

import java.util.List;

public interface RecommendationDao {
    
    /**
     * Removes recommendation from database
     * @param recommendation to remove
     */
    void remove(Recommendation recommendation);
    
    /**
     * Adds recommendation to databse
     * @param recommendation to add
     */
    Recommendation add(Recommendation recommendation);
    
    /**
     * Updates existing recommendation in databse
     * @param recommendation to update
     */
    void update(Recommendation recommendation);
    
    /**
     * Finds all recommendations
     * @return List of all recommendations
     */
    List<Recommendation> findAll();
    
    /**
     * Finds recommendation by id
     * @param id of recommendation
     * @return Recommendation with specified id or null if no recommendation is found
     */
    Recommendation findById(Long id);

    /**
     * Find recomendation by steam id
     * @param id steam id
     * @return recomendation
     */
    Recommendation findBySteamId(Long id);

    Recommendation findByAuthorAndGame(User author, Game game);

    List<Recommendation> findPositive();

    List<Recommendation> findNegative();

    List<Recommendation> findByAuthor(User author);

    List<Recommendation> findByGame(Game game);
}
