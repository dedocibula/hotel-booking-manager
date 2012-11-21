/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanager.service.interfaces;

import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
public interface RoomService {

    /**
     * Creates a new room
     * @param room room to be created
     * @throws IllegalArgumentException if room is null, hotel is null, or ID of room is manually set.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void createRoom(RoomTO room);

    /**
     * Deletes room.
     * @param room room to delete
     * @throws IllegalArgumentException if room is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void deleteRoom(RoomTO room);

    /**
     * Updates room.
     * @param room room to update
     * @throws IllegalArgumentException if room is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    void updateRoom(RoomTO room);

    /**
     * Returns hotel with given ID
     * @param id id of room to return
     * @return room with given ID
     * @throws IllegalArgumentException if id is null.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    RoomTO getRoom(Long id);

    /**
     * Returns all rooms.
     * @return all rooms
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<RoomTO> findAllRooms();

    /**
     * Returns all rooms attached to a hotel
     *
     * @param hotel hotel, whose rooms to return
     * @return list of all rooms attached to a hotel
     * @throws IllegalArgumentException if hotel is null
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<RoomTO> findRoomsByHotel(HotelTO hotel);

    /**
     * Returns all vacant rooms in the given hotel and date constraints
     * @param from date from which rooms are vacant
     * @param to date to which rooms are vacant
     * @param hotel hotel to search in
     * @return list of vacant rooms in given hotel and date constraints
     * @throws IllegalArgumentException if from, to date, hotel are null, or if hotel is not in the database, or if from date is after to date.
     * @throws DataAccessException in case of error on a persistence layer.
     */
    List<RoomTO> findVacantRooms(Date from, Date to, HotelTO hotel);

}
