/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hotelbookingmanager;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Andrej
 */
public class HotelDAOImpl implements HotelDAO {

    private EntityManagerFactory emf;
    
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        if (emf == null)
            throw new IllegalArgumentException("entityManagerFactory cannot be null");
        this.emf = emf;
    }
    
    @Override
    public void create(Hotel hotel) {
        if (hotel != null && hotel.getId() != null)
            throw new IllegalArgumentException("The id of hotel is automatically set, must not be set manually.");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(hotel);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Hotel get(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Hotel.class, id);
    }

    @Override
    public void update(Hotel hotel) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(hotel);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Hotel hotel) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(hotel));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Hotel> findAll() {
        EntityManager em = emf.createEntityManager();
        return (List<Hotel>)em.createQuery("SELECT h FROM Hotel h").getResultList();
    }
    
}
