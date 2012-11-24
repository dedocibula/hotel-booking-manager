package cz.fi.muni.pa165.hotelbookingmanagerhelper;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ReservationTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andrej Galád
 */
public class RoomServiceImpl implements RoomService {
    
    private ReservationService reservationService = new ReservationServiceImpl();

    @Override
    public void createRoom(RoomTO room) {
        room.setId(DatabaseSimulator.getRoomId());
        DatabaseSimulator.rooms.add(room);
    }

    @Override
    public void deleteRoom(RoomTO room) {
        for (ReservationTO reservation : reservationService.findAllReservations())
            if (reservation.getRoom().equals(room))
                reservationService.deleteReservation(reservation);
        DatabaseSimulator.rooms.remove(room);
    }

    @Override
    public void updateRoom(RoomTO room) {
        RoomTO roomTO = getRoom(room.getId());
        DatabaseSimulator.rooms.remove(roomTO);
        DatabaseSimulator.rooms.add(room);
    }

    @Override
    public RoomTO getRoom(Long id) {
        for (RoomTO room : findAllRooms()) {
            if (room.getId().equals(id))
                return room;
        }
        return null;
    }

    @Override
    public List<RoomTO> findAllRooms() {
        return DatabaseSimulator.rooms;
    }

    @Override
    public List<RoomTO> findRoomsByHotel(HotelTO hotel) {
        List<RoomTO> temp = new ArrayList<>();
        for (RoomTO roomTO : findAllRooms()) {
            if (roomTO.getHotel().equals(hotel))
                temp.add(roomTO);
        }
        return temp;
    }

    @Override
    public List<RoomTO> findVacantRooms(Date from, Date to, HotelTO hotel) {
        List<RoomTO> temp = new ArrayList<>();
        List<RoomTO> occupied = new ArrayList<>();
        for (ReservationTO reservationTO : reservationService.findReservationsByDate(from, to, hotel))
            occupied.add(reservationTO.getRoom());
        for (RoomTO roomTO : findAllRooms()) {
            if (!occupied.contains(roomTO))
                temp.add(roomTO);
        }
        return temp;
    }
    
}
