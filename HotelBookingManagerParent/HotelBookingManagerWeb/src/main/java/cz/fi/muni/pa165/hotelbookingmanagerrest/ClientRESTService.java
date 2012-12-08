package cz.fi.muni.pa165.hotelbookingmanagerrest;

import com.sun.jersey.api.Responses;
import cz.fi.muni.pa165.hotelbookingmanagerapi.service.ClientService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Andrej Galád
 */
@Component
@Path("/clients")
public class ClientRESTService {
    
    @Autowired
    private ClientService clientService;
    
    @GET
    @Path("/findClient")
    @Produces(MediaType.APPLICATION_JSON)
    public ClientTO getClientInJSON(@QueryParam("id") Long id) {
        ClientTO client = clientService.findClient(id);
        if (client == null)
            throw new WebApplicationException(
                    Response.status(Responses.NOT_FOUND)
                            .entity("Clients with id " + id + " weren't found")
                            .build());
        return client;
    }
    
    @GET
    @Path("/findAllClients")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientTO> getAllClientsInJSON() {
        return clientService.findAllClients();
    }
    
    @GET
    @Path("/findClientsByName")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClientTO> getCliensByNameInJSON(@QueryParam("name") String name) {
        List<ClientTO> temp = clientService.findClientsByName(name);
        if (temp.isEmpty())
            throw new WebApplicationException(
                    Response.status(Responses.NOT_FOUND)
                            .entity("Clients with name " + name + " weren't found")
                            .build());
        return temp;
    }

    @POST
    @Path("/createClient")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createClient(ClientTO client) {
        clientService.createClient(client);
        String result = "Client saved : " + client;
        return Response.ok(result).build();
    }
    
    @POST
    @Path("/updateClient")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClient(ClientTO client) {
        clientService.updateClient(client);
        String result = "Client updated : " + client;
        return Response.ok(result).build();
    }
    
    @DELETE
    @Path("/deleteClient")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteClient(@QueryParam("id") Long id) {
        ClientTO client = clientService.findClient(id);
        clientService.deleteClient(client);
        String result = "Client removed : " + client;
        return Response.ok(result).build();
    }
}
