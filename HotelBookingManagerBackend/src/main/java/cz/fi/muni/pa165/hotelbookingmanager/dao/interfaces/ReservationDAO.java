package cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces;

import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Marián Rusnák
 */
public interface ReservationDAO {
    
    /**
     * Creates a new Reservation in the database.
     *
     * @param reservation reservation to create.
     * @throws DataAccessException in case of error.
     */
    void create(Reservation reservation);

    /**
     * Removes existing Reservation it from database
     *
     * @param reservation reservation to remove.
     * @throws DataAccessException in case of error.
     */
    void delete(Reservation reservation);

     
    /**
     * Updates the reservation according to the ID of the reservation in the parameter.
     *
     * @param reservation reservation to update.
     * @throws DataAccessException in case of error.
     */
    void update(Reservation reservation);

    /**
     * Returns the reservation that has the specified ID.
     *
     * @param id id to search for.
     * @return reservation with corresponding id,
     *         null if such reservation doesn't exist.
     * @throws DataAccessException in case of error.
     */
    Reservation get(Long id);

    /**
     * Returns all reservations of client specified in the parameter.
     *
     * @return all reservations or empty list if there are none.
     * @throws DataAccessException in case of error.
     */
    List<Reservation> findAllReservations();
    
    /**
     * Return all reservations of given client.
     * 
     * @param client client
     * @return reservations of given client.
     * @throws DataAccessException in case of error.
     */
    List<Reservation> findReservationsByClient(Client client);
    
    /**
     * Return reservations within given date interval.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @return reservations within given date interval.
     * @throws DataAccessException in case of error.
     */
    List<Reservation> findReservationsByDate(Date from, Date to);
    
    /**
     * Return reservations withing given date interval in given hotel.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @param hotel hotel
     * @return reservations withing given date interval in given hotel.
     * @throws DataAccessException in case of error.
     */
    List<Reservation> findReservationsByDate(Date from, Date to, Hotel hotel);
}
