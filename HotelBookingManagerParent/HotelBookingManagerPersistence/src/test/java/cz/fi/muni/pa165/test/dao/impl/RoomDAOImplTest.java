package cz.fi.muni.pa165.test.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomType;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.App;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.ReservationDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Contact;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Room;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.ConstraintViolationException;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
public class RoomDAOImplTest {
    private RoomDAO roomDAO;
    private ClientDAO clientDAO;
    private ReservationDAO reservationDAO;
    private HotelDAO hotelDAO;

    private ApplicationContext context;

    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        roomDAO = context.getBean(RoomDAO.class);
        hotelDAO = context.getBean(HotelDAO.class);
        clientDAO = context.getBean(ClientDAO.class);
        reservationDAO = context.getBean(ReservationDAO.class);
    }

    @After
    public void tearDown() {
        for (Reservation reservation : reservationDAO.findAllReservations()) {
            reservationDAO.delete(reservation);
        }
        for (Hotel hotel : hotelDAO.findAll()) {
            hotelDAO.delete(hotel);
        }
        for (Room room : roomDAO.findAllRooms()) {
            roomDAO.delete(room);
        }
        for (Client client : clientDAO.findAll()) {
            clientDAO.delete(client);
        }
        roomDAO = null;
        hotelDAO = null;
        clientDAO = null;
        reservationDAO = null;
    }

    @Test
    public void testCreateAndGetRoom() {

        //Create a null Room
        try {
            roomDAO.create(null);
            fail("No DataAccessException thrown while creating a null Room.");
        } catch (DataAccessException iae) {
            //Works as intended
        }

        //Get a null Room
        try {
            roomDAO.get(null);
            fail("No DataAccessException thrown while removing a null Room.");
        } catch (DataAccessException iae) {
            //Works as intended
        }

        Room room = App.DatabaseSampler.buildRoom(RoomType.Double, BigDecimal.valueOf(777.00), new Hotel());
        room.setHotel(null);
        //Create a Room with null Hotel
        try {
            roomDAO.create(room);
            fail("Room with null Hotel was created.");
        } catch (ConstraintViolationException cve) {
            //All works well
        }

        Contact contact = App.DatabaseSampler.buildContact("123456789", "random@email.address", "street1", "city2", "country3");
        Hotel hotel = App.DatabaseSampler.buildHotel("MyNewHotel", contact);

        room.setHotel(hotel);

        hotelDAO.create(hotel);

        roomDAO.create(room);

        Long id = room.getId();
        assertThat(room, is(notNullValue()));
        Room room2 = roomDAO.get(id);
        assertThat(room2, is(not(sameInstance(room))));
        assertThat(room2, is(equalTo(room)));
    }

    @Test
    public void testUpdateRoom() {
        Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
        Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

        hotelDAO.create(hotel);

        Room room = App.DatabaseSampler.buildRoom(RoomType.Royal, BigDecimal.valueOf(333.00), hotel);


        roomDAO.create(room);


        //Change price per night
        room.setPricePerNight(BigDecimal.valueOf(777));
        roomDAO.update(room);
        Room tempRoom = roomDAO.get(room.getId());
        assertThat("Price per night not changed!", tempRoom.getPricePerNight(), is(equalTo(BigDecimal.valueOf(777).setScale(2))));
        assertThat("Hotel changed!", tempRoom.getHotel(), is(equalTo(hotel)));

        //Change Hotel
        Hotel hotel2 = App.DatabaseSampler.buildHotel("MyHotelToChangeTo", contact);
        hotelDAO.create(hotel2);
        room.setHotel(hotel2);
        roomDAO.update(room);
        tempRoom = roomDAO.get(room.getId());
        assertThat("Price per night changed!", tempRoom.getPricePerNight(), is(equalTo(BigDecimal.valueOf(777).setScale(2))));
        assertThat("Hotel not changed!", tempRoom.getHotel(), is(equalTo(hotel2)));
        assertThat("Room list of hotel not changed!", hotelDAO.get(room.getHotel().getId()).getRooms(), hasItems(room));
        assertThat("Previous hotel still has Room present!", hotelDAO.get(hotel.getId()).getRooms(), not(hasItems(room)));

    }

    @Test
    public void testDeleteRoom() {
        //Delete a null Room
        try {
            roomDAO.delete(null);
            fail("No DataAccessException thrown while removing null Room");
        } catch (DataAccessException iae) {
            //All works as intended
        }

        Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
        Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

        hotelDAO.create(hotel);

        Room room1 = App.DatabaseSampler.buildRoom(RoomType.Single, BigDecimal.ONE, hotel);

        roomDAO.create(room1);
        Room room2 = App.DatabaseSampler.buildRoom(RoomType.Single, BigDecimal.ONE, hotel);
        roomDAO.create(room2);

        assertThat(roomDAO.get(room1.getId()), is(not(nullValue())));
        assertThat(roomDAO.get(room2.getId()), is(not(nullValue())));


        assertThat(hotelDAO.get(hotel.getId()).getRooms(), hasItems(room1));

        roomDAO.delete(room1);

        assertThat(hotelDAO.get(hotel.getId()).getRooms(), not(hasItems(room1)));

        assertThat(roomDAO.get(room1.getId()), is(nullValue()));
        assertThat(roomDAO.get(room2.getId()), is(not(nullValue())));
   }

   @Test
   public void testFindAllVacantRooms() {
       Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
       Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

       hotelDAO.create(hotel);

       Room room1 = App.DatabaseSampler.buildRoom(RoomType.Single, BigDecimal.ONE, hotel);
       roomDAO.create(room1);

       Room room2 = App.DatabaseSampler.buildRoom(RoomType.Single, BigDecimal.TEN, hotel);
       roomDAO.create(room2);

       Client client = App.DatabaseSampler.buildClient("first", "last", contact);
       clientDAO.create(client);
       Date from = new Date(1990, 1, 1);
       Date to = new Date(2000, 1, 1);
       Reservation reservation = App.DatabaseSampler.buildReservation(client, room2, from, to, BigDecimal.ZERO);
       reservationDAO.create(reservation);

       assertThat(roomDAO.findAllVacantRooms(from, to), hasItems(room1));
       assertThat(roomDAO.findAllVacantRooms(from, to), not(hasItems(room2)));
   }
}
