package cz.fi.muni.pa165.hotelbookingmanagerrest;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.ClientService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
		ClientTO client;
		
		try {
			client = clientService.findClient(id);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		if (client == null) {
			// 404
			throw new WebApplicationException(
					Response.status(Response.Status.NOT_FOUND)
					.entity("Clients with id " + id + " weren't found")
					.build());
		}
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
		List<ClientTO> temp;
		
		try {
			temp = clientService.findClientsByName(name);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		if (temp.isEmpty()) {
			// 404
			throw new WebApplicationException(
					Response.status(Response.Status.NOT_FOUND)
					.entity("Clients with name " + name + " weren't found")
					.build());
		}
		return temp;
	}

	@POST
	@Path("/createClient")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createClient(ClientTO client) {
		try {
			clientService.createClient(client);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		String result = "Client saved : " + client;
		// 200
		return Response.ok(result).build();
	}

	@POST
	@Path("/updateClient")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateClient(ClientTO client) {
		try {
			clientService.updateClient(client);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		String result = "Client updated : " + client;
		// 200
		return Response.ok(result).build();
	}

	@DELETE
	@Path("/deleteClient")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteClient(@QueryParam("id") Long id) {
		ClientTO client;
		
		try {
			client = clientService.findClient(id);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		if (client == null) {
			// 404
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		try {
			clientService.deleteClient(client);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		} catch (DataAccessException ex) {
			// Mainly for DB constraint
			// 417
			throw new WebApplicationException(417);
		}
		
		String result = "Client removed : " + client;
		// 200
		return Response.ok(result).build();
	}
}
