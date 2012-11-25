package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ReservationTO;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.HotelServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.ReservationServiceImpl;
import java.text.DateFormatSymbols;
import java.util.Date;
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
@UrlBinding("/reservations/{$event}/{reservation.id}")
public class ReservationsActionBean implements ActionBean {
	
	private ActionBeanContext context;
	private ReservationService reservationService = new ReservationServiceImpl();
	private HotelService hotelService = new HotelServiceImpl();
	private List<HotelTO> hotels;
	private Object[] months;
	private List<ReservationTO> reservations;
	private DateInterval dateInterval;
	private ReservationTO reservation;

	public List<HotelTO> getHotels() {
		return hotels;
	}

	public void setHotels(List<HotelTO> hotels) {
		this.hotels = hotels;
	}
	
	public Object[] getMonths() {
		return months;
	}

	public void setMonths(Object[] months) {
		this.months = months;
	}

	public List<ReservationTO> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationTO> reservations) {
		this.reservations = reservations;
	}

	public DateInterval getDateInterval() {
		return dateInterval;
	}

	public void setDateInterval(DateInterval dateInterval) {
		this.dateInterval = dateInterval;
	}

	public ReservationTO getReservation() {
		return reservation;
	}

	public void setReservation(ReservationTO reservation) {
		this.reservation = reservation;
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
	public void populateDropDownLists() {
		String[] monthNames = (new DateFormatSymbols()).getMonths();
		Object[] months = new Object[12];
		for(int i = 0; i < 12; i++) {
			Month month = new Month();
			month.setIndex(i);
			month.setName(monthNames[i]);
			months[i] = month;
		}
		this.months = months;
		
		this.hotels = hotelService.findAllHotels();
	}
	
	@DefaultHandler
	public Resolution all() {
		return new ForwardResolution("/reservation/reservation.jsp");
	}
	
	public Resolution chooseDate() {
		Date from = new Date();
		from.setDate(dateInterval.getDateFrom());
		from.setMonth(dateInterval.getDateFrom());
		from.setYear(dateInterval.getYearFrom() - 1900);
		
		Date to = new Date();
		to.setDate(dateInterval.getDateTo());
		to.setMonth(dateInterval.getDateTo());
		to.setYear(dateInterval.getYearTo() - 1900);
		
		reservations = reservationService.findReservationsByDate(from, to);
		
		return new RedirectResolution(this.getClass(), "all");
	}
	
	@Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadHotelFromDatabase() {
        String ids = context.getRequest().getParameter("reservation.id");
        if (ids == null) return;
        reservation = reservationService.getReservation(Long.parseLong(ids));        
    }
	
	public Resolution edit() {
		return new ForwardResolution("/reservation/edit.jsp");
	}
	
	public Resolution save() {
        reservationService.updateReservation(reservation);
        return new RedirectResolution(this.getClass(), "all");
    }
    
    public Resolution delete() {
        reservationService.deleteReservation(reservation);                       
        return new RedirectResolution(this.getClass(), "all");
    }
}
