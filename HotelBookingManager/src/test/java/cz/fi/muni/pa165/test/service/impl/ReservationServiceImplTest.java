/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.test.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ReservationDAO;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanager.service.impl.ReservationServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ClientService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.*;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.Validator;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marian Rusnak
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ReservationServiceImplTest {
    
    private ReservationDAO mockReservationDao;
    private ReservationServiceImpl reservationService;
    
    private ClientService clientService;
    private RoomService roomService;
    private HotelService hotelService;
    
    private Mapper mapper;
            
    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        
        reservationService = new ReservationServiceImpl();
        
        mockReservationDao = mock(ReservationDAO.class);
        
        clientService = context.getBean(ClientService.class);
        roomService = context.getBean(RoomService.class);
        hotelService = context.getBean(HotelService.class);
        
        ClientDAO clientDao = context.getBean(ClientDAO.class);
        RoomDAO roomDao = context.getBean(RoomDAO.class);
        Validator validator = context.getBean("validator", org.springframework.validation.beanvalidation.LocalValidatorFactoryBean.class);
        mapper = context.getBean(DozerBeanMapper.class);
        
        reservationService.setReservationDAO(mockReservationDao);
        reservationService.setClientDAO(clientDao);
        reservationService.setRoomDAO(roomDao);
        reservationService.setValidator(validator);
        reservationService.setMapper(mapper);
        //MockitoAnnotations.initMocks(this);
    }
    
    @After
    public void tearDown() {
        reservationService = null;
    }

    @Test
    public void testCreateReservation() {
        ReservationTO reservation = sampleReservation();
        
        reservationService.createReservation(reservation);
        verify(mockReservationDao).create(mapper.map(reservation, Reservation.class));
    }
    
    @Test
    public void testCreateReservationWithWrongAttributes() {
        // Test null
        try {
            reservationService.createReservation(null);
            fail("Cannot create null reservation.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test of ID already set
        ReservationTO reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(113, 5, 20), new Date(113, 5, 25), BigDecimal.ZERO);
        reservation.setId(10L);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with id already set.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test null from date
        reservation = newReservation(sampleClient(), sampleRoom(), 
                null, new Date(113, 5, 20), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with null from date.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test null to date
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(113, 5, 20), null, BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with null to date.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test the date from after the date to
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(113, 5, 20), new Date(113, 5, 15), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with the date from after the date to.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test date from in the past
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(111, 5, 20), new Date(113, 5, 15), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with the date from in the past.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test date to in the past
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(111, 5, 20), new Date(111, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with the date to in the past.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test null client
        reservation = newReservation(null, sampleRoom(), 
                new Date(113, 5, 20), new Date(113, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with null client.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test client without id
        ClientTO client = newClient("Jozko", "Mrkvicka", sampleContact());
        reservation = newReservation(client, sampleRoom(), 
                new Date(113, 5, 20), new Date(113, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with client not in the database.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test null room
        reservation = newReservation(sampleClient(), null, 
                new Date(113, 5, 20), new Date(113, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with null room.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test room without id
        RoomTO room = newRoom(BigDecimal.TEN, sampleHotel());
        reservation = newReservation(sampleClient(), room, 
                new Date(113, 5, 20), new Date(113, 5, 25), BigDecimal.ZERO);
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with room not in the database.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // TODO Test not vacant room
//        Room nonVacantRoom = newRoom(BigDecimal.TEN, sampleHotel());
//        roomService.createRoom(nonVacantRoom);
//        reservation = newReservation(sampleClient(), nonVacantRoom, 
//                new Date(113, 5, 20), new Date(113, 5, 25), BigDecimal.ZERO);
//        reservationService.createReservation(reservation);
//        try {
//            reservationService.createReservation(reservation);
//            fail("Cannot create reservation on room that is not vacant.");
//        } catch (IllegalArgumentException ex) {
//            // OK
//        }
        
        // Test price less than zero
        reservation = newReservation(sampleClient(), sampleRoom(), 
                new Date(113, 5, 20), new Date(113, 5, 25), new BigDecimal(-1));
        try {
            reservationService.createReservation(reservation);
            fail("Cannot create reservation with price less than zero.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        verify(mockReservationDao, never()).create(any(Reservation.class));
    }

    @Test
    public void testDeleteReservation() {
        ReservationTO reservation1 = sampleReservation();
        ReservationTO reservation2 = sampleReservation();
        reservation2.setFromDate(new Date(113, 2, 10));
        reservation1.setId(1L);
        reservation2.setId(2L);

        reservationService.deleteReservation(reservation1);
        
        verify(mockReservationDao).delete(mapper.map(reservation1, Reservation.class));
        verify(mockReservationDao, never()).delete(mapper.map(reservation2, Reservation.class));
    }

    @Test
    public void testUpdateReservation() {
        ReservationTO reservation = sampleReservation();
        reservation.setId(1L);
        
        reservationService.updateReservation(reservation);
        
        verify(mockReservationDao).update(mapper.map(reservation, Reservation.class));
    }

    @Test
    public void testGetReservation() {
        reservationService.getReservation(1L);
        
        verify(mockReservationDao).get(1L);
    }

    @Test
    public void testFindAllReservations() {
        reservationService.findAllReservations();
        
        verify(mockReservationDao).findAllReservations();
    }

    @Test
    public void testFindReservationsByClient() {
        ClientTO client = sampleClient();
        client.setId(1L);
        
        reservationService.findReservationsByClient(client);
        
        verify(mockReservationDao).findReservationsByClient(mapper.map(client, Client.class));
    }
    
    @Test
    public void testFindReservationsByClientWithWrongAttributes() {
        // Test with null client
        try {
            reservationService.findReservationsByClient(null);
            fail("Cannot find reservations of null client.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test with client with null id
        ClientTO client = newClient("Jozko", "Mrkvicka", sampleContact());
        try {
            reservationService.findReservationsByClient(client);
            fail("Cannot find reservations of client with null id.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        verify(mockReservationDao, never()).findReservationsByClient(any(Client.class));
    }

    @Test
    public void testFindReservationsByDate_3args() {
        Date fromDate = new Date(100, 1, 1);
        Date toDate = new Date(101, 2, 2);
        HotelTO hotel = sampleHotel();
        
        reservationService.findReservationsByDate(fromDate, toDate, hotel);
        
        verify(mockReservationDao).findReservationsByDate(fromDate, toDate, mapper.map(hotel, Hotel.class));
    }
    
    @Test
    public void testFindReservationsByDate_3argsWithWrongAttributes() {      
        Date fromDate = new Date(100, 1, 1);
        Date toDate = new Date(101, 2, 2);
        HotelTO hotel = sampleHotel();
        hotel.setId(1L);
        
        // Test with null from date
        try {
            reservationService.findReservationsByDate(null, toDate, hotel);
            fail("From date cannot be null.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test with null to date
        try {
            reservationService.findReservationsByDate(fromDate, null, hotel);
            fail("To date cannot be null.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test with from date after to date
        try {
            reservationService.findReservationsByDate(toDate, fromDate, hotel);
            fail("From date must be after to date.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test with null hotel
        try {
            reservationService.findReservationsByDate(fromDate, toDate, null);
            fail("Hotel cannot be null.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        // Test with hotel with null id
        hotel.setId(null);
        try {
            reservationService.findReservationsByDate(fromDate, null, hotel);
            fail("To date cannot be null.");
        } catch (IllegalArgumentException ex) {
            // OK
        }
        
        verify(mockReservationDao, never()).findReservationsByDate(any(Date.class), 
                any(Date.class), any(Hotel.class));
    }
    
    private ReservationTO sampleReservation() {
        return newReservation(sampleClient(), sampleRoom(), 
                new Date(113, 5, 20), new Date(113, 5, 25), BigDecimal.TEN);
    }
    
    private ClientTO sampleClient() {
        ClientTO client = newClient("Jozko", "Mrkvicka", sampleContact());
        clientService.createClient(client);
        return client;
    }
    
    private static ContactTO sampleContact() {
        return newContact("12345", "jozko@mail.com", "Address", "City", "Country");
    }
    
    private RoomTO sampleRoom() {
        RoomTO room = newRoom(BigDecimal.TEN, sampleHotel());
        roomService.createRoom(room);
        return room;
    }
    
    private HotelTO sampleHotel() {
        HotelTO hotel = newHotel("Hilton", sampleContact());
        hotelService.createHotel(hotel);
        return hotel;
    }
    
    private static ReservationTO newReservation(ClientTO client, RoomTO room,
                Date fromDate, Date toDate, BigDecimal price) {
        return App.DatabaseSampler.buildReservationTO(client, room, fromDate, toDate, price);
    }
    
    private static ClientTO newClient(String firstName, String lastName, ContactTO contact) {
        return App.DatabaseSampler.buildClientTO(firstName, lastName, contact);
    }
    
    private static ContactTO newContact(String phone, String email, 
                String address, String city, String country) {
        return App.DatabaseSampler.buildContactTO(phone, email, address, city, country);
    }
    
    private static RoomTO newRoom(BigDecimal pricePerNight, HotelTO hotel) {
        return App.DatabaseSampler.buildRoomTO(pricePerNight, hotel);
    }
    
    private static HotelTO newHotel(String name, ContactTO contact) {
        return App.DatabaseSampler.buildHotelTO(name, contact);
    }
}
