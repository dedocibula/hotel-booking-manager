package cz.fi.muni.pa165.hotelbookingmanagerapi.service;


import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ReservationTO;
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
     * @param reservationTO transfer object describing reservation to create.
     * @throws IllegalArgumentException if reservation is null, any of its attributes except id is null,
     *          reservation id is not null, the client/room doesn't exist or the room is not vacant.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void createReservation(ReservationTO reservationTO);

    /**
     * Removes existing Reservation it from database
     *
     * @param reservationTO transfer object describing reservation to remove.
     * @throws IllegalArgumentException if reservation id is null and see createReservation() method.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void deleteReservation(ReservationTO reservationTO);

    /**
     * Returns the transfer object describing reservation that has the specified ID.
     *
     * @param id id to search for.
     * @return reservation with corresponding id,
     *         null if such reservation doesn't exist.
     * @throws IllegalArgumentException if id is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    ReservationTO getReservation(Long id);

    /**
     * Returns transfer object describing all reservations.
     *
     * @return all reservations or empty list if there are none.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<ReservationTO> findAllReservations();
    
    /**
     * Return transfer object describing reservations within given date interval.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @return reservations within given date interval.
     * @throws IllegalArgumentException if any of the parameters is null 
     *          or the date from is after the date to.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<ReservationTO> findReservationsByDate(Date from, Date to);
    
    /**
     * Return transfer object describing reservations within given date interval in given hotel.
     * 
     * @param from start of the interval
     * @param to end of the interval
     * @param hotel hotel
     * @return reservations within given date interval in given hotel.
     * @throws IllegalArgumentException if any of the parameters is null, 
     *          the date from is after the date to or hotel id is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<ReservationTO> findReservationsByDate(Date from, Date to, HotelTO hotelTO);
    
}
