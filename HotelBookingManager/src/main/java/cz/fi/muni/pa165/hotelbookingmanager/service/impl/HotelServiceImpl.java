package cz.fi.muni.pa165.hotelbookingmanager.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Andrej Galád
 */
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelDAO hotelDAO;
    
    @Autowired
    private RoomDAO roomDAO;
    
    @Autowired
    private Validator validator;

    @Override
    @Transactional
    public void createHotel(Hotel hotel) {
        if (hotel == null)
            throw new IllegalArgumentException("hotel cannot be null");
        if (hotel.getId() != null)
            throw new IllegalArgumentException("hotel cannot have manually assigned id");
        if (hotel.getRooms() != null) {
            for (Room room : hotel.getRooms()) {
                if (room == null || room.getId() != null)
                    throw new IllegalArgumentException("room cannot be null or already in a database");
                room.setHotel(hotel);
                validateAttachedRoomAttributes(room);
            }
        }
        validateHotelAttrributes(hotel);
        hotelDAO.create(hotel);
    }

    @Override
    public Hotel findHotel(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id cannot be null");
        return hotelDAO.get(id);
    }

    @Override
    @Transactional
    public void updateHotel(Hotel hotel) {
        if (hotel == null)
            throw new IllegalArgumentException("hotel cannot be null");
        if (hotel.getId() == null || hotelDAO.get(hotel.getId()) == null) 
            throw new IllegalArgumentException("trying to update non-existent hotel");
        if (hotel.getRooms() != null) {
            for (Room room : hotel.getRooms()) {
                if (room == null || room.getId() != null || roomDAO.get(room.getId()).getHotel() != hotel)
                    throw new IllegalArgumentException("room cannot be null or have attached a different hotel");
                validateAttachedRoomAttributes(room);
            }
        }
        validateHotelAttrributes(hotel);
        hotelDAO.update(hotel);
    }

    @Override
    @Transactional
    public void deleteHotel(Hotel hotel) {
        if (hotel == null)
            throw new IllegalArgumentException("hotel cannot be null");
        hotelDAO.delete(hotel);
    }

    @Override
    public List<Hotel> findAllHotels() {
        return hotelDAO.findAll();
    }

    @Override
    public List<Hotel> findHotelsByName(String name) {
        if (name == null || "".equals(name.trim()))
            throw new IllegalArgumentException("name cannot be empty");
        List<Hotel> hotels = new ArrayList<>();
        for (Hotel hotel : hotelDAO.findAll()) {
            if (hotel.getName().contains(name))
                hotels.add(hotel);
        }
        return hotels;
    }

    @Override
    public List<Hotel> findHotelsByAddress(String address) {
        if (address == null || "".equals(address.trim()))
            throw new IllegalArgumentException("address cannot be empty");
        List<Hotel> hotels = new ArrayList<>();
        for (Hotel hotel : hotelDAO.findAll()) {
            if (hotel.getContact().getAddress().contains(address))
                hotels.add(hotel);
        }
        return hotels;
    }

    @Override
    public List<Hotel> findHotelsByCity(String city) {
        if (city == null || "".equals(city.trim()))
            throw new IllegalArgumentException("city cannot be empty");
        List<Hotel> hotels = new ArrayList<>();
        for (Hotel hotel : hotelDAO.findAll()) {
            if (hotel.getContact().getCity().contains(city))
                hotels.add(hotel);
        }
        return hotels;
    }

    @Override
    public List<Hotel> findHotelsByCountry(String country) {
        if (country == null || "".equals(country.trim()))
            throw new IllegalArgumentException("country cannot be empty");
        List<Hotel> hotels = new ArrayList<>();
        for (Hotel hotel : hotelDAO.findAll()) {
            if (hotel.getContact().getCountry().contains(country))
                hotels.add(hotel);
        }
        return hotels;
    }
    
    private void validateHotelAttrributes(Hotel hotel) throws IllegalArgumentException {
        Set<ConstraintViolation<Hotel>> validationResults = validator.validate(hotel);
        if (!validationResults.isEmpty())
            throw new IllegalArgumentException("hotel parameters are invalid: " + validationResults.iterator().next().getMessage());
    }
    
    private void validateAttachedRoomAttributes(Room room) throws IllegalArgumentException {
        Set<ConstraintViolation<Room>> validationResults = validator.validate(room);
        if (!validationResults.isEmpty())
            throw new IllegalArgumentException("room parameters are invalid: " + validationResults.iterator().next().getMessage());
    }
}
