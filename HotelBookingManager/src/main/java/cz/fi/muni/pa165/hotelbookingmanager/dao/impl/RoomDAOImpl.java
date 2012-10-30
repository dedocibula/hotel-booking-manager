package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Felipe
 */
@Repository
public class RoomDAOImpl implements RoomDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(Room room){
        if (room != null && room.getId() != null) {
            throw new IllegalArgumentException("ID of Room is to be set automatically.");
        }
        if (room.getHotel() == null) {
            throw new IllegalArgumentException("Hotel attribute cannot be null.");
        }
        em.persist(room);
    }

    @Override
    public Room get(Long id) {
        return em.find(Room.class, id);
    }

    @Override
    @Transactional
    public void update(Room room) {
        em.merge(em.find(Room.class, room.getId()).getHotel());
        em.merge(room);
        em.merge(room.getHotel());
    }

    @Override
    @Transactional
    public void delete(Room room) {
        em.remove(em.merge(room));
        em.merge(room.getHotel());
    }

    @Override
    public List<Room> findAllVacantRooms(Date from, Date to) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r NOT IN (SELECT p.room FROM Reservation p WHERE fromDate >= :from AND toDate <= :to");
        query.setParameter("from", from);
        query.setParameter("to", to);
        return (List<Room>)query.getResultList();
    }


    @Override
    public List<Room> findAllRooms() {
        List<Room> rooms = em.createQuery("SELECT r FROM Room r").getResultList();
        return rooms;
    }

}
