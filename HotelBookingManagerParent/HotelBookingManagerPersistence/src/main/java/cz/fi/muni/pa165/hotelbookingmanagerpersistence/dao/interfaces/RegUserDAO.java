
package cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Client;
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
     * @throws DataAccessException in case of error.
     */
    void create(RegUser user);

    /**
     * Returns user with given id.
     *
     * @param id ID of the RegUser to get
     * @throws DataAccessException in case of error.
     */
    RegUser get(Long id);

    /**
     * Updates existing user.
     *
     * @param user RegUser to update
     * @throws DataAccessException in case of error.
     */
    void update(RegUser user);

    /**
     * Removes existing user.
     *
     * @param user RegUser to delete
     * @throws DataAccessException in case of error.
     */
    void delete(RegUser user);
	
	
	/**
     * Returns user of given client.
     * 
     * @param client client of requested user
     * @return user of given client 
     * @throws DataAccessException in case of error.
     */
    RegUser findUserByClient(Client client);

        
    /**
     * Returns user with given username
     * 
     * @param username username of requested user
     * @return user with given name 
     * @throws DataAccessException in case of error.
     */
    RegUser findUserByUsername(String username);
}
