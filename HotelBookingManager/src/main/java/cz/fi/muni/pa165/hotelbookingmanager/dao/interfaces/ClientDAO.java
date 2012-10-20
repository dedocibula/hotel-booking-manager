package cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces;

import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
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
     * @throws IllegalArgumentException if parameter is null or has assigned id.
     */
    void create(Client client);
    
    /**
     * Returns client with given id.
     * 
     * @param id primary key of requested client.
     * @return client with given id or null if such client doesn't exist.
     * @throws IllegalArgumentException when given id is null.
     */
    Client get(Long id);
    
    /**
     * Updates existing client.
     * 
     * @param client client to update (specified by id) with new attributes.
     * @throws IllegalArgumentException if parameter is null.
     */
    void update(Client client);
    
    /**
     * Removes existing client.
     * 
     * @param client client to remove (specified by id).
     * @throws IllegalArgumentException if parameter is null.
     */
    void delete(Client client);
    
    /**
     * Returns list of all clients in the database.
     * 
     * @return all clients in the DB or empty list if there are none.
     */
    List<Client> findAll();
}
