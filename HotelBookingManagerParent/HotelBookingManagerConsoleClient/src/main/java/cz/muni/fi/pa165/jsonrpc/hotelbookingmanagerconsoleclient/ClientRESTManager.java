package cz.muni.fi.pa165.jsonrpc.hotelbookingmanagerconsoleclient;

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

    public ClientTO findClient(Long id) {
        return webResource.path("findClient")
                          .queryParam("id", id.toString())
                          .accept(MediaType.APPLICATION_JSON)
                          .get(ClientTO.class);
    }
    
    public List<ClientTO> findAllClients() {
        List<ClientTO> clients = new ArrayList<>();
        String json = webResource.path("findAllClients")
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            clients = mapper.readValue(json, new TypeReference<List<ClientTO>>() {});    
        } catch (IOException e) {
        }
        return clients;
    }
    
    public List<ClientTO> findClientsByName(String name) {
        List<ClientTO> clients = new ArrayList<>();
        String json = webResource.path("findClientsByName")
                                 .queryParam("name", name)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .get(String.class);
        try {
            clients = mapper.readValue(json, new TypeReference<List<ClientTO>>() {});    
        } catch (IOException e) {
        }
        return clients;
    }
    
    public ClientResponse createClient(ClientTO client) {
        return webResource.path("createClient")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, client);
    }
    
    public ClientResponse updateClient(ClientTO client) {
        return webResource.path("updateClient")
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, client);
    }
    
    public ClientResponse deleteClient(ClientTO client) {
        return webResource.path("deleteClient")
                .queryParam("id", client.getId().toString())
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
    }
}
