/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hotelbookingmanager;

import java.util.List;

/**
 *
 * @author Andrej
 */
public interface HotelDAO {
    
    /**
     * Adds new hotel to the database.
     * 
     * @param hotel hotel to add.
     * @throws IllegalArgumentException if parameter is null or has assigned id.
     */
    void create(Hotel hotel);
    
    /**
     * Returns hotel with given id.
     * 
     * @param id primary key of requested hotel.
     * @return hotel with given id or null if such hotel doesn't exist.
     * @throws IllegalArgumentException when given id is null.
     */
    Hotel get(Long id);
    
    /**
     * Updates existing hotel.
     * 
     * @param hotel hotel to update (specified by id) with new attributes.
     * @throws IllegalArgumentException if parameter is null.
     */
    void update(Hotel hotel);
    
    /**
     * Removes existing hotel.
     * 
     * @param hotel hotel to remove (specified by id).
     * @throws IllegalArgumentException if parameter is null.
     */
    void delete(Hotel hotel);
    
    /**
     * Returns list of all hotels in the database.
     * 
     * @return all hotels in the DB or empty list if there are none.
     */
    List<Hotel> findAll();
}
