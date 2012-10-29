package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ReservationDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Reservation reservation) {
        if (reservation != null && reservation.getId() != null) {
            throw new IllegalArgumentException("ID of reservation cannot be set");
        }
        em.persist(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        em.remove(em.merge(reservation));
    }

    @Override
    public void update(Reservation reservation) {
        em.merge(reservation);
    }

    @Override
    public Reservation get(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAllReservations() {
        List<Reservation> reservations = em.createQuery("Select r FROM Reservation r").getResultList();
        return reservations;
    }
}
