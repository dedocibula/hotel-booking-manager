package cz.fi.muni.pa165.hotelbookingmanagerapi.service;

import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RegUserTO;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Marian Rusnak
 */
public interface RegUserService extends UserDetailsService {
	
	/**
     * Creates new user.
     *
     * @param user RegUser to create
     * @throws IllegalArgumentException if user is null or if Client parameter of user is null or if ID of user is already set
     * @throws ConstraintViolationException if user has any invalid parameter
     * @throws DataAccessException in case of error.
     */
    void create (RegUserTO userTO);

    /**
     * Returns user with given id.
     *
     * @param id ID of the RegUser to get
     * @return RegUser with the entered ID
     * @throws IllegalArgumentException if id is null
     * @throws DataAccessException in case of error.
     */
    RegUserTO get(Long id);

    /**
     * Updates existing user.
     *
     * @param user RegUser to update
     * @throws IllegalArgumentException if user is null
     * @throws DataAccessException in case of error.
     */
    void update(RegUserTO userTO);

    /**
     * Removes existing user.
     *
     * @param user RegUser to delete
     * @throws IllegalArgumentException if user is null
     * @throws ConstraintViolationException if user has any invalid parameter
     * @throws DataAccessException in case of error.
     */
    void delete(RegUserTO userTO);

        
    /**
     * Returns user with given username
     * 
     * @param username username of requested user
     * @return user with given name 
	 * @throws IllegalArgumentException if username is null or empty string.
     * @throws DataAccessException in case of error.
     */
    RegUserTO findUserByUsername(String username);
}
