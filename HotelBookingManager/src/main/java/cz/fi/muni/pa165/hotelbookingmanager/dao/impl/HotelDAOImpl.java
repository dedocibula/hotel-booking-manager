package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

}
