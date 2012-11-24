package cz.fi.muni.pa165.hotelbookingmanagerhelper;

import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ReservationTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrej Galád
 */
public class DatabaseSimulator {
    
    private static Long hotelId = 0l;
    private static Long clientId = 0l;
    private static Long reservationId = 0l;
    private static Long roomId = 0l;
    
    public static Set<HotelTO> hotels = new HashSet<>();
    public static Set<ClientTO> clients = new HashSet<>();
    public static Set<ReservationTO> reservations = new HashSet<>();
    public static Set<RoomTO> rooms = new HashSet<>();
    
    public static Long getHotelId() {
        return ++hotelId;
    }

    public static Long getClientId() {
        return ++clientId;
    }

    public static Long getReservationId() {
        return ++reservationId;
    }

    public static Long getRoomId() {
        return ++roomId;
    }
}
