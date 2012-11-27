package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ClientService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ReservationTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.ClientServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.HotelServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.ReservationServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.RoomServiceImpl;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

/**
 *
 * @author Marian Rusnak
 */
@UrlBinding("/reservations/{$event}/{reservation.id}")
public class ReservationsActionBean implements ActionBean {

	private ActionBeanContext context;
	private ReservationService reservationService = new ReservationServiceImpl();
	private HotelService hotelService = new HotelServiceImpl();
	private ClientService clientService = new ClientServiceImpl();
	private RoomService roomService = new RoomServiceImpl();
	private Object[] months;
	private List<ReservationTO> reservations;
	private DateInterval dateInterval;
        private Date from;
        private Date to;
        
        @ValidateNestedProperties(value = {
            @Validate(on = {"add"}, field = "client", required = true),
            @Validate(on = {"add"}, field = "room", required = true)
        })
	private ReservationTO reservation;
	private ClientTO client;
	private RoomTO room;
        
        @ValidateNestedProperties(value = {
            @Validate(on = {"createContinue"}, field = "id", required = true)
        })
        private HotelTO hotel;

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
	public void populateDropDownLists() {
		String[] monthNames = (new DateFormatSymbols()).getMonths();
		Object[] months = new Object[12];
		for (int i = 0; i < 12; i++) {
			Month month = new Month();
			month.setIndex(i);
			month.setName(monthNames[i]);
			months[i] = month;
		}
		this.months = months;
	}

	@DefaultHandler
	public Resolution all() {
		if (dateInterval != null) {

			String hotelID = context.getRequest().getParameter("hotel.id");
			if (hotelID != null) {
				HotelTO hotel = hotelService.findHotel(Long.parseLong(hotelID));
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
		Date from = new Date();
		from.setDate(dateInterval.getDateFrom());
		from.setMonth(dateInterval.getMonthFrom());
		from.setYear(dateInterval.getYearFrom() - 1900);
		return from;
	}

	private Date getDateTo() {
		Date to = new Date();
		to.setDate(dateInterval.getDateTo());
		to.setMonth(dateInterval.getMonthTo());
		to.setYear(dateInterval.getYearTo() - 1900);
		return to;
	}

	public Resolution chooseDate() {
		return new RedirectResolution(this.getClass(), "all");
	}

	@Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
	public void loadReservationFromDatabase() {
		String ids = context.getRequest().getParameter("reservation.id");
		if (ids == null) {
			return;
		}
		reservation = reservationService.getReservation(Long.parseLong(ids));
	}

	public Resolution create() {
		return new ForwardResolution("/reservation/createStepOne.jsp");
	}
        
        public Resolution createContinue() {
                return new ForwardResolution("/reservation/createStepTwo.jsp").addParameter("from", getDateFrom()).addParameter("to", getDateTo());
        }

	@Before(stages = LifecycleStage.BindingAndValidation, on = {"add"})
	public void loadClientAndRoomFromDatabase() throws ParseException {
		String clientID = context.getRequest().getParameter("client.id");
		String roomID = context.getRequest().getParameter("room.id");
                String from = context.getRequest().getParameter("from");
                String to = context.getRequest().getParameter("to");
		if (clientID == null || roomID == null) {
			return;
		}
		client = clientService.findClient(Long.parseLong(clientID));
		room = roomService.getRoom(Long.parseLong(roomID));
                this.from = new SimpleDateFormat("dd/MM/yy").parse(from);
                this.to =  new SimpleDateFormat("dd/MM/yy").parse(to);
	}
        
        @Before(stages = LifecycleStage.BindingAndValidation, on = {"createContinue"})
	public void loadHotelFromDatabase() {
		String hotelID = context.getRequest().getParameter("hotel.id");
		if (hotelID == null)
                    return;
                hotel = hotelService.findHotel(Long.parseLong(hotelID));
	}

	public Resolution add() throws ParseException {
                reservation = new ReservationTO();
                reservation.setFromDate(from);
		reservation.setToDate(to);
		reservation.setClient(client);
		reservation.setRoom(room);
		reservationService.createReservation(reservation);

		return new RedirectResolution(this.getClass(), "all");
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
