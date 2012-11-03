package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ReservationDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(Reservation reservation) {
        if (reservation != null && reservation.getId() != null) {
            throw new IllegalArgumentException("ID of reservation cannot be set");
        }
        em.persist(reservation);
    }

    @Override
    @Transactional
    public void delete(Reservation reservation) {
        em.remove(em.merge(reservation));
    }

    @Override
    @Transactional
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

    @Override
    public List<Reservation> findReservationsByClient(Client client) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.client = :client");
        query.setParameter("client" , client);
        return (List<Reservation>) query.getResultList();
    }

    @Override
    public List<Reservation> findReservationsByDate(Date from, Date to) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.fromDate >= :from AND r.toDate <= :to");
        query.setParameter("from" , from);
        query.setParameter("to" , to);
        return (List<Reservation>) query.getResultList();
    }

    @Override
    public List<Reservation> findReservationsByDate(Date from, Date to, Hotel hotel) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.fromDate >= :from AND r.toDate <= :to AND r.room.hotel = :hotel");
        query.setParameter("from" , from);
        query.setParameter("to" , to);
        query.setParameter("hotel" , hotel);
        return (List<Reservation>) query.getResultList();
    }
}
