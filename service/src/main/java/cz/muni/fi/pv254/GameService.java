package cz.muni.fi.pv254;

import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {

    void remove(Game game);

    /**
     * Adds game to databse
     * @param game to add
     */
    Game add(Game game);

    /**
     * Updates existing game in databse
     * @param game to update
     */
    void update(Game game);

    /**
     * Finds all games
     * @return List of all games
     */
    List<Game> findAll();

    /**
     * Finds game by id
     * @param id of game
     * @return Game with specified id or null if no game is found
     */
    Game findById(Long id);

    /**
     * Finds game by name
     * @param name of game
     * @return Game with specified name or null if no game is found
     */
    Game findByName(String name);

    /**
     * Find game by steam id
     * @param id steam id
     * @return game
     */
    Game findBySteamId(Long id);

    /**
     * Find games recommended by user
     * @param author
     * @return list of games
     */
    List<Game>findRecommendedByUser(User author);
}
