package cz.fi.muni.pa165.hotelbookingmanagerapi.service;

import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

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
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void createClient(ClientTO client);
    
    /**
     * Returns client with given id.
     * 
     * @param id primary key of requested client.
     * @return client with given id or null if such client doesn't exist.
     * @throws IllegalArgumentException when given id is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
	@PreAuthorize("hasAnyRole('ROLE_USER, ROLE_ADMIN')")
    ClientTO findClient(Long id);
    
    /**
     * Updates existing client.
     * 
     * @param client client to update (specified by id) with new attributes.
     * @throws IllegalArgumentException if client is null or he doesn't exist in database.
     *         if validation for client attributes fails.
     * @throws DataAccessException in case of error on a persistence layer.
     */
	@PreAuthorize("hasAnyRole('ROLE_USER, ROLE_ADMIN')")
    void updateClient(ClientTO client);
    
    /**
     * Removes existing client.
     * 
     * @param client client to remove (specified by id).
     * @throws IllegalArgumentException if parameter is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteClient(ClientTO client);
    
    /**
     * Returns list of all clients.
     * 
     * @return all clients in the DB or empty list if there are none. 
     * @throws DataAccessException in case of error on a persistence layer.
     */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    List<ClientTO> findAllClients();
    
    /**
     * Returns list of clients with given first name.
     * 
     * @param name first name of requested clients
     * @return all clients with given name or empty list if there are none.
     * @throws IllegalArgumentException if parameter is null or empty string.
     * @throws DataAccessException in case of error on a persistence layer.
     */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    List<ClientTO> findClientsByName(String name);
            
}
