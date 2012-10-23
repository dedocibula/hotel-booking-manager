package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Marián Rusnák
 */
public class HotelDAOImpl implements HotelDAO {

    private EntityManagerFactory emf;

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        if (emf == null) {
            throw new IllegalArgumentException("entityManagerFactory cannot be null");
        }
        this.emf = emf;
    }

    @Override
    public void create(Hotel hotel) {
        if (hotel != null && hotel.getId() != null) {
            throw new IllegalArgumentException("The id of hotel is automatically set, must not be set manually.");
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Hotel get(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Hotel.class, id);
    }

    @Override
    public void update(Hotel hotel) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(hotel);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Hotel hotel) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(hotel));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Hotel> findAll() {
        EntityManager em = emf.createEntityManager();
        return (List<Hotel>)em.createQuery("SELECT h FROM Hotel h").getResultList();
    }

}
