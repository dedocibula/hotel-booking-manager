package com.mycompany.hotelbookingmanager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Implementation of RoomDAO Interface
 *
 * @author Thanh Dang Hoang Minh
 */
public class RoomDAOImpl implements RoomDAO{

    private EntityManagerFactory emf;

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void create(Room room) throws UnavailableDatabaseException {
        if (room == null) {
            throw new IllegalArgumentException("room cannot be null!");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(room);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Room get(Long id) throws UnavailableDatabaseException {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null!");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Room room = em.find(Room.class, id);
        em.close();
        return room;
    }

    @Override
    public void update(Room room) throws UnavailableDatabaseException {
        if (room == null) {
            throw new IllegalArgumentException("room cannot be null!");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Room tempRoom = em.find(Room.class, room.getId());
        tempRoom.setHotel(room.getHotel());
        tempRoom.setPricePerNight(room.getPricePerNight());
        tempRoom.setVacant(room.isVacant());
        em.refresh(tempRoom);
        em.persist(tempRoom);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Room room) throws UnavailableDatabaseException {
        if (room == null) {
            throw new IllegalArgumentException("room cannot be null!");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(room));
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Room> findAllVacantRooms() throws UnavailableDatabaseException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Room> rooms = em.createQuery("SELECT r FROM Room r WHERE VACANT = TRUE").getResultList();
        em.getTransaction().commit();
        em.close();
        return rooms;
    }

    @Override
    public List<Room> findAllRooms() throws UnavailableDatabaseException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Room> rooms = em.createQuery("SELECT r FROM Room r").getResultList();
        em.getTransaction().commit();
        em.close();
        return rooms;
    }

}
