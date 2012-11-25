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
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Filip Bogyai
 */

@UrlBinding("/hotels/{$event}/{hotel.id}")
public class HotelsActionBean implements ActionBean {

    private ActionBeanContext context;

    //@SpringBean
    protected HotelService hotelManager = new HotelServiceImpl();
    private CountryPicker countryPicker = new CountryPicker();

    @DefaultHandler
    public Resolution hotelAll() {
        return new ForwardResolution("/showHotel.jsp");
    }

    public List<HotelTO> getHotels() {
        return hotelManager.findAllHotels();
    }
    
    public Set<String> getCountries() {
        return countryPicker.getCountriesName();
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
            @Validate(on = {"add", "save"}, field = "id", required = true),
            @Validate(on = {"add", "save"}, field = "email", required = true),
            @Validate(on = {"add", "save"}, field = "address", required = true),
            @Validate(on = {"add", "save"}, field = "city", required = true),
            @Validate(on = {"add", "save"}, field = "country", required = true)

    })
    */
    private HotelTO hotel;

    public Resolution add() {
        hotelManager.createHotel(hotel);
        return new RedirectResolution(this.getClass(), "all");
    }

    public HotelTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelTO hotel) {
        this.hotel = hotel;
    }

    public Resolution delete() {
        hotelManager.deleteHotel(hotel);
        return new RedirectResolution(this.getClass(), "all");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save","rooms"})
    public void loadHotelFromDatabase() {
        String ids = context.getRequest().getParameter("hotel.id");
        if (ids == null) return;
        hotel = hotelManager.findHotel(Long.parseLong(ids));
    }

    public Resolution edit() {
        return new ForwardResolution("/editHotel.jsp");
    }

    public Resolution save() {
        hotelManager.updateHotel(hotel);
        return new RedirectResolution(this.getClass(), "all");
    }

    public Resolution rooms() {
        return new RedirectResolution( RoomsActionBean.class, "hotelRooms").addParameter("hotel", hotel.getId());
    }
}


