package cz.fi.muni.pa165.hotelbookingmanagerdesktop.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Andrej Galád
 */
public class ClientRESTManager {
    
    private String url;
    private Client client;
    private ObjectMapper mapper = new ObjectMapper();
    private WebResource webResource;
    
    public ClientRESTManager() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        url = ServerURIHelper.loadURLForClient();
        client = Client.create(clientConfig);
        webResource = client.resource(url);
    }

    /**
	 * Returns client with given id.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 400 Bad request				Not applicable for now, cannot send null id to server, because IllegalArgumentException is thrown.
	 * 404 Not found				If client with given id was not found.
	 * 500 Internal server error	Other error.
	 * 
	 * @param id
	 * @return client with given id.
	 * @throws IllegalArgumentException if id is null.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
	public ClientTO findClient(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Id cannot be null.");
		}
        return webResource.path("findClient")
                          .queryParam("id", id.toString())
                          .accept(MediaType.APPLICATION_JSON)
                          .get(ClientTO.class);
    }
    
	/**
	 * Returns all clients.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 500 Internal server error	In case of any error on service layer.
	 * 
	 * @return all clients.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
    public List<ClientTO> findAllClients() {
        List<ClientTO> clients = new ArrayList<>();
        String json = webResource.path("findAllClients")
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            clients = mapper.readValue(json, new TypeReference<List<ClientTO>>() {});    
        } catch (IOException e) {
			Logger.getLogger(ClientRESTManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return clients;
    }
    
	/**
	 * Returns client with given name.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 400 Bad request				If name is null or empty string.
	 * 404 Not found				If client with given name was not found.
	 * 500 Internal server error	Other error.
	 * 
	 * @param name
	 * @return client with given name.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
    public List<ClientTO> findClientsByName(String name) {
        List<ClientTO> clients = new ArrayList<>();
        String json = webResource.path("findClientsByName")
                                 .queryParam("name", name)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            clients = mapper.readValue(json, new TypeReference<List<ClientTO>>() {});    
        } catch (IOException e) {
			Logger.getLogger(ClientRESTManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return clients;
    }
    
	/**
	 * Creates client.
	 * Error status codes (wrapped in server response) are:
	 * 400 Bad request				In case of invalid client.
	 * 500 Internal server error	Other error.
	 * 
	 * @param client
	 * @return response from server.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 */
    public ClientResponse createClient(ClientTO client) {
        return webResource.path("createClient")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, client);
    }
    
	/**
	 * Updates client.
	 * Error status codes (wrapped in server response) are:
	 * 400 Bad request				In case of invalid client.
	 * 500 Internal server error	Other error.
	 * 
	 * @param client
	 * @return response from server.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 */
    public ClientResponse updateClient(ClientTO client) {
        return webResource.path("updateClient")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, client);
    }
    
	/**
	 * Deletes client.
	 * Error status codes (wrapped in server response) are:
	 * 400 Bad request				If client is invalid.
	 * 404 Not found				If client was not found.
	 * 417 Expectation Failed		In case of DB error, mainly DB constraint.
	 * 500 Internal server error	Other error.
	 * 
	 * @param client
	 * @return response from server.
	 * @throws IllegalArgumentException if client is null or has null id.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 */
    public ClientResponse deleteClient(ClientTO client) {
		if (client == null) {
			throw new IllegalArgumentException("Client cannot be null.");
		}
		if (client.getId() == null) {
			throw new IllegalArgumentException("Client id cannot be null.");
		}
        return webResource.path("deleteClient")
                .queryParam("id", client.getId().toString())
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
    }
}
