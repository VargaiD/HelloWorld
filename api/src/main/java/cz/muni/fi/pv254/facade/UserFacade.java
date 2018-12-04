package cz.muni.fi.pv254.facade;

import cz.muni.fi.pv254.dto.UserAuthenticateDTO;
import cz.muni.fi.pv254.dto.UserDTO;

import java.util.List;

public interface UserFacade {
    /**
     * Add a new user
     * @param user to be added
     * @param password of the user (not hashed)
     */
    UserDTO add(UserDTO user, String password);

    /**
     * Remove user
     * @param user to be removed
     */
    void remove(UserDTO user);

    /**
     * Update user
     * @param user to be updated
     */
    void update(UserDTO user);

    /**
     * Find all users
     * @return list of all users
     */
    List<UserDTO> findAll();

    /**
     * Find user by id
     * @param id of user
     * @return user with given id or null if no such user found
     */
    UserDTO findById(Long id);

    /**
     * Find user by email
     * @param email of user
     * @return user with given email or null if no such user found
     */
    UserDTO findByEmail(String email);

    /**
     * Check if given user is admin
     * @param user to be checked
     * @return true if user is admin, false otherwise
     */
    Boolean isAdmin(UserDTO user);

    /**
     * Authenticate user
     * @param user to be authenticated
     * @return UserDTO if user is successfully authenticated, null otherwise
     */
    UserDTO authenticate(UserAuthenticateDTO user);

    /**
     * Finds user by steam id
     * @param id steam id
     * @return author
     */
    UserDTO findBySteamId(Long id);

    /**
     * Finds user by steam id
     * @param id steam id
     * @return author
     */
    UserDTO findBySteamId(Long id, boolean populateRecommendations);
}
