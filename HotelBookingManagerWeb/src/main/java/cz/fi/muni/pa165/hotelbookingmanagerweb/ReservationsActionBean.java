package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.ReservationServiceImpl;
import java.text.DateFormatSymbols;
import java.util.List;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;

/**
 *
 * @author Marian Rusnak
 */
@UrlBinding("/reservations/{$event}")
public class ReservationsActionBean implements ActionBean {
	
	private ActionBeanContext context;
	private ReservationService reservationService = new ReservationServiceImpl();
	private List<HotelTO> hotels;
	private Object[] months;

	public Object[] getMonths() {
		return months;
	}

	public void setMonths(Object[] months) {
		this.months = months;
	}

	public List<HotelTO> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelTO> hotels) {
		this.hotels = hotels;
	}
	
	@Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }
	
	@Before(stages = LifecycleStage.ResolutionExecution)
	public void setMonths() {
		String[] monthNames = (new DateFormatSymbols()).getMonths();
		Object[] months = new Object[12];
		for(int i = 0; i < 12; i++) {
			Month month = new Month();
			month.setIndex(i);
			month.setName(monthNames[i]);
			months[i] = month;
		}
		this.months = months;
	}
	
	@DefaultHandler
	public Resolution all() {
		return new ForwardResolution("/reservation.jsp");
	}
	
	public Resolution chooseDate() {
		return new ForwardResolution("/reservation.jsp");
	}
	
	public Resolution other() {
		return new RedirectResolution(this.getClass(), "all");
	}
}
