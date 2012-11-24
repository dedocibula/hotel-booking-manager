package cz.fi.muni.pa165.hotelbookingmanagerhelper;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrej Galád
 */
public class HotelServiceImpl implements HotelService {

    private RoomService roomService = new RoomServiceImpl();
    
    @Override
    public void createHotel(HotelTO hotelTO) {
        hotelTO.setId(DatabaseSimulator.getHotelId());
        DatabaseSimulator.hotels.add(hotelTO);
    }

    @Override
    public HotelTO findHotel(Long id) {
        for (HotelTO hotel : findAllHotels()) {
            if (hotel.getId() == id)
                return hotel;
        }
        return null;
    }

    @Override
    public void updateHotel(HotelTO hotelTO) {
        HotelTO hotel = findHotel(hotelTO.getId());
        DatabaseSimulator.hotels.remove(hotel);
        DatabaseSimulator.hotels.add(hotelTO);
    }

    @Override
    public void deleteHotel(HotelTO hotel) {
        for (RoomTO room : roomService.findRoomsByHotel(hotel))
            roomService.deleteRoom(room);
        DatabaseSimulator.hotels.remove(hotel);
    }

    @Override
    public List<HotelTO> findAllHotels() {
        return DatabaseSimulator.hotels;
    }

    @Override
    public List<HotelTO> findHotelsByName(String name) {
        List<HotelTO> temp = new ArrayList<>();
        for (HotelTO hotelTO : findAllHotels()) {
            if (hotelTO.getName().equals(name))
                temp.add(hotelTO);
        }
        return temp;
    }

    @Override
    public List<HotelTO> findHotelsByAddress(String address) {
        List<HotelTO> temp = new ArrayList<>();
        for (HotelTO hotelTO : findAllHotels()) {
            if (hotelTO.getName().equals(address))
                temp.add(hotelTO);
        }
        return temp;
    }

    @Override
    public List<HotelTO> findHotelsByCity(String city) {
        List<HotelTO> temp = new ArrayList<>();
        for (HotelTO hotelTO : findAllHotels()) {
            if (hotelTO.getName().equals(city))
                temp.add(hotelTO);
        }
        return temp;
    }

    @Override
    public List<HotelTO> findHotelsByCountry(String country) {
        List<HotelTO> temp = new ArrayList<>();
        for (HotelTO hotelTO : findAllHotels()) {
            if (hotelTO.getName().equals(country))
                temp.add(hotelTO);
        }
        return temp;
    }
    
}
