package cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RegUserDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.RegUser;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author felipe
 */

@Repository
public class RegUserDAOImpl implements RegUserDAO{
    
    @PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void create(RegUser user) {
		if (user != null && user.getId() != null) {
			throw new IllegalArgumentException("The id of user is automatically set, must not be set manually.");
		}
		em.persist(user);
	}

	@Override
	public RegUser get(Long id) {
		return em.find(RegUser.class, id);
	}

	@Override
	@Transactional
	public void update(RegUser user) {
		em.merge(user);
	}

	@Override
	@Transactional
	public void delete(RegUser user) {
		em.remove(em.merge(user));
	}

	
	@Override
	public RegUser findUserByClient(Client client) {
		Query query = em.createQuery("SELECT u FROM RegUser u WHERE u.client = :client");
		query.setParameter("client", client);
		return (RegUser) query.getSingleResult();
	}

	@Override
	public RegUser findUserByUsername(String username) {
		Query query = em.createQuery("SELECT u FROM RegUser u WHERE UPPER(username) LIKE UPPER(:username)");
		query.setParameter("username", username + "%");
		return (RegUser) query.getSingleResult();
	}
    
}
