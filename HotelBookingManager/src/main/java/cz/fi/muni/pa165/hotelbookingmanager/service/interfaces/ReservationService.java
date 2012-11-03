package cz.fi.muni.pa165.hotelbookingmanager.service.interfaces;

import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Marian Rusnak
 */
public interface ReservationService {
    
    /**
     * Creates a new Reservation in the database.
     *
     * @param reservation reservation to create.
     * @throws IllegalArgumentException if reservation is null, any of its attributes except id is null,
     *          reservation id is not null, the client/room doesn't exist or the room is not vacant.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void createReservation(Reservation reservation);

    /**
     * Removes existing Reservation it from database
     *
     * @param reservation reservation to remove.
     * @throws IllegalArgumentException if reservation id is null and see createReservation() method.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void deleteReservation(Reservation reservation);

     
    /**
     * Updates the reservation according to the ID of the reservation in the parameter.
     *
     * @param reservation reservation to update.
     * @throws IllegalArgumentException if reservation id is null and see createReservation() method.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void updateReservation(Reservation reservation);

    /**
     * Returns the reservation that has the specified ID.
     *
     * @param id id to search for.
     * @return reservation with corresponding id,
     *         null if such reservation doesn't exist.
     * @throws IllegalArgumentException if id is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    Reservation getReservation(Long id);

    /**
     * Returns all reservations.
     *
     * @return all reservations or empty list if there are none.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<Reservation> findAllReservations();
    
    /**
     * Return all reservations of given client.
     * 
     * @param client client
     * @return reservations of given client.
     * @throws IllegalArgumentException if client is null or client's id is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<Reservation> findReservationsByClient(Client client);
    
    /**
     * Return reservations within given date interval.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @return reservations within given date interval.
     * @throws IllegalArgumentException if any of the parameters is null 
     *          or the date from is after the date to.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<Reservation> findReservationsByDate(Date from, Date to);
    
    /**
     * Return reservations withing given date interval in given hotel.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @param hotel hotel
     * @return reservations withing given date interval in given hotel.
     * @throws IllegalArgumentException if any of the parameters is null, 
     *          the date from is after the date to or hotel id is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<Reservation> findReservationsByDate(Date from, Date to, Hotel hotel);
    
}
