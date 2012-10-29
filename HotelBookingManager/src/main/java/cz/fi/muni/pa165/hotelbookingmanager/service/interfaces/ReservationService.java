package cz.fi.muni.pa165.hotelbookingmanager.service.interfaces;

import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
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
     * @throws IllegalArgumentException if reservation is null, any of its attributes is null,
     *         the client/room doesn't exist or the room is not vacant.
     * @throws ConstraintViolationException if reservation has any invalid parameter.
     */
    void createReservation(Reservation reservation);

    /**
     * Removes existing Reservation it from database
     *
     * @param reservation reservation to remove.
     * @throws IllegalArgumentException if reservation is null.
     */
    void deleteReservation(Reservation reservation);

     
    /**
     * Updates the reservation according to the ID of the reservation in the parameter.
     *
     * @param reservation reservation to update.
     * @throws IllegalArgumentException if the reservation is null or if the client/room of the reservation are non-existent.
     * @throws ConstraintViolationException if reservation has any invalid parameter.
     */
    void updateReservation(Reservation reservation);

    /**
     * Returns the reservation that has the specified ID.
     *
     * @param id id to search for.
     * @return reservation with corresponding id,
     *         null if such reservation doesn't exist.
     * @throws IllegalArgumentException if id is null.
     */
    Reservation getReservation(Long id);

    /**
     * Returns all reservations.
     *
     * @return all reservations or empty list if there are none.
     */
    List<Reservation> findAllReservations();
    
    /**
     * 
     * @param room
     * @return 
     */
    List<Reservation> findReservationsByRoom(Room room);
    
    /**
     * 
     * @param client
     * @return 
     */
    List<Reservation> findReservationsByClient(Client client);
    
    /**
     * 
     * @param hotel
     * @return 
     */
    List<Reservation> findReservationsByHotel(Hotel hotel);
    
    /**
     * 
     * @param from
     * @param to
     * @return 
     */
    List<Reservation> findReservationsByDate(Date from, Date to);
    
    /**
     * 
     * @param from
     * @param to
     * @param hotel
     * @return 
     */
    List<Reservation> findReservationsByDate(Date from, Date to, Hotel hotel);
    
}
