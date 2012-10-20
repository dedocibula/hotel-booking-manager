package cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces;

import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import java.util.List;

/**
 *
 * @author Andrej Galád
 */
public interface RoomDAO {

    /**
     * Adds new room to the database.
     * 
     * @param room Room to create
     * @throws IllegalArgumentException if room is null or if Hotel parameter of room is null or if ID of room is already set
     * @throws ConstraintViolationException if room has any invalid parameter
     */
    void create (Room room);

    /**
     * Returns room with given id.
     * 
     * @param id ID of the Room to get
     * @return Room with the entered ID
     * @throws IllegalArgumentException if id is null
     */
    Room get(Long id);

    /**
     * Updates existing room.
     * 
     * @param room Room to update
     * @throws IllegalArgumentException if room is null
     */
    void update (Room room);

    /**
     * Removes existing room.
     * 
     * @param room Room to delete
     * @throws IllegalArgumentException if room is null
     * @throws ConstraintViolationException if room has any invalid parameter
     */
    void delete (Room room);

    /**
     * Returns list of all vacant rooms in the database.
     * 
     * @return List of all vacant rooms
     */
    List<Room> findAllVacantRooms();

    /**
     * Returns list of all rooms in the database.
     * 
     * @return List of all Rooms
     */
    List<Room> findAllRooms();
}
