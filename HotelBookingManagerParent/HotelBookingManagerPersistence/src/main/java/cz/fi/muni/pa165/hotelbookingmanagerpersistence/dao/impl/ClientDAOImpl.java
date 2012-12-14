package cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Client;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Andrej Galád
 */
@Repository
public class ClientDAOImpl implements ClientDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void create(Client client) {
		if (client != null && client.getId() != null) {
			throw new IllegalArgumentException("Cannot create entity with set id.");
		}
		em.persist(client);

	}

	@Override
	public Client get(Long id) {
		return em.find(Client.class, id);
	}

	@Override
	@Transactional
	public void update(Client client) {
		em.merge(client);
	}

	@Override
	@Transactional
	public void delete(Client client) {
		em.remove(em.merge(client));

	}

	@Override
	public List<Client> findAll() {
		return (List<Client>) em.createQuery("SELECT c FROM Client c").getResultList();
	}

	@Override
	public List<Client> findClientsByName(String name) {
		name = name.replaceAll(" ", "");
		Query query = em.createQuery("SELECT c FROM Client c WHERE UPPER(CONCAT(c.firstName,c.lastName,c.firstName)) LIKE UPPER(:name)");
		query.setParameter("name", "%" + name + "%");
		return (List<Client>) query.getResultList();
	}
}
