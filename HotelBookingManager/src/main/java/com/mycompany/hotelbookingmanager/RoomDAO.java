package com.mycompany.hotelbookingmanager;

import java.util.List;

/**
 * DAO Interface for the entity Room
 *
 * @author Thanh Dang Hoang Minh
 */
public interface RoomDAO {

    /**
     *
     * @param room Room to create
     * @throws IllegalArgumentException if room is null
     * @throws UnavailableDatabaseException if the connection to the database fails
     */
    void create (Room room) throws UnavailableDatabaseException;

    /**
     *
     * @param id ID of the Room to get
     * @return Room with the entered ID
     * @throws IllegalArgumentException if id is null
     * @throws UnavailableDatabaseException if the connection to the database fails
     */
    Room get(Long id) throws UnavailableDatabaseException;

    /**
     *
     * @param room Room to update
     * @throws IllegalArgumentException if room is null
     * @throws UnavailableDatabaseException if the connection to the database fails
     */
    void update (Room room) throws UnavailableDatabaseException;

    /**
     *
     * @param room Room to delete
     * @throws IllegalArgumentException if room is null
     * @throws UnavailableDatabaseException if the connection to the database fails
     */
    void delete (Room room) throws UnavailableDatabaseException;

    /**
     *
     * @return List of all vacant rooms
     * @throws UnavailableDatabaseException if the connection to the database fails
     */
    List<Room> findAllVacantRooms() throws UnavailableDatabaseException;

    /**
     *
     * @return List of all Rooms
     * @throws UnavailableDatabaseException if the connection to the database fails
     */
    List<Room> findAllRooms() throws UnavailableDatabaseException;
}
