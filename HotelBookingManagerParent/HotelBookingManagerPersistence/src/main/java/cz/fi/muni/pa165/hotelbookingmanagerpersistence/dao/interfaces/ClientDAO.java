package cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Client;
import java.util.List;

/**
 *
 * @author Filip Bogyai
 */
public interface ClientDAO {
    
    /**
     * Adds new client to the database.
     * 
     * @param client client to add.
     * @throws DataAccessException in case of error.
     */
    void create(Client client);
    
    /**
     * Returns client with given id.
     * 
     * @param id primary key of requested client.
     * @return client with given id or null if such client doesn't exist.
     * @throws DataAccessException in case of error.
     */
    Client get(Long id);
    
    /**
     * Updates existing client.
     * 
     * @param client client to update (specified by id) with new attributes.
     * @throws DataAccessException in case of error.
     */
    void update(Client client);
    
    /**
     * Removes existing client.
     * 
     * @param client client to remove (specified by id).
     * @throws DataAccessException in case of error.
     */
    void delete(Client client);
    
    /**
     * Returns list of all clients in the database.
     * 
     * @return all clients in the DB or empty list if there are none.
     * @throws DataAccessException in case of error.
     */
    List<Client> findAll();
    
    /**
     * Returns list of clients with given name
     * 
     * @param name name of requested clients
     * @return all clients with given name or empty list if there are none.
     * @throws DataAccessException in case of error.
     */
    List<Client> findClientsByName(String name);
}
