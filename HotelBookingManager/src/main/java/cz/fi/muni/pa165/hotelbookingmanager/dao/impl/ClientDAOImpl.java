package cz.fi.muni.pa165.hotelbookingmanager.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Andrej Galád
 */
public class ClientDAOImpl implements ClientDAO {

    private EntityManagerFactory emf;

    public void setEntityManagerFactory(EntityManagerFactory emf) {
        if (emf == null) {
            throw new IllegalArgumentException("entityManagerFactory cannot be null");
        }
        this.emf = emf;
    }

    @Override
    public void create(Client client) {
        if (client != null && client.getId() != null) {
            throw new IllegalArgumentException("Cannot create entity with set id.");
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Client get(Long id) {
        EntityManager em = emf.createEntityManager();
        return em.find(Client.class, id);
    }

    @Override
    public void update(Client client) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(client);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Client client) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(client));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Client> findAll() {
        EntityManager em = emf.createEntityManager();
        return (List<Client>)em.createQuery("SELECT c FROM Client c").getResultList();
    }
}
