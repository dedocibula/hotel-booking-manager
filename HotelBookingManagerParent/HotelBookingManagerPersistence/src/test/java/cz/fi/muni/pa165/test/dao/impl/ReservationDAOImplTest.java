package cz.fi.muni.pa165.test.dao.impl;



import cz.fi.muni.pa165.hotelbookingmanagerpersistence.App;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.ReservationDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Contact;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Room;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Filip Bogyai
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ReservationDAOImplTest {

    ReservationDAO reservationDAO;
    EntityManager em;

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        reservationDAO = context.getBean(ReservationDAO.class);
        em = context.getBean(EntityManagerFactory.class).createEntityManager();
    }

    @After
    public void tearDown() {
        reservationDAO = null;
    }

    @Test
    public void testCreateReservation() {

        //Create a null Reservation
        try {
            reservationDAO.create(null);
            fail("No DataAccessException thrown while creating a null Reservation.");
        } catch (DataAccessException iae) {
            //Works as intended
        }
        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk",
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));

        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(client);
        em.persist(room);
        em.getTransaction().commit();


        reservationDAO.create(reservation);
        Long id = reservation.getId();

        Reservation reservation2 = reservationDAO.get(id);
        assertThat(reservation2, is(notNullValue()));
        assertThat(reservation2, is(not(sameInstance(reservation))));
        assertThat(reservation2, is(equalTo(reservation)));
    }

    @Test
    public void testCreateReservationWithWrongAttributes() {

        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));

        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(client);
        em.persist(room);
        em.getTransaction().commit();

        //while creating Reservation with null client
        try {
            reservation.setClient(null);
            reservationDAO.create(reservation);
            fail("No ConstraintViolationException thrown while creating Reservation with null client.");
        } catch (ConstraintViolationException iae) {
            //Works as intended
        }

        //creating Reservation with null room
        try {
            em.getTransaction().begin();
            em.find(Client.class, client.getId());
            reservation.setClient(client);
            reservation.setRoom(null);
            em.getTransaction().commit();
            reservationDAO.create(reservation);
            fail("No ConstraintViolationException thrown while creating Reservation with null room.");
        } catch (ConstraintViolationException iae) {
            //Works as intended
        }

        //creating Reservation with null price
         try {
            reservation.setRoom(room);
            reservation.setPrice(null);
            reservationDAO.create(reservation);
            fail("No ConstraintViolationException thrown while creating Reservation with null price.");
        } catch (ConstraintViolationException e) {
            //Works as intended
        }

        //creating Reservation with null date
        try {
            reservation.setPrice(BigDecimal.valueOf(1580));
            reservation.setFromDate(null);
            reservationDAO.create(reservation);
            fail("No ConstraintViolationException thrown while creating Reservation with null date.");
        } catch (ConstraintViolationException e) {
            //Works as intended
        }

    }



    @Test
    public void testGetReservation() {
        assertThat(reservationDAO.get(0l), is(nullValue()));
        try {
            reservationDAO.get(null);
            fail("No DataAccessException for null id");
        } catch (DataAccessException e) {
            //Ok
        }

        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));

        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(client);
        em.persist(room);
        em.getTransaction().commit();
        reservationDAO.create(reservation);

        Reservation testReservation1 = reservationDAO.get(reservation.getId());
        Reservation testReservation2 = reservationDAO.get(reservation.getId());

        assertThat(testReservation1, is(not(sameInstance(testReservation2))));
        assertThat(testReservation1, is(equalTo(testReservation2)));
        assertThat(testReservation1.getClient(), is(equalTo(testReservation2.getClient())));
        assertThat(testReservation1.getFromDate(), is(equalTo(testReservation2.getFromDate())));
        assertThat(testReservation1.getToDate(), is(equalTo(testReservation2.getToDate())));
        assertThat(testReservation1.getPrice(), is(equalTo(testReservation2.getPrice())));
        assertThat(testReservation1.getRoom(), is(equalTo(testReservation2.getRoom())));

    }

    /**
     * Test of update method, of class ReservationDAOImpl.
     */
    @Test
    public void testUpdateReservation() {
        try {
            reservationDAO.update(null);
            fail("No DataAccessException for empty input.");
        } catch (DataAccessException e) {
            //OK
        }
        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation1 = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));

        Contact contact2 = App.DatabaseSampler.buildContact("7", "smajda@azet.sk", 
                "address", "city", "country");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crown", contact2);
        Client client2 = App.DatabaseSampler.buildClient("Adolf", "Suchy" , contact2);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(69), hotel2);
        Reservation reservation2 = App.DatabaseSampler.buildReservation(client2, room2, 
                new Date(2016, 5, 20), new Date(2016, 7, 21), BigDecimal.valueOf(410));

        Room room3 = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(32), hotel2);
        
        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(hotel2);
        em.persist(client);
        em.persist(client2);
        em.persist(room);
        em.persist(room2); 
        em.persist(room3);
        em.getTransaction().commit();
        reservationDAO.create(reservation1);
        reservationDAO.create(reservation2);
        
        // changing reservation client
        reservation1.setClient(client2);
        reservationDAO.update(reservation1);
        Reservation temp = reservationDAO.get(reservation1.getId());
        assertThat("Reservation client not updated", temp.getClient(), is(equalTo(client2)));
        
        
        // changing reservation price
        reservation1.setPrice(BigDecimal.valueOf(10));
        reservationDAO.update(reservation1);
        temp = reservationDAO.get(reservation1.getId());
        assertThat("Reservation price not updated", temp.getPrice().toBigInteger(), 
                is(equalTo(BigDecimal.valueOf(10).toBigInteger())));
        
        
        // changing reservation FromDate
        reservation1.setFromDate(new Date(2014,1,1));
        reservationDAO.update(reservation1);
        temp = reservationDAO.get(reservation1.getId());
        assertThat("Reservation name fromDate not updated", temp.getFromDate(), 
                is(equalTo((new Date(2014,1,1)))));
                
        // changing reservation ToDate
        reservation1.setToDate(new Date(2017,2,2));
        reservationDAO.update(reservation1);
        temp = reservationDAO.get(reservation1.getId());
        assertThat("Reservation toDate not updated", temp.getToDate(), 
                is(equalTo(new Date(2017,2,2))));
       
        // changing reservation city
        reservation1.setRoom(room3);
        reservationDAO.update(reservation1);
        temp = reservationDAO.get(reservation1.getId());
        assertThat("Reservation room not updated ", temp.getRoom(), is(equalTo(room3)));
             
       
    }

    /**
     * Test of delete method, of class ReservationDAOImpl.
     */
    @Test
    public void testDeleteReservation() {
        try {
            reservationDAO.delete(null);
            fail("Did not throw DataAccessException on deleting null Reservation");
        } catch (Exception e) {
            //OK
        }
        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation1 = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));    
                
        Contact contact2 = App.DatabaseSampler.buildContact("7", "smajda@azet.sk", 
                "address", "city", "country");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crown", contact2);
        Client client2 = App.DatabaseSampler.buildClient("Adolf", "Suchy" , contact2);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(69), hotel2);
        Reservation reservation2 = App.DatabaseSampler.buildReservation(client2, room2, 
                new Date(2016, 5, 20), new Date(2016, 7, 21), BigDecimal.valueOf(410));  
        
        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(hotel2);
        em.persist(client);
        em.persist(client2);
        em.persist(room);
        em.persist(room2);       
        em.getTransaction().commit();
        reservationDAO.create(reservation1);
        reservationDAO.create(reservation2);
        
        assertThat(reservationDAO.get(reservation1.getId()), is(notNullValue()));
        assertThat(reservationDAO.get(reservation2.getId()), is(notNullValue()));
        
        reservationDAO.delete(reservation1);
        
        assertThat(reservationDAO.get(reservation1.getId()), is(nullValue()));
        assertThat(reservationDAO.get(reservation2.getId()), is(notNullValue()));
    }

    /**
     * Test of findAll method, of class ReservationDAOImpl.
     */
    @Test
    public void testFindAllReservations() {
        for (Reservation reservation : reservationDAO.findAllReservations()) {
            reservationDAO.delete(reservation);
        }
        assertTrue(reservationDAO.findAllReservations().isEmpty());
        
        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation1 = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));    
                
        Contact contact2 = App.DatabaseSampler.buildContact("7", "smajda@azet.sk", 
                "address", "city", "country");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crown", contact2);
        Client client2 = App.DatabaseSampler.buildClient("Adolf", "Suchy" , contact2);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(69), hotel2);
        Reservation reservation2 = App.DatabaseSampler.buildReservation(client2, room2, 
                new Date(2014, 5, 20), new Date(2014, 7, 21), BigDecimal.valueOf(410));  
        
        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(hotel2);
        em.persist(client);
        em.persist(client2);
        em.persist(room);
        em.persist(room2);       
        em.getTransaction().commit();
        reservationDAO.create(reservation1);
        reservationDAO.create(reservation2);
        
        assertThat(reservationDAO.findAllReservations(), hasItems(reservation1, reservation2));
    }
    
    @Test
    public void testFindReservationsByClient() {
        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation1 = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));    
                
        Contact contact2 = App.DatabaseSampler.buildContact("7", "smajda@azet.sk", 
                "address", "city", "country");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crown", contact2);
        Client client2 = App.DatabaseSampler.buildClient("Adolf", "Suchy" , contact2);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(69), hotel2);
        Reservation reservation2 = App.DatabaseSampler.buildReservation(client2, room2, 
                new Date(2014, 5, 20), new Date(2014, 7, 21), BigDecimal.valueOf(410));  
        
        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(hotel2);
        em.persist(client);
        em.persist(client2);
        em.persist(room);
        em.persist(room2);       
        em.getTransaction().commit();
        reservationDAO.create(reservation1);
        reservationDAO.create(reservation2);
        
        List<Reservation> result = reservationDAO.findReservationsByClient(client);
        
        assertTrue(result.contains(reservation1));
        assertFalse(result.contains(reservation2));
    }
    
    @Test
    public void testFindReservationsByClientWithNull() {
        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation1 = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));    
                
        Contact contact2 = App.DatabaseSampler.buildContact("7", "smajda@azet.sk", 
                "address", "city", "country");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crown", contact2);
        Client client2 = App.DatabaseSampler.buildClient("Adolf", "Suchy" , contact2);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(69), hotel2);
        Reservation reservation2 = App.DatabaseSampler.buildReservation(client2, room2, 
                new Date(2014, 5, 20), new Date(2014, 7, 21), BigDecimal.valueOf(410));  
        
        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(hotel2);
        em.persist(client);
        em.persist(client2);
        em.persist(room);
        em.persist(room2);       
        em.getTransaction().commit();
        reservationDAO.create(reservation1);
        reservationDAO.create(reservation2);
        
        List<Reservation> result = reservationDAO.findReservationsByClient(null);
        
        assertFalse(result.contains(reservation1));
        assertFalse(result.contains(reservation2));
    }
    
    @Test
    public void testFindReservationsByDate_3args() {
        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation1 = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));    
                
        Contact contact2 = App.DatabaseSampler.buildContact("7", "smajda@azet.sk", 
                "address", "city", "country");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crown", contact2);
        Client client2 = App.DatabaseSampler.buildClient("Adolf", "Suchy" , contact2);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(69), hotel2);
        Reservation reservation2 = App.DatabaseSampler.buildReservation(client2, room2, 
                new Date(2014, 5, 20), new Date(2014, 7, 21), BigDecimal.valueOf(410));  
        
        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(hotel2);
        em.persist(client);
        em.persist(client2);
        em.persist(room);
        em.persist(room2);       
        em.getTransaction().commit();
        reservationDAO.create(reservation1);
        reservationDAO.create(reservation2);
        
        Date from = new Date(2014, 5, 1);
        Date to = new Date(2014, 7, 1);
        
        List<Reservation> result = reservationDAO.findReservationsByDate(from, to, hotel);
        
        assertTrue(result.contains(reservation1));
        assertFalse(result.contains(reservation2));
    }
    
    @Test
    public void testFindReservationsByDate_3args_WithNulls() {
        Contact contact = App.DatabaseSampler.buildContact("13", "blondina@azet.sk", 
                "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        Client client = App.DatabaseSampler.buildClient("Jozko", "Morky" , contact);
        Room room = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(777), hotel);
        Reservation reservation1 = App.DatabaseSampler.buildReservation(client, room, 
                new Date(2014, 5, 2), new Date(2014, 6, 2), BigDecimal.valueOf(1580));    
                
        Contact contact2 = App.DatabaseSampler.buildContact("7", "smajda@azet.sk", 
                "address", "city", "country");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crown", contact2);
        Client client2 = App.DatabaseSampler.buildClient("Adolf", "Suchy" , contact2);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.valueOf(69), hotel2);
        Reservation reservation2 = App.DatabaseSampler.buildReservation(client2, room2, 
                new Date(2014, 5, 20), new Date(2014, 7, 21), BigDecimal.valueOf(410));  
        
        em.getTransaction().begin();
        em.persist(hotel);
        em.persist(hotel2);
        em.persist(client);
        em.persist(client2);
        em.persist(room);
        em.persist(room2);       
        em.getTransaction().commit();
        reservationDAO.create(reservation1);
        reservationDAO.create(reservation2);
        
        Date from = new Date(2014, 5, 1);
        Date to = new Date(2014, 7, 1);
        
        List<Reservation> result = reservationDAO.findReservationsByDate(null, null, hotel);
        
        assertFalse(result.contains(reservation1));
        assertFalse(result.contains(reservation2));
    }
}

