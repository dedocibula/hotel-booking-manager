package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Felipe
 */
@Repository
public class RoomDAOImpl implements RoomDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Room room){
        if (room != null && room.getId() != null)
            throw new IllegalArgumentException("ID of Room is to be set automatically.");
        em.getTransaction().begin();
    }

    @Override
    public Room get(Long id) {
        return em.find(Room.class, id);
    }

    @Override
    public void update(Room room) {
        em.merge(em.find(Room.class, room.getId()).getHotel());
        em.merge(room);
        em.merge(room.getHotel());
    }

    @Override
    public void delete(Room room) {
        em.getTransaction().begin();
        em.remove(em.merge(room));
        em.merge(room.getHotel());
    }

    @Override
    public List<Room> findAllVacantRooms() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }


    @Override
    public List<Room> findAllRooms() {
        List<Room> rooms = em.createQuery("SELECT r FROM Room r").getResultList();
        return rooms;
    }

}
