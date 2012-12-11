package cz.fi.muni.pa165.hotelbookingmanagerrest;

import com.sun.jersey.api.Responses;
import cz.fi.muni.pa165.hotelbookingmanagerapi.service.HotelService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomTO;
import java.util.ArrayList;
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
@Path("/hotels")
public class HotelRESTService {
    
    @Autowired
    private HotelService hotelService;
    
    @GET
    @Path("/findHotel")
    @Produces(MediaType.APPLICATION_JSON)
    public HotelTO getHotelInJSON(@QueryParam("id") Long id) {
        HotelTO hotel = hotelService.findHotel(id);
        if (hotel == null)
            throw new WebApplicationException(
                    Response.status(Responses.NOT_FOUND)
                            .entity("Hotel with id " + id + " wasn't found")
                            .build());
        return hotel;
    }
    
    @GET
    @Path("/findAllHotels")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelTO> getAllHotelsInJSON() {
        List<HotelTO> temp = hotelService.findAllHotels();
        return temp;
    }
    
    @GET
    @Path("/findHotelsByName")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelTO> getHotelsByNameInJSON(@QueryParam("name") String name) {
        List<HotelTO> temp = hotelService.findHotelsByName(name);
        if (temp.isEmpty())
            throw new WebApplicationException(
                    Response.status(Responses.NOT_FOUND)
                            .entity("Hotels with name " + name + " weren't found")
                            .build());
        return temp;
    }
    
    @GET
    @Path("/findHotelsByAddress")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelTO> getHotelsByAddressInJSON(@QueryParam("address") String address) {
        List<HotelTO> temp = hotelService.findHotelsByAddress(address);
        if (temp.isEmpty())
            throw new WebApplicationException(
                    Response.status(Responses.NOT_FOUND)
                            .entity("Hotels with address " + address + " weren't found")
                            .build());
        return temp;
    }
    
    @GET
    @Path("/findHotelsByCity")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelTO> getHotelsByCityInJSON(@QueryParam("city") String city) {
        List<HotelTO> temp = hotelService.findHotelsByCity(city);
        if (temp.isEmpty())
            throw new WebApplicationException(
                    Response.status(Responses.NOT_FOUND)
                            .entity("Hotels with city " + city + " weren't found")
                            .build());
        return temp;
    }
    
    @GET
    @Path("/findHotelsByCountry")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HotelTO> getHotelsByCountryInJSON(@QueryParam("country") String country) {
        List<HotelTO> temp = hotelService.findHotelsByCountry(country);
        if (temp.isEmpty())
            throw new WebApplicationException(
                    Response.status(Responses.NOT_FOUND)
                            .entity("Hotels with country " + country + " weren't found")
                            .build());
        return temp;
    }

    @POST
    @Path("/createHotel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHotel(HotelTO hotel) {
        hotelService.createHotel(hotel);
        String result = "Hotel saved : " + hotel;
        return Response.ok(result).build();
    }
    
    @POST
    @Path("/updateHotel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateHotel(HotelTO hotel) {
        hotelService.updateHotel(hotel);
        String result = "Hotel updated : " + hotel;
        return Response.ok(result).build();
    }
    
    @DELETE
    @Path("/deleteHotel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteHotel(@QueryParam("id") Long id) {
        HotelTO hotel = hotelService.findHotel(id);
        hotelService.deleteHotel(hotel);
        String result = "Hotel removed : " + hotel;
        return Response.ok(result).build();
    }
}
