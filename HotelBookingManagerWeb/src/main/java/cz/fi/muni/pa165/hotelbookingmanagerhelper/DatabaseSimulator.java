package cz.fi.muni.pa165.hotelbookingmanagerhelper;

import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ReservationTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrej Galád
 */
public class DatabaseSimulator {
    
    private static Long hotelId = 0l;
    private static Long clientId = 0l;
    private static Long reservationId = 0l;
    private static Long roomId = 0l;
    
    public static List<HotelTO> hotels = new ArrayList<>();
    public static List<ClientTO> clients = new ArrayList<>();
    public static List<ReservationTO> reservations = new ArrayList<>();
    public static List<RoomTO> rooms = new ArrayList<>();
    
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
