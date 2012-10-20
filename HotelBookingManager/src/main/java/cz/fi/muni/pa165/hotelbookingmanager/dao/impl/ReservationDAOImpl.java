package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ReservationDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
public class ReservationDAOImpl implements ReservationDAO{

    private EntityManagerFactory emf;

    public void setEmf(EntityManagerFactory emf) {
        if (emf == null)
            throw new IllegalArgumentException("entityManagerFactory cannot be null");
        this.emf = emf;
    }

    @Override
    public void create(Reservation reservation) {
        if(reservation != null && reservation.getId()!=null)
            throw new IllegalArgumentException("ID of reservation cannot be set");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
        em.close();        
    }
   
    @Override
    public void delete(Reservation reservation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(reservation));
        em.getTransaction().commit();
        em.close();  
    }

    @Override
    public void update(Reservation reservation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(reservation);
        em.getTransaction().commit();
        em.close(); 
    }

    @Override
    public Reservation get(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAllReservations() {
        EntityManager em = emf.createEntityManager();
        List<Reservation> reservations = em.createQuery("Select r FROM Reservation r").getResultList();
        return reservations;
    }
   
}
