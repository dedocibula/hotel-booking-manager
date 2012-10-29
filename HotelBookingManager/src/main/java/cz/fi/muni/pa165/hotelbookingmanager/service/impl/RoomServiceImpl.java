package cz.fi.muni.pa165.hotelbookingmanager.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thanh Dang Hoang Minh
 */

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDAO roomDAO;

    @Autowired
    private HotelDAO hotelDAO;

    @Autowired
    private Validator validator;

    @Override
    @Transactional
    public void createRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (room.getId() != null) {
            throw new IllegalArgumentException("ID of room cannot be set manually.");
        }
        validateRoom(room);

        roomDAO.create(room);
    }

    @Override
    @Transactional
    public void deleteRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Cannot delete null room.");
        }
        validateRoomIncludingId(room);
        roomDAO.delete(room);
    }

    @Override
    @Transactional
    public void updateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (room.getId() == null || roomDAO.get(room.getId()) == null) {
            throw new IllegalArgumentException("Cannot update non-existent room");
        }
        if (room.getHotel() == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        validateRoomIncludingId(room);
        roomDAO.update(room);
    }

    @Override
    @Transactional
    public Room getRoom(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        return roomDAO.get(id);
    }

    @Override
    @Transactional
    public List<Room> findAllRooms() {
        return roomDAO.findAllRooms();
    }

    @Override
    @Transactional
    public List<Room> findRoomsByHotel(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        if (hotel.getId() == null) {
            throw new IllegalArgumentException("Room must be in database.");
        }

        List<Room> rooms = new ArrayList<>();
        for (Room room : roomDAO.findAllRooms()) {
            if (room.getHotel().equals(hotel)) {
                rooms.add(room);
            }
        }
        return rooms;
    }

    @Override
    @Transactional
    public List<Room> findVacantRooms(Date from, Date to, Hotel hotel) {
        if (from == null) {
            throw new IllegalArgumentException("From date cannot be null.");
        }
        if (to == null) {
            throw new IllegalArgumentException("To date cannot be null.");
        }
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        if (hotel.getId() == null) {
            throw new IllegalArgumentException("Hotel must be in database.");
        }
        if (from.after(to)) {
            throw new IllegalArgumentException("From date must be before to date");
        }

        List<Room> rooms = new ArrayList<>();
        for (Room room : roomDAO.findAllVacantRooms(from, to)) {
            if (room.getHotel().equals(hotel)) {
                rooms.add(room);
            }
        }
        return rooms;

    }

    private void validateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        validateRoomAttributes(room);
        validateRoomHotel(room);
    }

    private void validateRoomIncludingId(Room room) {
        validateRoom(room);
        if (room.getId() == null) {
            throw new IllegalArgumentException("Room must have id already set.");
        }
    }

    private void validateRoomAttributes(Room room) throws IllegalArgumentException {
        Set<ConstraintViolation<Room>> validationResults = validator.validate(room);
        if (!validationResults.isEmpty()) {
            throw new IllegalArgumentException("room parameters are invalid: " + validationResults.iterator().next().getMessage());
        }
    }


    private void validateRoomHotel(Room room) {
        Hotel hotel = hotelDAO.get(room.getHotel().getId());
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        Set<ConstraintViolation<Hotel>> validationResults = validator.validate(hotel);
        if (!validationResults.isEmpty()) {
            throw new IllegalArgumentException("hotel parameters are invalid: " + validationResults.iterator().next().getMessage());
        }
    }
}
