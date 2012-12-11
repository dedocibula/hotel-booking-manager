package cz.fi.muni.pa165.hotelbookingmanagerdesktop.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    }

    public HotelTO findHotel(Long id) {
        return webResource.path("findHotel")
                          .queryParam("id", id.toString())
                          .accept(MediaType.APPLICATION_JSON)
                          .get(HotelTO.class);
    }
    
    public List<HotelTO> findAllHotels() {
        List<HotelTO> hotels = new ArrayList<>();
        String json = webResource.path("findAllHotels")
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            hotels = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
        }
        return hotels;
    }
    
    public List<HotelTO> findHotelsByName(String name) {
        List<HotelTO> clients = new ArrayList<>();
        String json = webResource.path("findHotelsByName")
                                 .queryParam("name", name)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            clients = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
        }
        return clients;
    }
    
    public List<HotelTO> findHotelsByAddress(String address) {
        List<HotelTO> clients = new ArrayList<>();
        String json = webResource.path("findHotelsByAddress")
                                 .queryParam("address", address)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            clients = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
        }
        return clients;
    }
    
    public List<HotelTO> findHotelsByCity(String city) {
        List<HotelTO> clients = new ArrayList<>();
        String json = webResource.path("findHotelsByCity")
                                 .queryParam("city", city)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            clients = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
        }
        return clients;
    }
    
    public List<HotelTO> findHotelsByCountry(String country) {
        List<HotelTO> clients = new ArrayList<>();
        String json = webResource.path("findHotelsByCountry")
                                 .queryParam("country", country)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            clients = mapper.readValue(json, new TypeReference<List<HotelTO>>() {});    
        } catch (IOException e) {
        }
        return clients;
    }
    
    public ClientResponse createHotel(HotelTO hotel) {
        return webResource.path("createHotel")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, hotel);
    }
    
    public ClientResponse updateHotel(HotelTO hotel) {
        return webResource.path("updateHotel")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, hotel);
    }
    
    public ClientResponse deleteHotel(HotelTO hotel) {
        return webResource.path("deleteHotel")
                .queryParam("id", hotel.getId().toString())
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
    }
}
