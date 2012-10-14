package com.mycompany.hotelbookingmanager;

import java.util.List;

/**
 * DAO Interface for the entity Room
 *
 * @author Andrej
 */
public interface RoomDAO {

    /**
     *
     * @param room Room to create
     * @throws IllegalArgumentException if room is null or if Hotel parameter of room is null or if ID of room is already set
     */
    void create (Room room);

    /**
     *
     * @param id ID of the Room to get
     * @return Room with the entered ID
     * @throws IllegalArgumentException if id is null
     */
    Room get(Long id);

    /**
     *
     * @param room Room to update
     * @throws IllegalArgumentException if room is null
     */
    void update (Room room);

    /**
     *
     * @param room Room to delete
     * @throws IllegalArgumentException if room is null
     */
    void delete (Room room);

    /**
     *
     * @return List of all vacant rooms
     */
    List<Room> findAllVacantRooms();

    /**
     *
     * @return List of all Rooms
     */
    List<Room> findAllRooms();
}
