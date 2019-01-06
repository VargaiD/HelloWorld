package cz.muni.fi.pv254.dao;

import cz.muni.fi.pv254.entity.AlgorithmRating;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.enums.AlgorithmType;

import java.util.List;

public interface AlgorithmRatingDao {

    /**
     * Removes AlgorithmRating from database
     * @param algorithmRating to remove
     */
    void remove(AlgorithmRating algorithmRating);

    /**
     * Adds AlgorithmRating to database
     * @param algorithmRating to add
     */
    AlgorithmRating add(AlgorithmRating algorithmRating);

    /**
     * Updates existing algorithmRating in database
     * @param algorithmRating to update
     */
    void update(AlgorithmRating algorithmRating);

    /**
     * Finds all algorithmRatings
     * @return List of all algorithmRatings
     */
    List<AlgorithmRating> findAll();

    AlgorithmRating findByAuthorAndType(User author, AlgorithmType type);
}
