package cz.fi.muni.pa165.test.dao.impl;


import cz.fi.muni.pa165.hotelbookingmanagerpersistence.App;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Contact;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marián Rusnák
 */

@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ClientDAOImplTest {

    private ClientDAO clientDAO;

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        clientDAO = context.getBean(ClientDAO.class);

    }

    @After
    public void tearDown() {
        for (Client client : clientDAO.findAll()) {
            clientDAO.delete(client);
        }
        clientDAO = null;
    }

    @Test
    public void testCreate() {
        Contact contact = newContact("123", "mail@mail.com", "address", "city", "country");
        Client client = newClient("Jozko", "Mrkvicka", contact);
        clientDAO.create(client);

        Client client2 = clientDAO.get(client.getId());
        assertThat(client2, is(not(sameInstance(client))));
        assertThat(client2, is(notNullValue()));
        assertThat(client, is(equalTo(client2)));
    }

    @Test
    public void testCreateWithNotNullId() {
        Contact contact = newContact("123", "mail@mail.com", "address", "city", "country");
        Client client = newClient("Jozko", "Mrkvicka", contact);
        client.setId(5l);

        try {
            clientDAO.create(client);
            fail("No DataAccessException thrown for creating entity with set id");
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void testCreateWithNull() {
        try {
            clientDAO.create(null);
            fail("No DataAccessException thrown for null parameter");
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void testGet() {
        Contact contact = newContact("123", "mail@mail.com", "address", "city", "country");
        Client client = newClient("Jozko", "Mrkvicka", contact);
        clientDAO.create(client);

        Client client1 = clientDAO.get(client.getId());
        Client client2 = clientDAO.get(client.getId());

        assertThat(client1, is(not(sameInstance(client2))));
        assertThat(client1, is(equalTo(client2)));
        assertThat(client1.getFirstName(), is(equalTo(client2.getFirstName())));
        assertThat(client1.getLastName(), is(equalTo(client2.getLastName())));
        assertThat(client1.getContact().getAddress(), is(equalTo(client2.getContact().getAddress())));
        assertThat(client1.getContact().getCity(), is(equalTo(client2.getContact().getCity())));
        assertThat(client1.getContact().getCountry(), is(equalTo(client2.getContact().getCountry())));
        assertThat(client1.getContact().getEmail(), is(equalTo(client2.getContact().getEmail())));
        assertThat(client1.getContact().getPhone(), is(equalTo(client2.getContact().getPhone())));
    }

    @Test
    public void testGetWithNull() {
        try {
            clientDAO.get(null);
            fail("No DataAccessException thrown for null id");
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void testUpdate() {
        Contact contact = newContact("123", "mail@mail.com", "address", "city", "country");
        Client client1 = newClient("FirstName", "LastName", contact);

        clientDAO.create(client1);

        client1.setFirstName("Jozko");
        client1.setLastName("Mrkvicka");
        client1.getContact().setAddress("changed address");

        clientDAO.update(client1);

        Client client2 = clientDAO.get(client1.getId());

        assertThat(client2.getFirstName(), is(equalTo("Jozko")));
        assertThat(client2.getLastName(), is(equalTo("Mrkvicka")));
        assertThat(client2.getContact().getAddress(), is(equalTo("changed address")));
        assertThat(client1.getContact().getCity(), is(equalTo(client2.getContact().getCity())));
        assertThat(client1.getContact().getCountry(), is(equalTo(client2.getContact().getCountry())));
        assertThat(client1.getContact().getEmail(), is(equalTo(client2.getContact().getEmail())));
        assertThat(client1.getContact().getPhone(), is(equalTo(client2.getContact().getPhone())));
    }

    @Test
    public void testUpdateWithNull() {
        try {
            clientDAO.update(null);
            fail("No DataAccessException thrown for null parameter");
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void testDelete() {
        Contact contact1 = newContact("123", "mail1@mail.com", "address", "city", "country");
        Client client1 = newClient("Jozko", "Mrkvicka", contact1);
        clientDAO.create(client1);

        Contact contact2 = newContact("456", "mail2@mail.com", "address", "city", "country");
        Client client2 = newClient("Peter", "Novak", contact2);
        clientDAO.create(client2);

        assertThat(clientDAO.get(client1.getId()), is(notNullValue()));
        assertThat(clientDAO.get(client2.getId()), is(notNullValue()));

        clientDAO.delete(client1);

        assertThat(clientDAO.get(client1.getId()), is(nullValue()));
        assertThat(clientDAO.get(client2.getId()), is(notNullValue()));
    }

    @Test
    public void testDeleteWithNull() {
        try {
            clientDAO.delete(null);
            fail("No DataAccessException thrown for null parameter");
        } catch (DataAccessException ex) {
            // OK
        }
    }

    @Test
    public void testFindAll() {
        for (Client client : clientDAO.findAll()) {
            clientDAO.delete(client);
        }
        assertTrue(clientDAO.findAll().isEmpty());

        Contact contact1 = newContact("123", "mail1@mail.com", "address", "city", "country");
        Client client1 = newClient("Jozko", "Mrkvicka", contact1);
        clientDAO.create(client1);

        Contact contact2 = newContact("456", "mail2@mail.com", "address", "city", "country");
        Client client2 = newClient("Peter", "Novak", contact2);
        clientDAO.create(client2);

        assertThat(clientDAO.findAll(), hasItems(client1, client2));
    }

    private static Client newClient(String firstName, String lastName, Contact contact) {
        return App.DatabaseSampler.buildClient(firstName, lastName, contact);
    }

    private static Contact newContact(String phone, String email,
                String address, String city, String country) {
        return App.DatabaseSampler.buildContact(phone, email, address, city, country);
    }
}
