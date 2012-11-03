package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marián Rusnák
 */
@Repository
public class HotelDAOImpl implements HotelDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(Hotel hotel) {
        if (hotel != null && hotel.getId() != null)
            throw new IllegalArgumentException("The id of hotel is automatically set, must not be set manually.");
        em.persist(hotel);
    }

    @Override
    public Hotel get(Long id) {
        return em.find(Hotel.class, id);
    }

    @Override
    @Transactional
    public void update(Hotel hotel) {
        em.merge(hotel);
    }

    @Override
    @Transactional
    public void delete(Hotel hotel) {
        em.remove(em.merge(hotel));
    }

    @Override
    public List<Hotel> findAll() {
        return (List<Hotel>)em.createQuery("SELECT h FROM Hotel h").getResultList();
    }

    @Override
    public List<Hotel> findHotelsByName(String name) {
        Query query = em.createQuery("SELECT h FROM Hotel h WHERE :name LIKE name");
        query.setParameter("name", name);
        return (List<Hotel>)query.getResultList();
    }

    @Override
    public List<Hotel> findHotelsByAddress(String address) {
        Query query = em.createQuery("SELECT h FROM Hotel h WHERE :address LIKE address");
        query.setParameter("address", address);
        return (List<Hotel>)query.getResultList();
    }

    @Override
    public List<Hotel> findHotelsByCity(String city) {
        Query query = em.createQuery("SELECT h FROM Hotel h WHERE :city LIKE city");
        query.setParameter("city", city);
        return (List<Hotel>)query.getResultList();
    }

    @Override
    public List<Hotel> findHotelsByCountry(String country) {
        Query query = em.createQuery("SELECT h FROM Hotel h WHERE :country LIKE country");
        query.setParameter("country", country);
        return (List<Hotel>)query.getResultList();
    }

}
