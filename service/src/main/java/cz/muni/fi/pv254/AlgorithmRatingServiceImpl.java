package cz.muni.fi.pv254;

import cz.muni.fi.pv254.dao.AlgorithmRatingDao;
import cz.muni.fi.pv254.entity.AlgorithmRating;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.enums.AlgorithmType;
import cz.muni.fi.pv254.exception.PersistenceException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AlgorithmRatingServiceImpl implements AlgorithmRatingService {

    @Inject
    private AlgorithmRatingDao algorithmRatingDao;

    @Override
    public void remove(AlgorithmRating algorithmRating) {
        try {
            algorithmRatingDao.remove(algorithmRating);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public AlgorithmRating add(AlgorithmRating algorithmRating) {
        try {
            return algorithmRatingDao.add(algorithmRating);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public void update(AlgorithmRating algorithmRating) {
        try {
            algorithmRatingDao.update(algorithmRating);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<AlgorithmRating> findAll() {
        try {
            return algorithmRatingDao.findAll();
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public AlgorithmRating findByAuthorAndType(User author, AlgorithmType type) {
        try {
            return algorithmRatingDao.findByAuthorAndType(author, type);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
}
