/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hotelbookingmanager;

import java.util.List;

/**
 * DAO Interface for the entity Reservation
 * 
 * @author Marian
 */
public interface ReservationDAO {
    
    /**
     * Creates a new Reservation in the database.
     *
     * @param reservation reservation to create.
     * @throws IllegalArgumentException if reservation is null, any of its attributes is null,
     *         the client/room doesn't exist or the room is not vacant.
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
