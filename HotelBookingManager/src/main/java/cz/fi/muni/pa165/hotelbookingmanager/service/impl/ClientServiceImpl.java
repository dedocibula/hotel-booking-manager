package cz.fi.muni.pa165.hotelbookingmanager.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ClientService;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
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
    private Validator validator;

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    
    @Override
    @Transactional
    public void createClient(Client client) {
        if (client == null)
            throw new IllegalArgumentException("client cannot be null");
        if (client.getId() != null)
            throw new IllegalArgumentException("client cannot have manually assigned id");
        validateClientAttrributes(client);
        clientDAO.create(client);
    }

    @Override
    public Client findClient(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id cannot be null");
        return clientDAO.get(id);
    }

    @Override
    @Transactional
    public void updateClient(Client client) {
         if (client == null)
            throw new IllegalArgumentException("client cannot be null");
        if (client.getId() == null || clientDAO.get(client.getId()) == null) 
            throw new IllegalArgumentException("trying to update non-existent client");
        validateClientAttrributes(client);
        clientDAO.update(client);
    }
    

    @Override
    @Transactional
    public void deleteClient(Client client) {
        if (client == null)
            throw new IllegalArgumentException("client cannot be null");
        clientDAO.delete(client);
    }

    @Override
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

     @Override
     public List<Client> findClientsByName(String name) {
         if((name == null) || (name.trim().equals("")))
             throw new IllegalArgumentException("name cannot be null");
        return clientDAO.findClientsByName(name);
    }
    
    private void validateClientAttrributes(Client client) throws IllegalArgumentException {
        
        Set<ConstraintViolation<Client>> validationResults = validator.validate(client);
        if (!validationResults.isEmpty())
            throw new IllegalArgumentException("client parameters are invalid: " + validationResults.iterator().next().getMessage());
    }
   

}
