package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.dto.AlgorithmRatingDTO;
import cz.muni.fi.pv254.dto.UserDTO;
import cz.muni.fi.pv254.enums.AlgorithmType;

import java.util.List;

public interface AlgorithmRatingFacade {


    /**
     * Removes AlgorithmRating from database
     * @param algorithmRating to remove
     */
    void remove(AlgorithmRatingDTO algorithmRating);

    /**
     * Adds AlgorithmRating to database
     * @param algorithmRating to add
     */
    AlgorithmRatingDTO add(AlgorithmRatingDTO algorithmRating);

    /**
     * Updates existing algorithmRating in database
     * @param algorithmRating to update
     */
    void update(AlgorithmRatingDTO algorithmRating);

    /**
     * Finds all algorithmRatings
     * @return List of all algorithmRatings
     */
    List<AlgorithmRatingDTO> findAll();

    AlgorithmRatingDTO findByAuthorAndType(UserDTO author, AlgorithmType type);
}
