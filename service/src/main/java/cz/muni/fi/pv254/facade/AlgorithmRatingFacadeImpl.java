package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.AlgorithmRatingService;
import cz.muni.fi.pv254.MappingService;
import cz.muni.fi.pv254.dto.AlgorithmRatingDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.entity.AlgorithmRating;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.enums.AlgorithmType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class AlgorithmRatingFacadeImpl implements AlgorithmRatingFacade {

    @Inject
    private MappingService mappingService;

    @Inject
    private AlgorithmRatingService algorithmRatingService;

    @Override
    public void remove(AlgorithmRatingDTO algorithmRating) {
        algorithmRatingService.remove(mappingService.mapTo(algorithmRating, AlgorithmRating.class));
    }

    @Override
    public AlgorithmRatingDTO add(AlgorithmRatingDTO algorithmRating) {
        return mappingService.mapTo(
                algorithmRatingService.add(mappingService.mapTo(algorithmRating, AlgorithmRating.class)),
                AlgorithmRatingDTO.class);
    }

    @Override
    public void update(AlgorithmRatingDTO algorithmRating) {
        algorithmRatingService.update(mappingService.mapTo(algorithmRating, AlgorithmRating.class));
    }

    @Override
    public List<AlgorithmRatingDTO> findAll() {
        return mappingService.mapTo(algorithmRatingService.findAll(), AlgorithmRatingDTO.class);
    }

    @Override
    public AlgorithmRatingDTO findByAuthorAndType(UserDTO author, AlgorithmType type) {
        return mappingService.mapTo(
                algorithmRatingService.findByAuthorAndType(
                        mappingService.mapTo(author, User.class),
                        type),
                AlgorithmRatingDTO.class);
    }
}
