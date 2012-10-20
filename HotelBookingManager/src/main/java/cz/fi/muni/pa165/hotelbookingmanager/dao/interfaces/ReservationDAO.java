package cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces;

import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
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
     * @throws IllegalArgumentException if reservation is null, any of its attributes is null,
     *         the client/room doesn't exist or the room is not vacant.
     * @throws ConstraintViolationException if reservation has any invalid parameter.
     */
    void create(Reservation reservation);

    /**
     * Removes existing Reservation it from database
     *
     * @param reservation reservation to remove.
     * @throws IllegalArgumentException if reservation is null.
     */
    void delete(Reservation reservation);

     
    /**
     * Updates the reservation according to the ID of the reservation in the parameter.
     *
     * @param reservation reservation to update.
     * @throws IllegalArgumentException if the reservation is null or if the client/room of the reservation are non-existent.
     * @throws ConstraintViolationException if reservation has any invalid parameter.
     */
    void update(Reservation reservation);

    /**
     * Returns the reservation that has the specified ID.
     *
     * @param id id to search for.
     * @return reservation with corresponding id,
     *         null if such reservation doesn't exist.
     * @throws IllegalArgumentException if id is null.
     */
    Reservation get(Long id);

    /**
     * Returns all reservations of client specified in the parameter.
     *
     * @return all reservations or empty list if there are none.
     */
    List<Reservation> findAllReservations();
      
}
