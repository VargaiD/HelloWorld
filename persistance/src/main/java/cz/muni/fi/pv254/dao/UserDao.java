package cz.muni.fi.pv254.dao;

import cz.muni.fi.pv254.entity.User;

import java.util.List;

public interface UserDao {

    /**
     * Removes user from the database
     *
     * @param user to be removed
     */
    void remove(User user);

    /**
     * Adds user to the database
     *
     * @param user to add
     */
    User add(User user);

    /**
     * Updates existing user in the database
     * @param user user with changed properties
     */
    void update(User user);

    /**
     * Returns all users from the database
     * @return List of all users in the database
     */
    List<User> findAll();

    /**
     * Finds particular user specified by parameter id
     * @param id id number of user
     * @return User with id equal to parameter id, or null if no such user found
     */
    User findById(Long id);

    /**
     * Finds particular user specified by email
     * @param email email of user
     * @return User with email equal to parameter email, or null if no such user found
     */
    User findByEmail(String email);

    /**
     * Find user by steam id
     * @param id steam id
     * @return user
     */
    User findBySteamId(Long id);

    User findBySteamId(Long id, boolean populateRecommendations);
}
