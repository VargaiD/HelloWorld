package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.dto.GameDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.entity.Game;

import java.util.List;

public interface GameFacade {

    void remove(GameDTO game);

    /**
     * Adds game to database
     * @param game to add
     */
    GameDTO add(GameDTO game);

    /**
     * Updates existing game in database
     * @param game to update
     */
    void update(GameDTO game);

    /**
     * Finds all games
     * @return List of all games
     */
    List<GameDTO> findAll();

    /**
     * Finds game by id
     * @param id of game
     * @return Game with specified id or null if no game is found
     */
    GameDTO findById(Long id);

    /**
     * Finds game by name
     * @param name of game
     * @return Game with specified name or null if no game is found
     */
    GameDTO findByName(String name);

    /**
     * Find game by steam id
     * @param id steam id
     * @return game
     */
    GameDTO findBySteamId(Long id);

    /**
     * Find games recommended by user
     * @param author
     * @return list of games
     */
    List<GameDTO>findRecommendedByUser(UserDTO author);

    Long countGames();
}
