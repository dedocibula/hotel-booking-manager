package cz.fi.muni.pa165.test.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.Contact;
import cz.fi.muni.pa165.hotelbookingmanager.dao.impl.HotelDAOImpl;
import cz.fi.muni.pa165.hotelbookingmanager.dao.impl.RoomDAOImpl;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import java.math.BigDecimal;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
public class RoomDAOImplTest {
    EntityManagerFactory emf;
    RoomDAOImpl roomDAO;

    @Before
    public void setUp() {
        roomDAO = new RoomDAOImpl();
        emf = Persistence.createEntityManagerFactory("HotelBookingManagerPU");
        //roomDAO.setEmf(emf);
    }

    @After
    public void tearDown() {
        emf = null;
        roomDAO = null;
    }

    @Test
    public void testCreateAndGetRoom() {

        //Create a null Room
        try {
            roomDAO.create(null);
            fail("No IllegalArgumentException thrown while creating a null Room.");
        } catch (IllegalArgumentException iae) {
            //Works as intended
        }

        //Get a null Room
        try {
            roomDAO.get(null);
            fail("No IllegalArgumentException thrown while removing a null Room.");
        } catch (IllegalArgumentException iae) {
            //Works as intended
        }

        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777.00), new Hotel());
        room.setHotel(null);
        //Create a Room with null Hotel
        try {
            roomDAO.create(room);
            fail("Room with null Hotel was created.");
        } catch (ConstraintViolationException iae) {
            //All works well
        }

        Contact contact = App.DatabaseSampler.buildContact("123456789", "random@email.address", "street1", "city2", "country3");
        Hotel hotel = App.DatabaseSampler.buildHotel("MyNewHotel", contact);

        room.setHotel(hotel);

        HotelDAOImpl hotelDAO = new HotelDAOImpl();
        //hotelDAO.setEntityManagerFactory(emf);
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

        HotelDAOImpl hotelDAO = new HotelDAOImpl();
        //hotelDAO.setEntityManagerFactory(emf);
        hotelDAO.create(hotel);

        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(333.00), hotel);


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
            fail("No IllegalArgumentException thrown while removing null Room");
        } catch (IllegalArgumentException iae) {
            //All works as intended
        }

        Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
        Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

        HotelDAOImpl hotelDAO = new HotelDAOImpl();
        //hotelDAO.setEntityManagerFactory(emf);
        hotelDAO.create(hotel);

        Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel);

        roomDAO.create(room1);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel);
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
       for (Room room : roomDAO.findAllRooms()) {
           roomDAO.delete(room);
       }
       assertTrue("There are still Rooms in the database after deletion.", roomDAO.findAllVacantRooms().isEmpty());

       Contact contact = App.DatabaseSampler.buildContact("12345", "something@random.wtf", "streetz", "town", "land");
       Hotel hotel = App.DatabaseSampler.buildHotel("MyHotel", contact);

       HotelDAOImpl hotelDAO = new HotelDAOImpl();
       //hotelDAO.setEntityManagerFactory(emf);
       hotelDAO.create(hotel);

       Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, hotel);
       roomDAO.create(room1);

       Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.TEN, hotel);
       roomDAO.create(room2);

       assertThat(roomDAO.findAllVacantRooms(), hasItems(room1));
       assertThat(roomDAO.findAllVacantRooms(), not(hasItems(room2)));
   }
}
