package cz.fi.muni.pa165.hotelbookingmanagerapi.service;


import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import java.util.List;


/**
 *
 * @author Andrej Galád
 */
public interface HotelService {
    
    /**
     * Creates new hotel.
     * 
     * @param hotelTO transfer object describing hotel to create.
     * @throws IllegalArgumentException if hotel is null or has assigned id.
     *         if hotel has rooms attached and any of them is null.
     *         if any of hotel's rooms is already created.
     *         if validation for hotel attributes fails.
     *         if validation for hotel's rooms fails provided it has any.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void createHotel(HotelTO hotelTO);
    
    /**
     * Returns transfer object describing hotel with given id.
     * 
     * @param id primary key of requested hotel.
     * @return hotel with given id or null if such hotel doesn't exist.
     * @throws IllegalArgumentException when given id is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    HotelTO findHotel(Long id);
    
    /**
     * Updates existing hotel.
     * 
     * @param hotelTO transfer object describing hotel to update (specified by id) with new attributes.
     * @throws IllegalArgumentException if hotel is null or he doesn't exist in database.
     *         if hotel has rooms attached and any of them is null.
     *         if any of hotel's rooms is already created and doesn't have hotel attached.
     *         if validation for hotel attributes fails.
     *         if validation for hotel's rooms fails provided it has any.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void updateHotel(HotelTO hotelTO);
    
    /**
     * Removes existing hotel.
     * 
     * @param hotel transfer object describing hotel to remove (specified by id).
     * @throws IllegalArgumentException if parameter is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void deleteHotel(HotelTO hotel);
    
    /**
     * Returns list of transfer objects describing all hotels.
     * 
     * @return all hotels in the DB or empty list if there are none.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<HotelTO> findAllHotels();
    
    /**
     * Returns list of transfer objects describing hotels with given name.
     * 
     * @param name name of requested hotels
     * @return all hotels with given name or empty list if there are none.
     * @throws IllegalArgumentException if parameter is null or empty string.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<HotelTO> findHotelsByName(String name);
    
}
