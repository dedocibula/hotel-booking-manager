package com.mycompany.hotelbookingmanager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Main class containing sampler
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HotelBookingManagerPU");
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
            
            Room room1 = buildRoom(BigDecimal.valueOf(150), true, hotel1);
            Room room2 = buildRoom(BigDecimal.valueOf(200), true, hotel1);
            Room room3 = buildRoom(BigDecimal.valueOf(100), true, hotel2);
            Room room4 = buildRoom(BigDecimal.valueOf(80), true, hotel2);
            
            Reservation reservation1 = buildReservation(client1, room2, new Date(112, 1, 1), new Date(112, 2, 4), BigDecimal.valueOf(5000));
            Reservation reservation2 = buildReservation(client3, room3, new Date(112, 4, 5), new Date(112, 5, 20), BigDecimal.valueOf(4200));
            
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
        
        public static Room buildRoom(BigDecimal pricePerNight, boolean vacant, Hotel hotel) {
            Room room = new Room();
            room.setPricePerNight(pricePerNight);
            room.setVacant(vacant);
            room.setHotel(hotel);
            if (hotel.getRooms() == null)
                hotel.setRooms(new ArrayList<Room>());
            hotel.getRooms().add(room);
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
    }
}
