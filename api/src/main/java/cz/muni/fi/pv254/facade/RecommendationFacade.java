package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.dto.GameDTO;
import cz.muni.fi.pv254.dto.RecommendationDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.entity.Recommendation;

import java.util.List;

public interface RecommendationFacade {
    /**
     * Removes recommendation from database
     * @param recommendation to remove
     */
    void remove(RecommendationDTO recommendation);

    /**
     * Adds recommendation to databse
     * @param recommendation to add
     */
    RecommendationDTO add(RecommendationDTO recommendation);

    /**
     * Updates existing recommendation in databse
     * @param recommendation to update
     */
    void update(RecommendationDTO recommendation);

    /**
     * Finds all recommendations
     * @return List of all recommendations
     */
    List<RecommendationDTO> findAll();

    /**
     * Finds recommendation by id
     * @param id of recommendation
     * @return Recommendation with specified id or null if no recommendation is found
     */
    RecommendationDTO findById(Long id);

    /**
     * Find game by steam id
     * @param id steam id
     * @return game
     */
    RecommendationDTO findBySteamId(Long id);

    RecommendationDTO findByAuthorAndGame(UserDTO author, GameDTO game);

    List<RecommendationDTO> findPositive();

    List<RecommendationDTO> findNegative();

    List<RecommendationDTO> findByAuthor(UserDTO author);

    List<RecommendationDTO> findByGame(GameDTO game);
}
