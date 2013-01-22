package cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.ClientService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RegUserDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.RegUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Filip Bogyai
 */
@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDAO clientDAO;
	@Autowired
	private RegUserDAO userDAO;
	@Autowired
	private Validator validator;
	@Autowired
	private Mapper mapper;

	@Override
	@Transactional
	public void createClient(ClientTO client) {
		if (client == null) {
			throw new IllegalArgumentException("client cannot be null");
		}
		if (client.getId() != null) {
			throw new IllegalArgumentException("client cannot have manually assigned id");
		}
		Client clientDO = mapper.map(client, Client.class);

		validateClientAttrributes(clientDO);
		clientDAO.create(clientDO);

		client.setId(clientDO.getId());
	}

	@Override
	public ClientTO findClient(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("id cannot be null");
		}
		if (clientDAO.get(id) == null) {
			return null;
		} else {
			return mapper.map(clientDAO.get(id), ClientTO.class);
		}
	}

	@Override
	@Transactional
	public void updateClient(ClientTO client) {
		if (client == null) {
			throw new IllegalArgumentException("client cannot be null");
		}
		if (client.getId() == null || clientDAO.get(client.getId()) == null) {
			throw new IllegalArgumentException("trying to update non-existent client");
		}
		Client clientDO = mapper.map(client, Client.class);

		validateClientAttrributes(clientDO);
		clientDAO.update(clientDO);
	}

	@Override
	@Transactional
	public void deleteClient(ClientTO client) {
		if (client == null) {
			throw new IllegalArgumentException("client cannot be null");
		}
		Client clientDO = mapper.map(client, Client.class);
		
		// Backward compatibility
		try {
			RegUser user = userDAO.findUserByClient(clientDO);
			userDAO.delete(user);
		} catch (DataAccessException e) {
			// No user found
		}
                clientDAO.delete(clientDO);
	}

	@Override
	public List<ClientTO> findAllClients() {

		List<Client> clients = clientDAO.findAll();
		List<ClientTO> clientsTO = new ArrayList<>();
		for (Client clientDO : clients) {
			clientsTO.add(mapper.map(clientDO, ClientTO.class));
		}

		return clientsTO;
	}

	@Override
	public List<ClientTO> findClientsByName(String name) {
		if ((name == null) || (name.trim().equals(""))) {
			throw new IllegalArgumentException("name cannot be null");
		}

		List<Client> clients = clientDAO.findClientsByName(name);
		List<ClientTO> clientsTO = new ArrayList<>();
		for (Client clientDO : clients) {
			clientsTO.add(mapper.map(clientDO, ClientTO.class));
		}

		return clientsTO;
	}

	private void validateClientAttrributes(Client client) throws IllegalArgumentException {

		Set<ConstraintViolation<Client>> validationResults = validator.validate(client);
		if (!validationResults.isEmpty()) {
			throw new IllegalArgumentException("client parameters are invalid: " + validationResults.iterator().next().getMessage());
		}
	}
}