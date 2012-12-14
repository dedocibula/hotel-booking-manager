package cz.fi.muni.pa165.hotelbookingmanagerrest;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.HotelService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
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
@Path("/hotels")
public class HotelRESTService {
    
    @Autowired
    private HotelService hotelService;
    
    @GET
    @Path("/findHotel")
    @Produces(MediaType.APPLICATION_JSON)
    public HotelTO getHotelInJSON(@QueryParam("id") Long id) {
		HotelTO hotel;
		
		try {
			hotel = hotelService.findHotel(id);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		if (hotel == null) {
			// 404
			throw new WebApplicationException(
					Response.status(Response.Status.NOT_FOUND)
					.entity("Hotel with id " + id + " wasn't found")
					.build());
		}
		return hotel;
    }
    
    @GET
    @Path("/findAllHotels")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelTO> getAllHotelsInJSON() {
        return hotelService.findAllHotels();
    }
    
    @GET
    @Path("/findHotelsByName")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelTO> getHotelsByNameInJSON(@QueryParam("name") String name) {
        List<HotelTO> temp;
		
		try {
			temp = hotelService.findHotelsByName(name);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		if (temp.isEmpty()) {
			// 404
			throw new WebApplicationException(
					Response.status(Response.Status.NOT_FOUND)
					.entity("Hotels with name " + name + " weren't found")
					.build());
		}
		return temp;
    }
    
    @POST
    @Path("/createHotel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHotel(HotelTO hotel) {
		try {
			hotelService.createHotel(hotel);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		String result = "Hotel saved : " + hotel;
		// 200
		return Response.ok(result).build();
    }
    
    @POST
    @Path("/updateHotel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHotel(HotelTO hotel) {
		try {
			hotelService.updateHotel(hotel);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		String result = "Hotel updated : " + hotel;
		// 200
		return Response.ok(result).build();
    }
    
    @DELETE
    @Path("/deleteHotel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteHotel(@QueryParam("id") Long id) {
		HotelTO hotel;
		
		try {
			hotel = hotelService.findHotel(id);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		
		if (hotel == null) {
			// 404
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		try {
			hotelService.deleteHotel(hotel);
		} catch (IllegalArgumentException ex) {
			// 400
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		} catch (DataAccessException ex) {
			// Mainly for DB constraint
			// 417
			throw new WebApplicationException(417);
		}
		
		String result = "Hotel removed : " + hotel;
		// 200
		return Response.ok(result).build();
    }
}
