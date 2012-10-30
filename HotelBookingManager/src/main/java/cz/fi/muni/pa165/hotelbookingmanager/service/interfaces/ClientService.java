/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanager.service.interfaces;


import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import java.util.List;

/**
 *
 * @author Filip Bogyai
 */
public interface ClientService {
    
    /**
     * Creates new client.
     * 
     * @param client client to create.
     * @throws IllegalArgumentException if client is null or has assigned id.
     *         if validation for client attributes fails.
     */
    void createClient(Client client);
    
    /**
     * Returns client with given id.
     * 
     * @param id primary key of requested client.
     * @return client with given id or null if such client doesn't exist.
     * @throws IllegalArgumentException when given id is null.
     */
    Client findClient(Long id);
    
    /**
     * Updates existing client.
     * 
     * @param client client to update (specified by id) with new attributes.
     * @throws IllegalArgumentException if client is null or he doesn't exist in database.
     *         if validation for client attributes fails.
     */
    void updateClient(Client client);
    
    /**
     * Removes existing client.
     * 
     * @param client client to remove (specified by id).
     * @throws IllegalArgumentException if parameter is null.
     */
    void deleteClient(Client client);
    
    /**
     * Returns list of all clients.
     * 
     * @return all clients in the DB or empty list if there are none. 
     */
    List<Client> findAllClients();
    
    /**
     * Returns list of clients with given first name.
     * 
     * @param name first name of requested clients
     * @return all clients with given name or empty list if there are none.
     * @throws IllegalArgumentException if parameter is null or empty string.
     */
    List<Client> findClientsByName(String name);
            
}
