/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.RegUser;

/**
 *
 * @author Filip Bogyai
 */
public interface RegUserDAO {
    
    /**
     * Adds new user to the database.
     *
     * @param user RegUser to create
     * @throws IllegalArgumentException if user is null or if Client parameter of user is null or if ID of user is already set
     * @throws ConstraintViolationException if user has any invalid parameter
     * @throws DataAccessException in case of error.
     */
    void create (RegUser user);

    /**
     * Returns user with given id.
     *
     * @param id ID of the RegUser to get
     * @return RegUser with the entered ID
     * @throws IllegalArgumentException if id is null
     * @throws DataAccessException in case of error.
     */
    RegUser get(Long id);

    /**
     * Updates existing user.
     *
     * @param user RegUser to update
     * @throws IllegalArgumentException if user is null
     * @throws DataAccessException in case of error.
     */
    void update (RegUser user);

    /**
     * Removes existing user.
     *
     * @param user RegUser to delete
     * @throws IllegalArgumentException if user is null
     * @throws ConstraintViolationException if user has any invalid parameter
     * @throws DataAccessException in case of error.
     */
    void delete (RegUser user);

        
    /**
     * Returns user with given username
     * 
     * @param username username of requested user
     * @return user with given name 
     * @throws DataAccessException in case of error.
     */
    RegUser findUserByUsername(String username);
}
