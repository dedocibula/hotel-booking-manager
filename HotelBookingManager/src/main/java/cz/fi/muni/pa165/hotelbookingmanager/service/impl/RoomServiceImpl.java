package cz.fi.muni.pa165.hotelbookingmanager.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.dozer.DozerBeanMapper;
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
    private Validator validator;
    
    @Autowired
    private DozerBeanMapper mapper;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    @Transactional
    public void createRoom(RoomTO room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (room.getId() != null) {
            throw new IllegalArgumentException("ID of room cannot be set manually.");
        }
        Room roomDO = mapper.map(room, Room.class);
        
        validateRoom(roomDO);
        roomDAO.create(roomDO);
    }

    @Override
    @Transactional
    public void deleteRoom(RoomTO room) {
        if (room == null) {
            throw new IllegalArgumentException("Cannot delete null room.");
        }
        Room roomDO = mapper.map(room, Room.class);
        
        validateRoomIncludingId(roomDO);
        roomDAO.delete(roomDO);
    }

    @Override
    @Transactional
    public void updateRoom(RoomTO room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (room.getId() == null || roomDAO.get(room.getId()) == null) {
            throw new IllegalArgumentException("Cannot update non-existent room");
        }
        if (room.getHotel() == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        Room roomDO = mapper.map(room, Room.class);
        
        validateRoomIncludingId(roomDO);
        roomDAO.update(roomDO);
    }

    @Override
    @Transactional
    public RoomTO getRoom(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        Room roomDO = roomDAO.get(id);
        RoomTO roomTO= mapper.map(roomDO, RoomTO.class);
        
        return roomTO;
    }

    @Override
    public List<RoomTO> findAllRooms() {
        
        List<Room> rooms= roomDAO.findAllRooms();
        List<RoomTO> roomsTO = new ArrayList<RoomTO>();
        for(Room roomDO : rooms){
            roomsTO.add(mapper.map(roomDO, RoomTO.class));
        }        
        return roomsTO;
    }

    @Override
    public List<RoomTO> findRoomsByHotel(HotelTO hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null.");
        }
        if (hotel.getId() == null) {
            throw new IllegalArgumentException("Room must be in database.");
        }
        Hotel hotelDO = mapper.map(hotel, Hotel.class);
        
        List<Room> rooms= hotelDO.getRooms();
        List<RoomTO> roomsTO = new ArrayList<RoomTO>();
        for(Room roomDO : rooms){
            roomsTO.add(mapper.map(roomDO, RoomTO.class));
        }        
        return roomsTO;        
        
    }

    @Override
    public List<RoomTO> findVacantRooms(Date from, Date to, HotelTO hotel) {
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
        Hotel hotelDO = mapper.map(hotel, Hotel.class);
        
        List<Room> daoRooms = roomDAO.findAllVacantRooms(from, to);
        List<Room> rooms = new ArrayList<>();
        for (Room room : daoRooms) {
            if (room.getHotel().equals(hotelDO)) {
                rooms.add(room);
            }
        }
        List<RoomTO> roomsTO = new ArrayList<RoomTO>();
        for(Room roomDO : rooms){
            roomsTO.add(mapper.map(roomDO, RoomTO.class));
        }        
        return roomsTO; 

    }

    private void validateRoom(Room room) {
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        validateRoomAttributes(room);
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
}
