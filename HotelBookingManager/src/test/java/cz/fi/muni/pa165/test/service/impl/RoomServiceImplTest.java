package cz.fi.muni.pa165.test.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.Contact;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ClientService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thanh Dang Hoang Minh
 */

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class RoomServiceImplTest {

    private RoomService roomService;
    private HotelService hotelService;
    private ClientService clientService;
    private ReservationService reservationService;

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        roomService = context.getBean(RoomService.class);
        hotelService = context.getBean(HotelService.class);
        clientService = context.getBean(ClientService.class);
        reservationService = context.getBean(ReservationService.class);
    }

    @After
    public void tearDown() {
        roomService = null;
        hotelService = null;
        clientService = null;
        reservationService = null;
    }

    /**
     * Test of createRoomRoom method, of class RoomServiceImpl.
     */
    @Test
    public void testCreateAndGetRoom() {
        Contact contact = App.DatabaseSampler.buildContact("123", "some@email.asdf", "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("mynewhotel", contact);
        hotelService.createHotel(hotel);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel);
        roomService.createRoom(room);

        Room room2 = roomService.getRoom(room.getId());
        assertThat(room2, is(not(sameInstance(room))));
        assertThat(room2, is(notNullValue()));
        assertThat(room, is(equalTo(room2)));

        //Create a null Room
        try {
            roomService.createRoom(null);
            fail("No IllegalArgumentException thrown while creating a null Room.");
        } catch (IllegalArgumentException iae) {
            //Works as intended
        }

        //Get a null Room
        try {
            roomService.getRoom(null);
            fail("No IllegalArgumentException thrown while removing a null Room.");
        } catch (IllegalArgumentException iae) {
            //Works as intended
        }

        room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777.00), new Hotel());
        room.setHotel(null);
        //Create a Room with null Hotel
        try {
            roomService.createRoom(room);
            fail("Room with null Hotel was createRoomd.");
        } catch (ConstraintViolationException cve) {
            //All works well
        }
    }

    /**
     * Test of updateRoom method, of class RoomServiceImpl.
     */
    @Test
    public void testUpdateRoom() {
        Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
        Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

        hotelService.createHotel(hotel);

        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(333.00), hotel);


        roomService.createRoom(room);


        //Change price per night
        room.setPricePerNight(BigDecimal.valueOf(777));
        roomService.updateRoom(room);
        Room tempRoom = roomService.getRoom(room.getId());
        assertThat("Price per night not changed!", tempRoom.getPricePerNight(), is(equalTo(BigDecimal.valueOf(777).setScale(2))));
        assertThat("Hotel changed!", tempRoom.getHotel(), is(equalTo(hotel)));

        //Change Hotel
        Hotel hotel2 = App.DatabaseSampler.buildHotel("MyHotelToChangeTo", contact);
        hotelService.createHotel(hotel2);
        room.setHotel(hotel2);
        roomService.updateRoom(room);
        tempRoom = roomService.getRoom(room.getId());
        assertThat("Price per night changed!", tempRoom.getPricePerNight(), is(equalTo(BigDecimal.valueOf(777).setScale(2))));
        assertThat("Hotel not changed!", tempRoom.getHotel(), is(equalTo(hotel2)));
        assertThat("Room list of hotel not changed!", hotelService.findHotel(room.getHotel().getId()).getRooms(), hasItems(room));
        assertThat("Previous hotel still has Room present!", hotelService.findHotel(hotel.getId()).getRooms(), not(hasItems(room)));
    }

    /**
     * Test of deleteRoom method, of class RoomServiceImpl.
     */
    @Test
    public void testDeleteRoom() {
        //Delete a null Room
        try {
            roomService.deleteRoom(null);
            fail("No IllegalArgumentException thrown while removing null Room");
        } catch (IllegalArgumentException iae) {
            //All works as intended
        }

        Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
        Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

        hotelService.createHotel(hotel);

        Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel);

        roomService.createRoom(room1);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel);
        roomService.createRoom(room2);

        assertThat(roomService.getRoom(room1.getId()), is(not(nullValue())));
        assertThat(roomService.getRoom(room2.getId()), is(not(nullValue())));


        assertThat(hotelService.findHotel(hotel.getId()).getRooms(), hasItems(room1));

        roomService.deleteRoom(room1);

        assertThat(hotelService.findHotel(hotel.getId()).getRooms(), not(hasItems(room1)));

        assertThat(roomService.getRoom(room1.getId()), is(nullValue()));
        assertThat(roomService.getRoom(room2.getId()), is(not(nullValue())));
    }

    /**
     * Test of findAllRooms method, of class RoomServiceImpl.
     */
    @Test
    public void testFindAllRooms() {
        for (Room room : roomService.findAllRooms()) {
           roomService.deleteRoom(room);
       }
       assertTrue("There are still Rooms in the database after deletion.", roomService.findAllRooms().isEmpty());

       Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
       Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

       hotelService.createHotel(hotel);

       Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel);
       roomService.createRoom(room1);

       Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.TEN, hotel);
       roomService.createRoom(room2);

       assertThat(roomService.findAllRooms(), hasItems(room1));
       assertThat(roomService.findAllRooms(), hasItems(room2));
       assertEquals(roomService.findAllRooms().size(), 2);
    }

    /**
     * Test of findRoomsByName method, of class RoomServiceImpl.
     */
    @Test
    public void testFindVacantRooms() {
        for (Room room : roomService.findAllRooms()) {
           roomService.deleteRoom(room);
       }
       assertTrue("There are still Rooms in the database after deletion.", roomService.findAllRooms().isEmpty());

       Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
       Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

       hotelService.createHotel(hotel);

       Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel);
       roomService.createRoom(room1);

       Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.TEN, hotel);
       roomService.createRoom(room2);

       Client client = App.DatabaseSampler.buildClient("first", "last", contact);
       //TODO
       //clientService.createClient(client);
       Date from = new Date(1990, 1, 1);
       Date to = new Date(2000, 1, 1);
       Reservation reservation = App.DatabaseSampler.buildReservation(client, room2, from, to, BigDecimal.ZERO);
       reservationService.createReservation(reservation);

       assertThat(roomService.findVacantRooms(from, to, hotel), hasItems(room1));
       assertThat(roomService.findVacantRooms(from, to, hotel), not(hasItems(room2)));
    }

    @Test
    public void testFindRoomsByHotel() {
        for (Room room : roomService.findAllRooms()) {
           roomService.deleteRoom(room);
       }
       assertTrue("There are still Rooms in the database after deletion.", roomService.findAllRooms().isEmpty());

       Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
       Hotel hotel1 = App.DatabaseSampler.buildHotel("MyHotel", contact);
       Hotel hotel2 = App.DatabaseSampler.buildHotel("MyHotel2", contact);

       hotelService.createHotel(hotel1);
       hotelService.createHotel(hotel2);

       Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel1);
       roomService.createRoom(room1);

       Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.TEN, hotel2);
       roomService.createRoom(room2);


       assertThat(roomService.findRoomsByHotel(hotel1), hasItems(room1));
       assertThat(roomService.findRoomsByHotel(hotel1), not(hasItems(room2)));
       assertThat(roomService.findRoomsByHotel(hotel2), hasItems(room2));
       assertThat(roomService.findRoomsByHotel(hotel2), not(hasItems(room1)));
    }
}
