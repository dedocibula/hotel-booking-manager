package cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.RoomService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomTO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Room;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.dozer.Mapper;
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
	private Mapper mapper;

	@Override
	@Transactional
	public void createRoom(RoomTO roomTO) {
		if (roomTO == null) {
			throw new IllegalArgumentException("Room cannot be null.");
		}
		if (roomTO.getId() != null) {
			throw new IllegalArgumentException("ID of room cannot be set manually.");
		}
		Room room = mapper.map(roomTO, Room.class);

		validateRoom(room);
		roomDAO.create(room);

		roomTO.setId(room.getId());
	}

	@Override
	@Transactional
	public void deleteRoom(RoomTO roomTO) {
		if (roomTO == null) {
			throw new IllegalArgumentException("Cannot delete null room.");
		}
		Room room = mapper.map(roomTO, Room.class);
		validateRoomIncludingId(room);
		roomDAO.delete(room);
	}

	@Override
	@Transactional
	public void updateRoom(RoomTO roomTO) {
		if (roomTO == null) {
			throw new IllegalArgumentException("Room cannot be null");
		}
		if (roomTO.getId() == null || roomDAO.get(roomTO.getId()) == null) {
			throw new IllegalArgumentException("Cannot update non-existent room");
		}
		if (roomTO.getHotel() == null) {
			throw new IllegalArgumentException("Hotel cannot be null.");
		}
		Room room = mapper.map(roomTO, Room.class);
		validateRoomIncludingId(room);
		roomDAO.update(room);
	}

	@Override
	public RoomTO getRoom(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}
		if (roomDAO.get(id) == null) {
			return null;
		} else {
			return mapper.map(roomDAO.get(id), RoomTO.class);
		}
	}

	@Override
	public List<RoomTO> findRoomsByHotel(HotelTO hotelTO) {
		if (hotelTO == null) {
			throw new IllegalArgumentException("Hotel cannot be null.");
		}
		if (hotelTO.getId() == null) {
			throw new IllegalArgumentException("Room must be in database.");
		}
		Hotel hotel = mapper.map(hotelTO, Hotel.class);

		List<Room> rooms = hotel.getRooms();
		List<RoomTO> roomsTO = new ArrayList<>();
		for (Room roomDO : rooms) {
			roomsTO.add(mapper.map(roomDO, RoomTO.class));
		}
		return roomsTO;

	}

	@Override
	public List<RoomTO> findVacantRooms(Date from, Date to, HotelTO hotelTO) {
		if (from == null) {
			throw new IllegalArgumentException("From date cannot be null.");
		}
		if (to == null) {
			throw new IllegalArgumentException("To date cannot be null.");
		}
		if (hotelTO == null) {
			throw new IllegalArgumentException("Hotel cannot be null.");
		}
		if (hotelTO.getId() == null) {
			throw new IllegalArgumentException("Hotel must be in database.");
		}
		if (from.after(to)) {
			throw new IllegalArgumentException("From date must be before to date");
		}
		Hotel hotel = mapper.map(hotelTO, Hotel.class);
		List<Room> daoRooms = roomDAO.findAllVacantRooms(from, to);
		List<Room> rooms = new ArrayList<>();
		for (Room room : daoRooms) {
			if (room.getHotel().equals(hotel)) {
				rooms.add(room);
			}
		}
		List<RoomTO> roomsTO = new ArrayList<>();
		for (Room roomDO : rooms) {
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
