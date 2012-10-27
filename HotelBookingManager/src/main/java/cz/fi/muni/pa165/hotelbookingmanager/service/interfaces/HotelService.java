/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanager.service.interfaces;

import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import java.util.List;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Andrej Galád
 */
public interface HotelService {
    
    /**
     * Creates new hotel.
     * 
     * @param hotel hotel to create.
     * @throws IllegalArgumentException if hotel is null or has assigned id.
     *         if hotel has rooms attached and any of them is null.
     *         if any of hotel's rooms is already created.
     *         if validation for hotel attributes fails.
     *         if validation for hotel's rooms fails provided it has any.
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    void createHotel(Hotel hotel);
    
    /**
     * Returns hotel with given id.
     * 
     * @param id primary key of requested hotel.
     * @return hotel with given id or null if such hotel doesn't exist.
     * @throws IllegalArgumentException when given id is null.
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    Hotel findHotel(Long id);
    
    /**
     * Updates existing hotel.
     * 
     * @param hotel hotel to update (specified by id) with new attributes.
     * @throws IllegalArgumentException if hotel is null or he doesn't exist in database.
     *         if hotel has rooms attached and any of them is null.
     *         if any of hotel's rooms is already created and doesn't have hotel attached.
     *         if validation for hotel attributes fails.
     *         if validation for hotel's rooms fails provided it has any.   
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    void updateHotel(Hotel hotel);
    
    /**
     * Removes existing hotel.
     * 
     * @param hotel hotel to remove (specified by id).
     * @throws IllegalArgumentException if parameter is null.
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    void deleteHotel(Hotel hotel);
    
    /**
     * Returns list of all hotels.
     * 
     * @return all hotels in the DB or empty list if there are none. 
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    List<Hotel> findAllHotels();
    
    /**
     * Returns list of hotels with given name.
     * 
     * @param name name of requested hotels
     * @return all hotels with given name or empty list if there are none.
     * @throws IllegalArgumentException if parameter is null or empty string.
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    List<Hotel> findHotelsByName(String name);
    
    /**
     * Returns list of hotels with given address.
     * 
     * @param address address of requested hotels
     * @return all hotels with given address or empty list if there are none.
     * @throws IllegalArgumentException if parameter is null or empty string.
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    List<Hotel> findHotelsByAddress(String address);
    
    /**
     * Returns list of hotels with given city.
     * 
     * @param city city of requested hotels
     * @return all hotels with given city or empty list if there are none.
     * @throws IllegalArgumentException if parameter is null or empty string.
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    List<Hotel> findHotelsByCity(String city);
    
    /**
     * Returns list of hotels with given country.
     * 
     * @param country country of requested hotels
     * @return all hotels with given country or empty list if there are none.
     * @throws IllegalArgumentException if parameter is null or empty string.
     * @throws DataAccessException in case of any exception on persistence layer.
     */
    List<Hotel> findHotelsByCountry(String country);
}
