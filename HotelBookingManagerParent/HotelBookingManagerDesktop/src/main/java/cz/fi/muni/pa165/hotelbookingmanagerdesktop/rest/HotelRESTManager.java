package cz.fi.muni.pa165.hotelbookingmanagerdesktop.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
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
public class HotelRESTManager {
    
    private String url;
    private Client client;
    private ObjectMapper mapper = new ObjectMapper();
    private WebResource webResource;
    
    public HotelRESTManager() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        url = ServerURIHelper.loadURLForHotel();
        client = Client.create(clientConfig);
        webResource = client.resource(url);
        webResource.addFilter(new HTTPBasicAuthFilter("rest", "rest"));
    }

	/**
	 * Returns hotel with given id.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 400 Bad request				Not applicable for now, cannot send null id to server, because IllegalArgumentException is thrown.
	 * 404 Not found				If hotel with given id was not found.
	 * 500 Internal server error	Other error.
	 * 
	 * @param id
	 * @return hotel with given id.
	 * @throws IllegalArgumentException if id is null.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
    public HotelTO findHotel(Long id) {
        return webResource.path("findHotel")
                          .queryParam("id", id.toString())
                          .accept(MediaType.APPLICATION_JSON)
                          .get(HotelTO.class);
    }
    
	/**
	 * Returns all hotels.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 500 Internal server error	In case of any error on service layer.
	 * 
	 * @return all hotels.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
    public List<HotelTO> findAllHotels() {
        List<HotelTO> hotels = new ArrayList<>();
        String json = webResource.path("findAllHotels")
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            hotels = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
			Logger.getLogger(HotelRESTManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return hotels;
    }
    
	/**
	 * Returns hotel with given name.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 400 Bad request				If name is null or empty string.
	 * 404 Not found				If hotel with given name was not found.
	 * 500 Internal server error	Other error.
	 * 
	 * @param name
	 * @return hotel with given name.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
    public List<HotelTO> findHotelsByName(String name) {
        List<HotelTO> hotels = new ArrayList<>();
        String json = webResource.path("findHotelsByName")
                                 .queryParam("name", name)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            hotels = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
			Logger.getLogger(HotelRESTManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return hotels;
    }
    
	/**
	 * Returns hotels with given address.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 400 Bad request				If address is null or empty string.
	 * 404 Not found				If hotels with given address were not found.
	 * 500 Internal server error	Other error.
	 * 
	 * @param name
	 * @return hotels with given address.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
    public List<HotelTO> findHotelsByAddress(String address) {
        List<HotelTO> hotels = new ArrayList<>();
        String json = webResource.path("findHotelsByAddress")
                                 .queryParam("address", address)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            hotels = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
			Logger.getLogger(HotelRESTManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return hotels;
    }
    
	/**
	 * Returns hotels in given city.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 400 Bad request				If city is null or empty string.
	 * 404 Not found				If hotels in given city were not found.
	 * 500 Internal server error	Other error.
	 * 
	 * @param name
	 * @return hotels in given city.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
    public List<HotelTO> findHotelsByCity(String city) {
        List<HotelTO> hotels = new ArrayList<>();
        String json = webResource.path("findHotelsByCity")
                                 .queryParam("city", city)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            hotels = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
			Logger.getLogger(HotelRESTManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return hotels;
    }
    
	/**
	 * Returns hotels in given country.
	 * Error status codes (wrapped in UniformInterfaceException) are:
	 * 400 Bad request				If country is null or empty string.
	 * 404 Not found				If hotels in given country were not found.
	 * 500 Internal server error	Other error.
	 * 
	 * @param name
	 * @return hotels in given country.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 * @throws UniformInterfaceException when the status code of the HTTP response indicates a response that is not expected.
	 */
    public List<HotelTO> findHotelsByCountry(String country) {
        List<HotelTO> hotels = new ArrayList<>();
        String json = webResource.path("findHotelsByCountry")
                                 .queryParam("country", country)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            hotels = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
			Logger.getLogger(HotelRESTManager.class.getName()).log(Level.SEVERE, null, e);
        }
        return hotels;
    }
    
	/**
	 * Creates hotel.
	 * Error status codes (wrapped in server response) are:
	 * 400 Bad request				In case of invalid hotel.
	 * 500 Internal server error	Other error.
	 * 
	 * @param hotel
	 * @return response from server.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 */
    public ClientResponse createHotel(HotelTO hotel) {
        return webResource.path("createHotel")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, hotel);
    }
    
	/**
	 * Updates hotel.
	 * Error status codes (wrapped in server response) are:
	 * 400 Bad request				In case of invalid hotel.
	 * 500 Internal server error	Other error.
	 * 
	 * @param hotel
	 * @return response from server.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 */
    public ClientResponse updateHotel(HotelTO hotel) {
        return webResource.path("updateHotel")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, hotel);
    }
    
	/**
	 * Deletes hotel.
	 * Error status codes (wrapped in server response) are:
	 * 400 Bad request				If hotel is invalid.
	 * 404 Not found				If hotel was not found.
	 * 417 Expectation Failed		In case of DB error, mainly DB constraint.
	 * 500 Internal server error	Other error.
	 * 
	 * @param hotel
	 * @return response from server.
	 * @throws IllegalArgumentException if hotel is null or has null id.
	 * @throws ClientHandlerException if signals a failure to process the HTTP request or HTTP response.
	 */
    public ClientResponse deleteHotel(HotelTO hotel) {
        return webResource.path("deleteHotel")
                .queryParam("id", hotel.getId().toString())
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
    }
}
