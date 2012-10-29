/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.test.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.Contact;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.impl.ReservationServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Marian Rusnak
 */
public class ReservationServiceImplTest {
    
    private ReservationService reservationService;
    
    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        reservationService = context.getBean(ReservationService.class);
    }
    
    @After
    public void tearDown() {
        reservationService = null;
    }

    @Test
    public void testCreateReservation() {
        Reservation reservation = sampleReservation();
        
        reservationService.createReservation(reservation);
        
        Reservation reservation2 = reservationService.getReservation(reservation.getId());
        
        assertNotSame(reservation2, reservation);
        assertNotNull(reservation2);
        assertEquals(reservation2, reservation);
    }
    
    @Test
    public void testCreateReservationWithWrongAttributes() {
        try {
            reservationService.createReservation(null);
            fail("Cannot create null reservation.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test of ID already set
        Reservation reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(2013, 5, 20), new Date(2013, 5, 25), BigDecimal.ZERO);
        reservation.setId(10L);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with id already set.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test null from date
        reservation = newReservation(sampleClient(), sampleRoom(), 
                null, new Date(2013, 5, 20), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with null from date.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test null to date
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(2013, 5, 20), null, BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with null to date.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test the date from after the date to
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(2013, 5, 20), new Date(2013, 5, 15), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with the date from after the date to.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test date from in the past
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(2011, 5, 20), new Date(2013, 5, 15), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with the date from in the past.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test date to in the past
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(2011, 5, 20), new Date(2011, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with the date to in the past.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test null client
        reservation = newReservation(null, sampleRoom(), 
                new Date(2013, 5, 20), new Date(2013, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with null client.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // TODO Test client without id
        reservation = newReservation(null, sampleRoom(), 
                new Date(2013, 5, 20), new Date(2013, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with client not in the database.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test null room
        reservation = newReservation(sampleClient(), null, 
                new Date(2013, 5, 20), new Date(2013, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with null room.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // TODO Test room without id
        reservation = newReservation(sampleClient(), null, 
                new Date(2013, 5, 20), new Date(2013, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with room not in the database.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // TODO Test not vacant room
        
        // Test price less than zero
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(2013, 5, 20), new Date(2013, 5, 25), new BigDecimal(-1));
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with price less than zero.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
    }

    @Test
    public void testDeleteReservation() {
        Reservation reservation1 = sampleReservation();
        Reservation reservation2 = sampleReservation();
        reservation2.setFromDate(new Date(2013, 2, 10));
        
        reservationService.createReservation(reservation1);
        reservationService.createReservation(reservation2);
        
        assertNotNull(reservationService.getReservation(reservation1.getId()));
        assertNotNull(reservationService.getReservation(reservation2.getId()));
        
        reservationService.deleteReservation(reservation1);
        
        assertNull(reservationService.getReservation(reservation1.getId()));
        assertNotNull(reservationService.getReservation(reservation2.getId()));
    }

    @Test
    public void testUpdateReservation() {
        Reservation reservation = sampleReservation();
        
        reservationService.createReservation(reservation);
        
        assertNotNull(reservationService.getReservation(reservation.getId()));
        
        Date newFromDate = new Date(2013, 4, 10);
        Date newToDate = new Date(2013, 4, 20);
        
        reservation.setFromDate(newFromDate);
        reservation.setToDate(newToDate);
        
        reservationService.updateReservation(reservation);
        
        Reservation reservation2 = reservationService.getReservation(reservation.getId());
        
        assertEquals(newFromDate, reservation2.getFromDate());
        assertEquals(newToDate, reservation2.getToDate());
        assertEquals(reservation.getPrice(), reservation2.getPrice());
        assertEquals(reservation.getClient(), reservation2.getClient());
        assertEquals(reservation.getRoom(), reservation2.getRoom());
    }

    @Test
    public void testGetReservation() {
        Reservation reservation = sampleReservation();
        
        reservationService.createReservation(reservation);
        
        Reservation reservation1 = reservationService.getReservation(reservation.getId());
        Reservation reservation2 = reservationService.getReservation(reservation.getId());
        
        assertNotSame(reservation1, reservation2);
        assertEquals(reservation1, reservation2);
        assertEquals(reservation1.getClient(), reservation2.getClient());
        assertEquals(reservation1.getFromDate(), reservation2.getFromDate());
        assertEquals(reservation1.getToDate(), reservation2.getToDate());
        assertEquals(reservation1.getPrice(), reservation2.getPrice());
        assertEquals(reservation1.getRoom(), reservation2.getRoom());
    }

    @Test
    public void testFindAllReservations() {
        Reservation reservation1 = sampleReservation();
        Reservation reservation2 = sampleReservation();
        reservation2.setFromDate(new Date(2013, 2, 10));
        
        reservationService.createReservation(reservation1);
        reservationService.createReservation(reservation2);
        
        assertNotNull(reservationService.getReservation(reservation1.getId()));
        assertNotNull(reservationService.getReservation(reservation2.getId()));
        
        List<Reservation> reservations = reservationService.findAllReservations();
        
        assertThat(reservations, hasItems(reservation1, reservation2));
    }

    //@Test
    public void testFindReservationsByRoom() {
        // TODO
        
        Room room = sampleRoom();
        Reservation reservation = sampleReservation();
        reservation.setRoom(room);
        
        reservationService.createReservation(reservation);
    }

    //@Test
    public void testFindReservationsByClient() {
        // TODO
    }

    //@Test
    public void testFindReservationsByHotel() {
        // TODO
    }

    @Test
    public void testFindReservationsByDate_2args() {
        Reservation reservation1 = sampleReservation();
        reservation1.setFromDate(new Date(2013, 4, 10));
        reservation1.setToDate(new Date(2013, 4, 20));
        Reservation reservation2 = sampleReservation();
        reservation2.setFromDate(new Date(2013, 3, 10));
        reservation2.setToDate(new Date(2013, 3, 20));
        
        reservationService.createReservation(reservation1);
        reservationService.createReservation(reservation2);
        
        assertNotNull(reservationService.getReservation(reservation1.getId()));
        assertNotNull(reservationService.getReservation(reservation2.getId()));
        
        List<Reservation> reservations = reservationService.findReservationsByDate(
                new Date(2013, 3, 5), new Date(2013, 4, 25));
        
        assertThat(reservations, hasItems(reservation1, reservation2));
    }

    //@Test
    public void testFindReservationsByDate_3args() {
        Reservation reservation1 = sampleReservation();
        reservation1.setFromDate(new Date(2013, 4, 10));
        reservation1.setToDate(new Date(2013, 4, 20));
        Reservation reservation2 = sampleReservation();
        reservation2.setFromDate(new Date(2013, 3, 10));
        reservation2.setToDate(new Date(2013, 3, 20));
        
        // TODO room change
        //Hotel = 
        //reservation1.setRoom(null);
        
        reservationService.createReservation(reservation1);
        reservationService.createReservation(reservation2);
        
        assertNotNull(reservationService.getReservation(reservation1.getId()));
        assertNotNull(reservationService.getReservation(reservation2.getId()));
        
        List<Reservation> reservations = reservationService.findReservationsByDate(
                new Date(2013, 3, 5), new Date(2013, 4, 25), null);
        
        assertTrue(reservations.contains(reservation1));
        assertFalse(reservations.contains(reservation2));
    }
    
    private static Reservation sampleReservation() {
        return newReservation(sampleClient(), sampleRoom(), 
                new Date(2013, 5, 20), new Date(2013, 5, 25), BigDecimal.TEN);
    }
    
    private static Client sampleClient() {
        return newClient("Jozko", "Mrkvicka", sampleContact());
    }
    
    private static Contact sampleContact() {
        return newContact("12345", "jozko@mail.com", "Address", "City", "Country");
    }
    
    private static Room sampleRoom() {
        return newRoom(BigDecimal.TEN, sampleHotel());
    }
    
    private static Hotel sampleHotel() {
        return newHotel("Hilton", sampleContact());
    }
    
    private static Reservation newReservation(Client client, Room room,
                Date fromDate, Date toDate, BigDecimal price) {
        return App.DatabaseSampler.buildReservation(client, room, fromDate, toDate, price);
    }
    
    private static Client newClient(String firstName, String lastName, Contact contact) {
        return App.DatabaseSampler.buildClient(firstName, lastName, contact);
    }
    
    private static Contact newContact(String phone, String email, 
                String address, String city, String country) {
        return App.DatabaseSampler.buildContact(phone, email, address, city, country);
    }
    
    private static Room newRoom(BigDecimal pricePerNight, Hotel hotel) {
        return App.DatabaseSampler.buildRoom(pricePerNight, hotel);
    }
    
    private static Hotel newHotel(String name, Contact contact) {
        return App.DatabaseSampler.buildHotel(name, contact);
    }
}
