package cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Room;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
        em.merge(room);
        Hotel hotel = em.find(Hotel.class, room.getHotel().getId());
        hotel.getRooms().remove(room);
        em.merge(hotel);
        em.remove(em.merge(room)); 
    }

    @Override
    public List<Room> findAllVacantRooms(Date from, Date to) {
        Query query = em.createQuery("SELECT r FROM Room r WHERE r NOT IN (SELECT p.room FROM Reservation p WHERE (p.fromDate >= :from AND p.fromDate <= :to) OR (p.toDate >= :from AND p.toDate <= :to) OR (p.fromDate <= :from AND p.toDate >= :to))");
        query.setParameter("from", from, TemporalType.DATE);
        query.setParameter("to", to, TemporalType.DATE);
        return (List<Room>)query.getResultList();
    }

    @Override
    public boolean isVacant(Room room, Date from, Date to) {
        return findAllVacantRooms(from, to).contains(room);
    }

    @Override
    public List<Room> findAllRooms() {
        List<Room> rooms = em.createQuery("SELECT r FROM Room r").getResultList();
        return rooms;
    }

}
