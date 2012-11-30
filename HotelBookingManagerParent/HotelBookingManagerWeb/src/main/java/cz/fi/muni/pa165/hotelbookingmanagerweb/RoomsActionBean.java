/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanagerweb;


import cz.fi.muni.pa165.hotelbookingmanagerapi.service.HotelService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.service.RoomService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomTO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl.HotelServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl.RoomServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.util.List;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Filip Bogyai
 */
@UrlBinding("/rooms/{$event}/{room.id}")
public class RoomsActionBean implements ActionBean {

    private ActionBeanContext context;

    @SpringBean
    protected RoomService roomManager;
    @SpringBean
    protected HotelService hotelManager;

    @DefaultHandler
    public Resolution roomAll() {
        return new ForwardResolution("/room/showRoom.jsp");
    }

    public List<RoomTO> getRooms() {
        return roomManager.findAllRooms();
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }
    
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "pricePerNight", required = true, minvalue = 1)
    })
    private RoomTO room;
    private static HotelTO hotel;



    public RoomTO getRoom() {
        return room;
    }

    public void setRoom(RoomTO room) {
        this.room = room;
    }

    public List<RoomTO> getHotelRooms() {
        return roomManager.findRoomsByHotel(hotel);
        
    }

    public Resolution add() {
        if (room != null)
            room.setHotel(hotel);
        roomManager.createRoom(room);
        return new RedirectResolution(this.getClass(), "hotelRooms").addParameter("hotel", hotel.getId());
    }

    public Resolution delete() {
        try {
            roomManager.deleteRoom(room);
            return new RedirectResolution(this.getClass(), "hotelRooms").addParameter("hotel", hotel.getId());
        } catch (DataAccessException e) {
            return new RedirectResolution("/room/errorRoom.jsp");
        }
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save", "delete"})
    public void loadRoomFromDatabase() {
        String ids = context.getRequest().getParameter("room.id");
        if (ids == null) return;
        room = roomManager.getRoom(Long.parseLong(ids));
    }

    public Resolution edit() {
        return new ForwardResolution("/room/editRoom.jsp");
    }

    public Resolution save() {
        roomManager.updateRoom(room);
        return new RedirectResolution(this.getClass(), "hotelRooms").addParameter("hotel", hotel.getId());
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"hotelRooms"})
    public void loadHotelRooms() {
        String ids = context.getRequest().getParameter("hotel");
        if (ids == null) return;
         hotel = hotelManager.findHotel(Long.parseLong(ids));
    }

    public Resolution hotelRooms() {
        return new ForwardResolution("/room/showRoom.jsp");
    }

    public Resolution backToHotels() {
        return new ForwardResolution("/hotel/showHotel.jsp");
    }


}
