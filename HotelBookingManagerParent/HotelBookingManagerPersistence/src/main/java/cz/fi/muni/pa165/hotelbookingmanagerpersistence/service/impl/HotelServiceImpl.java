package cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.HotelService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Room;
import java.util.ArrayList;
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
	@Autowired
	private Mapper mapper;

	@Override
	@Transactional
	public void createHotel(HotelTO hotelTO) {
		if (hotelTO == null) {
			throw new IllegalArgumentException("hotel cannot be null");
		}
		if (hotelTO.getId() != null) {
			throw new IllegalArgumentException("hotel cannot have manually assigned id");
		}
		Hotel hotel = mapper.map(hotelTO, Hotel.class);
		if (hotel.getRooms() != null) {
			for (Room room : hotel.getRooms()) {
				if (room == null || room.getId() != null) {
					throw new IllegalArgumentException("room cannot be null or already in a database");
				}
				room.setHotel(hotel);
				validateAttachedRoomAttributes(room);
			}
		}
		validateHotelAttributes(hotel);
		hotelDAO.create(hotel);

		hotelTO.setId(hotel.getId());
	}

	@Override
	public HotelTO findHotel(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("id cannot be null");
		}
		Hotel hotel = hotelDAO.get(id);
		return hotel != null ? mapper.map(hotel, HotelTO.class) : null;
	}

	@Override
	@Transactional
	public void updateHotel(HotelTO hotelTO) {
		if (hotelTO == null) {
			throw new IllegalArgumentException("hotel cannot be null");
		}
		if (hotelTO.getId() == null || hotelDAO.get(hotelTO.getId()) == null) {
			throw new IllegalArgumentException("trying to update non-existent hotel");
		}
		Hotel hotel = mapper.map(hotelTO, Hotel.class);
		if (hotel.getRooms() != null) {
			for (Room room : hotel.getRooms()) {
				if (room == null || room.getId() != null || roomDAO.get(room.getId()).getHotel() != hotel) {
					throw new IllegalArgumentException("room cannot be null or be attached to a different hotel");
				}
				validateAttachedRoomAttributes(room);
			}
		}
		validateHotelAttributes(hotel);
		hotelDAO.update(hotel);
	}

	@Override
	@Transactional
	public void deleteHotel(HotelTO hotelTO) {
		if (hotelTO == null) {
			throw new IllegalArgumentException("hotel cannot be null");
		}
		Hotel hotel = mapper.map(hotelTO, Hotel.class);

		hotelDAO.delete(hotel);
	}

	@Override
	public List<HotelTO> findAllHotels() {
		List<HotelTO> hotelTOs = new ArrayList<>();
		for (Hotel hotel : hotelDAO.findAll()) {
			hotelTOs.add(mapper.map(hotel, HotelTO.class));
		}
		return hotelTOs;
	}

	@Override
	public List<HotelTO> findHotelsByName(String name) {
		if (name == null || "".equals(name.trim())) {
			throw new IllegalArgumentException("name cannot be empty");
		}
		List<HotelTO> hotelTOs = new ArrayList<>();
		for (Hotel hotel : hotelDAO.findHotelsByName(name)) {
			hotelTOs.add(mapper.map(hotel, HotelTO.class));
		}
		return hotelTOs;
	}

	@Override
	public List<HotelTO> findHotelsByAddress(String address) {
		if (address == null || "".equals(address.trim())) {
			throw new IllegalArgumentException("address cannot be empty");
		}
		List<HotelTO> hotelTOs = new ArrayList<>();
		for (Hotel hotel : hotelDAO.findHotelsByAddress(address)) {
			hotelTOs.add(mapper.map(hotel, HotelTO.class));
		}
		return hotelTOs;
	}

	@Override
	public List<HotelTO> findHotelsByCity(String city) {
		if (city == null || "".equals(city.trim())) {
			throw new IllegalArgumentException("city cannot be empty");
		}
		List<HotelTO> hotelTOs = new ArrayList<>();
		for (Hotel hotel : hotelDAO.findHotelsByCity(city)) {
			hotelTOs.add(mapper.map(hotel, HotelTO.class));
		}
		return hotelTOs;
	}

	@Override
	public List<HotelTO> findHotelsByCountry(String country) {
		if (country == null || "".equals(country.trim())) {
			throw new IllegalArgumentException("country cannot be empty");
		}
		List<HotelTO> hotelTOs = new ArrayList<>();
		for (Hotel hotel : hotelDAO.findHotelsByCountry(country)) {
			hotelTOs.add(mapper.map(hotel, HotelTO.class));
		}
		return hotelTOs;
	}

	private void validateHotelAttributes(Hotel hotel) throws IllegalArgumentException {
		Set<ConstraintViolation<Hotel>> validationResults = validator.validate(hotel);
		if (!validationResults.isEmpty()) {
			throw new IllegalArgumentException("hotel parameters are invalid: " + validationResults.iterator().next().getMessage());
		}
	}

	private void validateAttachedRoomAttributes(Room room) throws IllegalArgumentException {
		Set<ConstraintViolation<Room>> validationResults = validator.validate(room);
		if (!validationResults.isEmpty()) {
			throw new IllegalArgumentException("room parameters are invalid: " + validationResults.iterator().next().getMessage());
		}
	}
}
