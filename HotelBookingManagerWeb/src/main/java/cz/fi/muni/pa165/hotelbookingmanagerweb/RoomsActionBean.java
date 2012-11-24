/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.HotelServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.RoomServiceImpl;

import java.util.ArrayList;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
 
import java.util.List;

/**
 *
 * @author Filip Bogyai
 */
@UrlBinding("/rooms/{$event}/{room.id}")
public class RoomsActionBean implements ActionBean {
      
    private ActionBeanContext context;
     
    //@SpringBean    
    protected RoomService roomManager = new RoomServiceImpl();
    protected HotelService hotelManager = new HotelServiceImpl();    
 
    @DefaultHandler
    public Resolution all() {
        return new ForwardResolution("/showRoom.jsp");
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
    /*
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "pricePerNight", required = true, minvalue = 1)
    })
    */
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
        room.setHotel(hotel);
        roomManager.createRoom(room);
        return new RedirectResolution(this.getClass(), "all");
    }
 
    public Resolution delete() {
        roomManager.deleteRoom(room);                       
        return new RedirectResolution(this.getClass(), "all");
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadRoomFromDatabase() {
        String ids = context.getRequest().getParameter("room.id");
        if (ids == null) return;
        room = roomManager.getRoom(Long.parseLong(ids));
    }
 
    public Resolution edit() {
        return new ForwardResolution("/editRoom.jsp");
    }
 
    public Resolution save() {
        roomManager.updateRoom(room);
        return new RedirectResolution(this.getClass(), "all");
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"hotelRooms"})
    public void loadHotelRooms() {
        String ids = context.getRequest().getParameter("hotel");
        if (ids == null) return;
         hotel = hotelManager.findHotel(Long.parseLong(ids));        
    }
    
    public Resolution hotelRooms() {
        return new ForwardResolution("/showRoom.jsp");
    }
    
    public Resolution backToHotels() {
        return new ForwardResolution("/showHotel.jsp");
    }  

    
}
