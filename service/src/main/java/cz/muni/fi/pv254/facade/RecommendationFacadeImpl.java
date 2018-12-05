package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.MappingService;
import cz.muni.fi.pv254.RecommendationService;
import cz.muni.fi.pv254.dto.GameDTO;
import cz.muni.fi.pv254.dto.RecommendationDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.entity.Game;
import cz.muni.fi.pv254.entity.Recommendation;
import cz.muni.fi.pv254.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class RecommendationFacadeImpl implements RecommendationFacade {

    @Inject
    private MappingService mappingService;

    @Inject
    private RecommendationService recommendationService;

    @Override
    public void remove(RecommendationDTO recommendation) {
        recommendationService.remove(mappingService.mapTo(recommendation, Recommendation.class));
    }

    @Override
    public RecommendationDTO add(RecommendationDTO recommendation) {
        Recommendation rec = recommendationService.add(mappingService.mapTo(recommendation, Recommendation.class));
        return mappingService.mapTo(rec,RecommendationDTO.class);
    }

    @Override
    public void update(RecommendationDTO recommendation) {
        recommendationService.update(mappingService.mapTo(recommendation, Recommendation.class));
    }

    @Override
    public List<RecommendationDTO> findAll() {
        return mappingService.mapTo(recommendationService.findAll(), RecommendationDTO.class);
    }

    @Override
    public RecommendationDTO findById(Long id) {
        Recommendation recommendation = recommendationService.findById(id);
        if (recommendation == null) {
            return null;
        }
        return mappingService.mapTo(recommendation, RecommendationDTO.class);
    }

    @Override
    public RecommendationDTO findBySteamId(Long id) {
        Recommendation recommendation = recommendationService.findBySteamId(id);
        if (recommendation == null) {
            return null;
        }
        return mappingService.mapTo(recommendation, RecommendationDTO.class);
    }

    @Override
    public RecommendationDTO findByAuthorAndGame(UserDTO author, GameDTO game) {
        Recommendation recommendation = recommendationService.findByAuthorAndGame(
                mappingService.mapTo(author, User.class),
                mappingService.mapTo(game, Game.class));
        if (recommendation == null)
            return null;

        return mappingService.mapTo(recommendation, RecommendationDTO.class);
    }

    @Override
    public List<RecommendationDTO> findPositive() {
        return mappingService.mapTo(recommendationService.findPositive(), RecommendationDTO.class);
    }

    @Override
    public List<RecommendationDTO> findNegative() {
        return mappingService.mapTo(recommendationService.findNegative(), RecommendationDTO.class);
    }

    @Override
    public List<RecommendationDTO> findByAuthor(UserDTO author) {
        List<Recommendation> recommendation = recommendationService.findByAuthor(
                mappingService.mapTo(author, User.class));
        if (recommendation == null)
            return null;

        return mappingService.mapTo(recommendation, RecommendationDTO.class);
    }

    @Override
    public List<RecommendationDTO> findByGame(GameDTO game) {
        List<Recommendation> recommendation = recommendationService.findByGame(
                mappingService.mapTo(game, Game.class));
        if (recommendation == null)
            return null;

        return mappingService.mapTo(recommendation, RecommendationDTO.class);
    }
}
