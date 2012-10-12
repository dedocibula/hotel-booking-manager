package com.mycompany.hotelbookingmanager;

import com.sun.media.sound.RIFFInvalidDataException;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;

/**
 * Tests for RoomDAOImpl class
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
        roomDAO.setEmf(emf);
    }

    @After
    public void tearDown() {
        emf = null;
        roomDAO = null;
    }

    @Test
    public void testCreateAndGetRoom() throws UnavailableDatabaseException {

        //Create a null Room
        try {
            roomDAO.create(null);
            fail("No IllegalArgumentException thrown while creating a null Room.");
        } catch (IllegalArgumentException iae) {
            //Works as intended
        }

        //Remove a null Room
        try {
            roomDAO.get(null);
            fail("No IllegalArgumentException thrown while removing a null Room.");
        } catch (IllegalArgumentException iae) {
            //Works as intended
        }

        Room room = newRoom(true, BigDecimal.valueOf(777), null);
        roomDAO.create(room);

        Long id = room.getId();
        assertThat(room, is(notNullValue()));
        Room room2 = roomDAO.get(id);
        assertThat(room2, is(not(sameInstance(room))));
        assertThat(room2, is(equalTo(room)));
    }

    @Test
    public void testUpdateRoom() throws UnavailableDatabaseException {
        Hotel hotel = new Hotel();
        Contact contact = new Contact();
            contact.setAddress("ulicá");
            contact.setCity("city");
            contact.setCountry("contriez");
            contact.setEmail("something@random.wtf");
            contact.setPhone("123456789");

        hotel.setContact(null);
        Room room = newRoom(true, BigDecimal.valueOf(333), null);
        roomDAO.create(room);

        //Change vacancy status
        room.setVacant(false);
        roomDAO.update(room);
        assertThat("Vacancy not changed!", room.isVacant(), is(equalTo(false)));
        assertThat("Price per night changed!", room.getPricePerNight(), is(equalTo(BigDecimal.valueOf(333))));
        assertThat("Hotel changed!", room.getHotel(), is(equalTo(null)));

        //Change price per night
        room.setPricePerNight(BigDecimal.valueOf(777));
        roomDAO.update(room);
        assertThat("Vacancy changed!", room.isVacant(), is(equalTo(false)));
        assertThat("Price per night not changed!", room.getPricePerNight(), is(equalTo(BigDecimal.valueOf(777))));
        assertThat("Hotel changed!", room.getHotel(), is(equalTo(null)));

        //Change Hotel
        room.setHotel(hotel);
        roomDAO.update(room);
        assertThat("Vacancy changed!", room.isVacant(), is(equalTo(false)));
        assertThat("Price per night changed!", room.getPricePerNight(), is(equalTo(BigDecimal.valueOf(777))));
        assertThat("Hotel not changed!", room.getHotel(), is(equalTo(hotel)));
    }

    @Test
    public void testDeleteRoom() throws UnavailableDatabaseException {
        //Delete a null Room
        try {
            roomDAO.delete(null);
            fail("No IllegalArgumentException thrown while removing null Room");
        } catch (IllegalArgumentException iae) {
            //All works as intended
        }

        Room room1 = newRoom(true, BigDecimal.ONE, null);
        Room room2 = newRoom(false, BigDecimal.TEN, null);
        roomDAO.create(room1);
        roomDAO.create(room2);

        assertThat(roomDAO.get(room1.getId()), is(not(nullValue())));
        assertThat(roomDAO.get(room2.getId()), is(not(nullValue())));

        roomDAO.delete(room1);

        assertThat(roomDAO.get(room1.getId()), is(nullValue()));
        assertThat(roomDAO.get(room2.getId()), is(not(nullValue())));
   }

   @Test
   public void testFindAllVacantRooms() throws UnavailableDatabaseException {
       for (Room room : roomDAO.findAllRooms()) {
           roomDAO.delete(room);
       }
       assertTrue(roomDAO.findAllVacantRooms().isEmpty());

       Room room1 = newRoom(true, BigDecimal.valueOf(123), null);
       Room room2 = newRoom(false, BigDecimal.valueOf(987), null);

       roomDAO.create(room1);
       roomDAO.create(room2);

       assertThat(roomDAO.findAllVacantRooms(), hasItems(room1));
       assertThat(roomDAO.findAllVacantRooms(), not(hasItems(room2)));
   }

    private static Room newRoom(boolean vacant, BigDecimal pricePerNight, Hotel hotel) {
        Room room = new Room();
        room.setVacant(vacant);
        room.setPricePerNight(pricePerNight);
        room.setHotel(hotel);
        return room;
    }
}
