package cz.fi.muni.pa165.test.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.service.impl.ClientServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ContactTO;
import javax.validation.Validator;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Filip Bogyai
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ClientServiceImplTest {

    private ClientDAO mockClientDao;
    private ClientServiceImpl clientService;
    private Mapper mapper;

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");

        clientService = new ClientServiceImpl();

        mockClientDao = mock(ClientDAO.class);
        Validator validator = context.getBean("validator", org.springframework.validation.beanvalidation.LocalValidatorFactoryBean.class);
        mapper = context.getBean("mapper", DozerBeanMapper.class);

        ReflectionTestUtils.setField(clientService, "clientDAO", mockClientDao);
        ReflectionTestUtils.setField(clientService, "validator", validator);
        ReflectionTestUtils.setField(clientService, "mapper", mapper);
    }

    @After
    public void tearDown() {
        clientService = null;
    }

    /**
     * Test of createClient method, of class ClientServiceImpl.
     */
    @Test
    public void testCreateClient() {
        ClientTO client = sampleClient();

        clientService.createClient(client);

        verify(mockClientDao).create(mapper.map(client, Client.class));
    }

    /**
     * Test of createClient method, of class ClientServiceImpl, with wrong attributes.
     */
    @Test
    public void testCreateClientWithWrongAttributes() {
        // create with null parameter
        try {
            clientService.createClient(null);
            fail("No IllegalArgumentException for empty input.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // manually setting id
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        ClientTO client = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact);
        client.setId(5l);
        try {
            clientService.createClient(client);
            fail("Cannot change id for existing client.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create client with null contact attribute
        client = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", null);
        try {
            clientService.createClient(client);
            fail("Contact cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create client with null first name attribute
        client = App.DatabaseSampler.buildClientTO(null, "chudak", contact);
        try {
            clientService.createClient(client);
            fail("First name cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create client with null last name attribute
        client = App.DatabaseSampler.buildClientTO("Jozef", null, contact);
        try {
            clientService.createClient(client);
            fail("Last Name cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create client with too first long name
        client = App.DatabaseSampler.buildClientTO("Trolololololololololololololololololololololololololo","Mrazik", contact);
        try {
            clientService.createClient(client);
            fail("First Name cannot exceed 50 characters.");
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        // create client with too last long name
        client = App.DatabaseSampler.buildClientTO("Marek","Trolololololololololololololololololololololololololo", contact);
        try {
            clientService.createClient(client);
            fail("Last Name cannot exceed 30 characters.");
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        // create client with contact missing phone number
        contact = App.DatabaseSampler.buildContactTO(null, "dude@dude.sk", "address", "city", "country");
        client = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("Phone number cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create client with contact missing email address
        contact = App.DatabaseSampler.buildContactTO("123", null, "address", "city", "country");
        client = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("Email address cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create client with contact missing address
        contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", null, "city", "country");
        client = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("Address cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create client with contact missing city
        contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", null, "country");
        client = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("City cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create client with contact missing country
        contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", null);
        client = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("Country cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        verify(mockClientDao, never()).create(any(Client.class));
    }


    /**
     * Test of findClient method, of class ClientServiceImpl.
     */
    @Test
    public void testFindClient() {
        try {
            clientService.findClient(null);
            fail("No IllegalArgumentException for null id");
        } catch (IllegalArgumentException e) {
            //Ok
        }

        verify(mockClientDao, never()).get(anyLong());

        ClientTO client = sampleClient();

        when(mockClientDao.get(1L)).thenReturn(mapper.map(client, Client.class));

        clientService.findClient(1L);

        verify(mockClientDao, times(2)).get(1L);
    }

    /**
     * Test of updateClient method, of class ClientServiceImpl.
     */
    @Test
    public void testUpdateClient() {
        try {
            clientService.updateClient(null);
            fail("No IllegalArgumentException for empty input.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        ContactTO contact1 = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        ClientTO client1 = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact1);
        try {
            clientService.updateClient(client1);
            fail("Cannot update a client not present in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        ClientTO client2 = App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact1);
        client2.setId(30l);
        try {
            clientService.updateClient(client2);
            fail("Cannot update a client not present in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        verify(mockClientDao, never()).update(any(Client.class));

        ClientTO client = sampleClient();
        client.setId(1L);
        when(mockClientDao.get(1L)).thenReturn(mapper.map(client, Client.class));

        clientService.updateClient(client);

        verify(mockClientDao).update(mapper.map(client, Client.class));
    }


    /**
     * Test of findAllClients method, of class ClientServiceImpl.
     */
    @Test
    public void testFindAllClients() {
        clientService.findAllClients();

        verify(mockClientDao).findAll();
    }

    /**
     * Test of findClientsByName method, of class ClientServiceImpl.
     */
    @Test
    public void testFindClientsByName() {
        try {
            clientService.findClientsByName(null);
            fail("Did not throw IllegalArgumentException on null");
        } catch (Exception e) {
            //OK
        }

        try {
            clientService.findClientsByName("");
            fail("Did not throw IllegalArgumentException on empty string");
        } catch (Exception e) {
            //OK
        }

        verify(mockClientDao, never()).findClientsByName(anyString());

        clientService.findClientsByName("Jozko");

        verify(mockClientDao).findClientsByName("Jozko");
    }

    /**
     * Test of deleteHotel method, of class HotelServiceImpl.
     */
    @Test
    public void testDeleteClient() {
        try {
            clientService.deleteClient(null);
            fail("Did not throw IllegalArgumentException on deleting null Client");
        } catch (Exception e) {
            //OK
        }

        verify(mockClientDao, never()).delete(any(Client.class));

        ContactTO contact1 = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        ClientTO client1 = App.DatabaseSampler.buildClientTO("Anton","Vysmiaty", contact1);
        client1.setId(1L);

        ContactTO contact2 = App.DatabaseSampler.buildContactTO("333", "lol@lol.sk", "dress", "ty", "untry");
        ClientTO client2 = App.DatabaseSampler.buildClientTO("Zoltan","Zeleny", contact2);
        client2.setId(2L);

        clientService.deleteClient(client1);

        verify(mockClientDao).delete(mapper.map(client1, Client.class));
        verify(mockClientDao, never()).delete(mapper.map(client2, Client.class));
    }

    public static ClientTO sampleClient() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        return App.DatabaseSampler.buildClientTO("Andrej","Zamocky", contact);
    }
}
