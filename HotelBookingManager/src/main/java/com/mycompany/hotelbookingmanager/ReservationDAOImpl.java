package com.mycompany.hotelbookingmanager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
public class ReservationDAOImpl implements ReservationDAO{

    private EntityManagerFactory emf;
    
    public ReservationDAOImpl() {
    }
      
    public ReservationDAOImpl(EntityManagerFactory emf){
        if (emf == null)
            throw new IllegalArgumentException("entityManagerFactory cannot be null");
        this.emf=emf;
    }
    
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    @Override
    public void create(Reservation reservation) {
        if(reservation == null){
            throw new IllegalArgumentException("parameter reservation cannot be null");
        }
        if(reservation != null && reservation.getId()!=null){
            throw new IllegalArgumentException("ID of reservation cannot be set");
        }
        if(reservation.getClient() == null){
            throw new IllegalArgumentException("Client of reservation cannot be null");
        }
        if(reservation.getRoom() == null){
            throw new IllegalArgumentException("Room of reservation cannot be null");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
        em.close();        
    }
   
    @Override
    public void delete(Reservation reservation) {
        if(reservation == null){
            throw new IllegalArgumentException("parameter reservation cannot be null");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(reservation));
        em.getTransaction().commit();
        em.close();  
    }

    @Override
    public void update(Reservation reservation) {
        if(reservation == null){
            throw new IllegalArgumentException("parameter reservation cannot be null");
        }
        if(reservation.getClient() == null){
            throw new IllegalArgumentException("Client of reservation cannot be null");
        }
        if(reservation.getRoom() == null){
            throw new IllegalArgumentException("Room of reservation cannot be null");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Reservation tempReservation =em.find(Reservation.class, reservation.getId());
        em.merge(reservation);
        em.persist(tempReservation);
        em.getTransaction().commit();
        em.close(); 
    }

    @Override
    public Reservation get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("parameter id cannot be null!");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Reservation reservation = em.find(Reservation.class, id);
        em.close();
        return reservation;
    }

    @Override
    public List<Reservation> findAllReservations() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Reservation> reservations = em.createQuery("Select r FROM Reservation r").getResultList();
        em.getTransaction().commit();
        em.close();
        return reservations;
    }
   
}
