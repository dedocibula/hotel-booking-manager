package cz.fi.muni.pa165.hotelbookingmanagerweb;
import cz.fi.muni.pa165.hotelbookingmanagerapi.service.HotelService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import java.util.List;
import java.util.Set;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Filip Bogyai
 */

@UrlBinding("/hotels/{$event}/{hotel.id}")
public class HotelsActionBean implements ActionBean {

    private ActionBeanContext context;

    @SpringBean
    protected HotelService hotelManager;

    private CountryPicker countryPicker = new CountryPicker();

    @DefaultHandler
    public Resolution hotelAll() {
        return new ForwardResolution("/hotel/hotel.jsp");
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

    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "name", required = true, minlength = 2, maxlength = 50),
            @Validate(on = {"add", "save"}, field = "contact.phone", required = true, mask = "\\d*", maxlength = 30),
            @Validate(on = {"add", "save"}, field = "contact.email", required = true, mask = "[\\w\\-\\.\\+]+@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z]+", minlength = 6, maxlength = 50),
            @Validate(on = {"add", "save"}, field = "contact.address", required = true, maxlength = 30),
            @Validate(on = {"add", "save"}, field = "contact.city", required = true, minlength = 2, maxlength = 50)
    })

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
        try {
            hotelManager.deleteHotel(hotel);
            return new RedirectResolution(this.getClass(), "all");
        } catch (DataAccessException e) {
            return new RedirectResolution("/hotel/errorHotel.jsp");
        }
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save","rooms"})
    public void loadHotelFromDatabase() {
        String ids = context.getRequest().getParameter("hotel.id");
        if (ids == null) return;
        hotel = hotelManager.findHotel(Long.parseLong(ids));
    }

    public Resolution edit() {
        return new ForwardResolution("/hotel/editHotel.jsp");
    }

    public Resolution save() {
        hotelManager.updateHotel(hotel);
        return new RedirectResolution(this.getClass(), "all");
    }

    public Resolution rooms() {
        return new RedirectResolution( RoomsActionBean.class, "hotelRooms").addParameter("hotel", hotel.getId());
    }
}


