package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.GameService;
import cz.muni.fi.pv254.MappingService;
import cz.muni.fi.pv254.dto.GameDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class GameFacadeImpl implements GameFacade {

    @Inject
    private MappingService mappingService;

    @Inject
    private GameService gameService;

    @Override
    public void remove(GameDTO game) {
        gameService.remove(mappingService.mapTo(game, Game.class));
    }

    @Override
    public GameDTO add(GameDTO game) {

        Game g = gameService.add(mappingService.mapTo(game, Game.class));
        return mappingService.mapTo(g, GameDTO.class);

    }

    @Override
    public void update(GameDTO game) {
        gameService.update(mappingService.mapTo(game, Game.class));
    }

    @Override
    public List<GameDTO> findAll() {
        return mappingService.mapTo(gameService.findAll(), GameDTO.class);
    }

    @Override
    public GameDTO findById(Long id) {
        Game game = gameService.findById(id);
        if (game == null) {
            return null;
        }
        return mappingService.mapTo(game, GameDTO.class);
    }

    @Override
    public GameDTO findByName(String name) {
        Game game = gameService.findByName(name);
        if (game == null) {
            return null;
        }
        return mappingService.mapTo(game, GameDTO.class);
    }

    @Override
    public GameDTO findBySteamId(Long id) {
        Game game = gameService.findBySteamId(id);
        if (game == null) {
            return null;
        }
        return mappingService.mapTo(game,GameDTO.class);
    }

    @Override
    public List<GameDTO> findRecommendedByUser(UserDTO author) {
        return mappingService.mapTo(gameService.findRecommendedByUser(mappingService.mapTo(author, User.class)), GameDTO.class);
    }

    @Override
    public Long countGames() {
        return gameService.countGames();
    }
}
