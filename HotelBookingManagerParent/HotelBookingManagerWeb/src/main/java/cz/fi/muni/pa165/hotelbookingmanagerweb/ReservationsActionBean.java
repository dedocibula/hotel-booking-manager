package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.ClientService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.service.HotelService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.service.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.service.RoomService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ReservationTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomTO;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.*;

/**
 *
 * @author Marian Rusnak
 */
@UrlBinding("/reservations/{$event}/{reservation.id}")
public class ReservationsActionBean implements ActionBean {

	private ActionBeanContext context;
	
	// service
	@SpringBean
	private ReservationService reservationService;
	@SpringBean
	private HotelService hotelService;
	@SpringBean
	private ClientService clientService;
	@SpringBean
	private RoomService roomService;
	
	// DTO
	private ReservationTO reservation;
	@ValidateNestedProperties(value = {
		@Validate(on = "add", field = "id", required = true)
	})
	private ClientTO client;
	@ValidateNestedProperties(value = {
		@Validate(on = "add", field = "id", required = true)
	})
	private RoomTO room;
	@ValidateNestedProperties(value = {
		@Validate(on = {"createContinue", "add"}, field = "id", required = true)
	})
	private HotelTO hotel;
	
	// hepler fields
	private String months;
	private List<ReservationTO> reservations;
	private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	@Validate(on = {"createContinue", "add"}, required = true)
	private String from;
	@Validate(on = {"createContinue", "add"}, required = true)
	private String to;
	private Date dateFrom;
	private Date dateTo;

	@ValidationMethod(on = {"createContinue", "add"})
	public void validateFutureDates(ValidationErrors errors) {
		dateFrom = getDateFrom();
		dateTo = getDateTo();
		if (dateFrom == null || dateTo == null) {
			return;
		}
		if (dateFrom.before(new Date())) {
			errors.add("dateFrom", new LocalizableError("validation.date.future"));
		}
		if (dateTo.before(new Date())) {
			errors.add("dateTo", new LocalizableError("validation.date.future"));
		}
	}

	@ValidationMethod()
	public void validateDates(ValidationErrors errors) {
		dateFrom = getDateFrom();
		dateTo = getDateTo();
		if (dateFrom == null || dateTo == null) {
			return;
		}
		if (dateFrom.after(dateTo)) {
			errors.add("dates", new LocalizableError("validation.date.after"));
		}
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public HotelTO getHotel() {
		return hotel;
	}

	public void setHotel(HotelTO hotel) {
		this.hotel = hotel;
	}

	public List<HotelTO> getHotels() {
		return hotelService.findAllHotels();
	}

	public List<ClientTO> getClients() {
		return clientService.findAllClients();
	}

	public List<RoomTO> getRooms() {
		return roomService.findVacantRooms(getDateFrom(), getDateTo(), hotel);
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}
	
	public List<ReservationTO> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationTO> reservations) {
		this.reservations = reservations;
	}

	public ReservationTO getReservation() {
		return reservation;
	}

	public void setReservation(ReservationTO reservation) {
		this.reservation = reservation;
	}

	public ClientTO getClient() {
		return client;
	}

	public void setClient(ClientTO client) {
		this.client = client;
	}

	public RoomTO getRoom() {
		return room;
	}

	public void setRoom(RoomTO room) {
		this.room = room;
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
	public void populateMonths() {
		String[] monthNames = (new DateFormatSymbols()).getMonths();
		StringBuilder months = new StringBuilder("[ ");
		for (int i = 0; i < 11; i++) {
			months.append('\'').append(monthNames[i]).append("', ");
		}
		months.append('\'').append(monthNames[11]).append("' ]");
		this.months = months.toString();
	}
	
	@DefaultHandler
	public Resolution all() {
		if (from != null && !from.equals("") && to != null && !to.equals("")) {
			String hotelID = context.getRequest().getParameter("hotel.id");
			HotelTO hotel = null;
			if (hotelID != null) {
				hotel = hotelService.findHotel(Long.parseLong(hotelID));
			}
			if (hotel != null) {
				reservations = reservationService.findReservationsByDate(getDateFrom(), getDateTo(), hotel);
			} else {
				reservations = reservationService.findReservationsByDate(getDateFrom(), getDateTo());
			}
		} else {
			reservations = reservationService.findAllReservations();
		}

		return new ForwardResolution("/reservation/reservation.jsp");
	}

	private Date getDateFrom() {
		if (dateFrom != null) {
			return dateFrom;
		}
		try {
			from = context.getRequest().getParameter("from");
			if (from == null) {
				return null;
			}
			dateFrom = dateFormat.parse(from);
			return dateFrom;
		} catch (ParseException ex) {
			Logger.getLogger(ReservationsActionBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	private Date getDateTo() {
		if (dateTo != null) {
			return dateTo;
		}
		try {
			to = context.getRequest().getParameter("to");
			if (to == null) {
				return null;
			}
			dateTo = dateFormat.parse(to);
			return dateTo;
		} catch (ParseException ex) {
			Logger.getLogger(ReservationsActionBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public Resolution chooseDate() {
		return new RedirectResolution(this.getClass(), "all");
	}

	public Resolution create() {
		return new ForwardResolution("/reservation/createStepOne.jsp");
	}

	@Before(stages = LifecycleStage.BindingAndValidation, on = {"createContinue", "add"})
	public void loadHotelFromDatabase() {
		String hotelID = context.getRequest().getParameter("hotel.id");
		if (hotelID == null) {
			return;
		}
		hotel = hotelService.findHotel(Long.parseLong(hotelID));
	}

	public Resolution createContinue() {
		return new ForwardResolution("/reservation/createStepTwo.jsp");
	}
        
        public Resolution goBack() {
                return create();
        }

	@Before(stages = LifecycleStage.BindingAndValidation, on = {"add"})
	public void loadClientAndRoomFromDatabase() {
		String clientID = context.getRequest().getParameter("client.id");
		String roomID = context.getRequest().getParameter("room.id");
		dateFrom = getDateFrom();
		dateTo = getDateTo();
		if (clientID == null || roomID == null || dateFrom == null || dateTo == null) {
			return;
		}
		client = clientService.findClient(Long.parseLong(clientID));
		room = roomService.getRoom(Long.parseLong(roomID));

		reservation = new ReservationTO();
		reservation.setFromDate(dateFrom);
		reservation.setToDate(dateTo);
		reservation.setClient(client);
		reservation.setRoom(room);
		reservation.setPrice(room.getPricePerNight());
	}

	public Resolution add() {
		reservationService.createReservation(reservation);
		return new RedirectResolution(this.getClass(), "all");
	}

	@Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save", "delete"})
	public void loadReservationFromDatabase() {
		String ids = context.getRequest().getParameter("reservation.id");
		if (ids == null) {
			return;
		}
		reservation = reservationService.getReservation(Long.parseLong(ids));
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
