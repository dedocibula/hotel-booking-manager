package cz.fi.muni.pa165.hotelbookingmanager;

import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

/**
 * Main class containing sampler
 *
 */
public class App
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        JpaTransactionManager jtm = context.getBean(JpaTransactionManager.class);
        EntityManagerFactory emf = jtm.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        DatabaseSampler.feedDatabase(em);
        System.out.println( "Database is working!" );
    }

    public static class DatabaseSampler {

        public static void feedDatabase(EntityManager em) {
            Contact contact1 = buildContact("123", "a@b.sk", "Diera", "Brno", "Czech Republic");
            Contact contact2 = buildContact("321", "b@a.sk", "Heaven", "Praha", "Czech Republic");
            Contact contact3 = buildContact("222", "b@b.sk", "Purgatory", "Bratislava", "Slovakia");

            Contact contact4 = buildContact("888", "h1@h.sk", "Cowley", "London", "UK");
            Contact contact5 = buildContact("999", "h2@h.sk", "Warvick", "Oxford", "UK");

            Client client1 = buildClient("Fero", "Taraba", contact3);
            Client client2 = buildClient("Vaclav", "Havel", contact2);
            Client client3 = buildClient("Pepa", "Brandejs", contact1);

            Hotel hotel1 = buildHotel("Hilton", contact4);
            Hotel hotel2 = buildHotel("Crowne", contact5);

            Room room1 = buildRoom(BigDecimal.valueOf(150), hotel1);
            Room room2 = buildRoom(BigDecimal.valueOf(200), hotel1);
            Room room3 = buildRoom(BigDecimal.valueOf(100), hotel2);
            Room room4 = buildRoom(BigDecimal.valueOf(80), hotel2);

            Reservation reservation1 = buildReservation(client1, room2, new Date(115, 1, 1), new Date(115, 2, 4), BigDecimal.valueOf(5000));
            Reservation reservation2 = buildReservation(client3, room3, new Date(115, 4, 5), new Date(115, 5, 20), BigDecimal.valueOf(4200));

            em.getTransaction().begin();
            em.persist(client1);
            em.persist(client2);
            em.persist(client3);
            em.persist(hotel1);
            em.persist(hotel2);
            em.persist(room1);
            em.persist(room2);
            em.persist(room3);
            em.persist(room4);
            em.persist(reservation1);
            em.persist(reservation2);
            em.getTransaction().commit();
        }

        public static Client buildClient(String firstName, String lastName, Contact contact) {
            Client client = new Client();
            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setContact(contact);
            return client;
        }

        public static Contact buildContact(String phone, String email,
                String address, String city, String country) {
            Contact contact = new Contact();
            contact.setPhone(phone);
            contact.setEmail(email);
            contact.setAddress(address);
            contact.setCity(city);
            contact.setCountry(country);
            return contact;
        }

        public static Hotel buildHotel(String name, Contact contact) {
            Hotel hotel = new Hotel();
            hotel.setName(name);
            hotel.setContact(contact);
            return hotel;
        }
        
        public static Hotel buildHotelWithRooms(String name, Contact contact, Room... rooms) {
            Hotel hotel = new Hotel();
            hotel.setName(name);
            hotel.setContact(contact);
            if (hotel.getRooms() == null)
                hotel.setRooms(new ArrayList<Room>());
            hotel.getRooms().addAll(Arrays.asList(rooms));
            return hotel;
        }

        public static Room buildRoom(BigDecimal pricePerNight, Hotel hotel) {
            Room room = new Room();
            room.setPricePerNight(pricePerNight);
            room.setHotel(hotel);
            if (hotel != null)
            {
                if (hotel.getRooms() == null)
                    hotel.setRooms(new ArrayList<Room>());
                hotel.getRooms().add(room);
            }
            return room;
        }

        public static Reservation buildReservation(Client client, Room room,
                Date fromDate, Date toDate, BigDecimal price) {
            Reservation reservation = new Reservation();
            reservation.setClient(client);
            reservation.setRoom(room);
            reservation.setFromDate(fromDate);
            reservation.setToDate(toDate);
            reservation.setPrice(price);
            return reservation;
        }
        
        public static ClientTO buildClientTO(String firstName, String lastName, ContactTO contact) {
            ClientTO clientTO = new ClientTO();
            clientTO.setFirstName(firstName);
            clientTO.setLastName(lastName);
            clientTO.setContact(contact);
            return clientTO;
        }

        public static ContactTO buildContactTO(String phone, String email,
                String address, String city, String country) {
            ContactTO contactTO = new ContactTO();
            contactTO.setPhone(phone);
            contactTO.setEmail(email);
            contactTO.setAddress(address);
            contactTO.setCity(city);
            contactTO.setCountry(country);
            return contactTO;
        }

        public static HotelTO buildHotelTO(String name, ContactTO contact) {
            HotelTO hotelTO = new HotelTO();
            hotelTO.setName(name);
            hotelTO.setContact(contact);
            return hotelTO;
        }
        
        public static HotelTO buildHotelTOWithRooms(String name, ContactTO contact, RoomTO... rooms) {
            HotelTO hotelTO = new HotelTO();
            hotelTO.setName(name);
            hotelTO.setContact(contact);
            if (hotelTO.getRooms() == null)
                hotelTO.setRooms(new ArrayList<RoomTO>());
            hotelTO.getRooms().addAll(Arrays.asList(rooms));
            return hotelTO;
        }

        public static RoomTO buildRoomTO(BigDecimal pricePerNight, HotelTO hotel) {
            RoomTO roomTO = new RoomTO();
            roomTO.setPricePerNight(pricePerNight);
            roomTO.setHotel(hotel);
            if (hotel != null)
            {
                if (hotel.getRooms() == null)
                    hotel.setRooms(new ArrayList<RoomTO>());
                hotel.getRooms().add(roomTO);
            }
            return roomTO;
        }

        public static ReservationTO buildReservationTO(ClientTO client, RoomTO room,
                Date fromDate, Date toDate, BigDecimal price) {
            ReservationTO reservationTO = new ReservationTO();
            reservationTO.setClient(client);
            reservationTO.setRoom(room);
            reservationTO.setFromDate(fromDate);
            reservationTO.setToDate(toDate);
            reservationTO.setPrice(price);
            return reservationTO;
        }
    }
}
