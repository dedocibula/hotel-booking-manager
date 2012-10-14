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
public class ClientDAOImpl implements ClientDAO {

    private EntityManagerFactory emf;
    
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        if (emf == null)
            throw new IllegalArgumentException("entityManagerFactory cannot be null");
        this.emf = emf;
    }
    
    @Override
    public void create(Client client) {
        if (client != null && client.getId() != null)
            throw new IllegalArgumentException("Cannot create entity with set id.");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Client get(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Client.class, id);
    }

    @Override
    public void update(Client client) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(client);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(Client client) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.merge(client));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Client> findAll() {
        EntityManager em = emf.createEntityManager();
        return (List<Client>)em.createQuery("SELECT c FROM Client c").getResultList();
    }
}
