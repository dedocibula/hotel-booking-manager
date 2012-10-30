/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.test.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.Contact;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ClientService;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.hasItems;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Filip Bogyai
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ClientServiceImplTest {
    
    private ClientService clientService;
    
    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        clientService = context.getBean(ClientService.class);
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
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Client client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact);
        clientService.createClient(client);
        
        Client client2 = clientService.findClient(client.getId());
        assertThat(client2, is(not(sameInstance(client))));
        assertThat(client2, is(notNullValue()));
        assertThat(client, is(equalTo(client2)));
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
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Client client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact);
        client.setId(5l);
        try {
            clientService.createClient(client);
            fail("Cannot change id for existing client.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create client with null contact attribute
        client = App.DatabaseSampler.buildClient("Andrej","Zamocky", null);
        try {
            clientService.createClient(client);
            fail("Contact cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create client with null first name attribute
        client = App.DatabaseSampler.buildClient(null, "chudak", contact);
        try {
            clientService.createClient(client);
            fail("First name cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create client with null last name attribute
        client = App.DatabaseSampler.buildClient("Jozef", null, contact);
        try {
            clientService.createClient(client);
            fail("Last Name cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create client with too first long name
        client = App.DatabaseSampler.buildClient("Trolololololololololololololololololololololololololo","Mrazik", contact);
        try {
            clientService.createClient(client);
            fail("First Name cannot exceed 50 characters.");
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }
        
        // create client with too last long name
        client = App.DatabaseSampler.buildClient("Marek","Trolololololololololololololololololololololololololo", contact);
        try {
            clientService.createClient(client);
            fail("Last Name cannot exceed 30 characters.");
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }
        
        // create client with contact missing phone number
        contact = App.DatabaseSampler.buildContact(null, "dude@dude.sk", "address", "city", "country");
        client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("Phone number cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create client with contact missing email address
        contact = App.DatabaseSampler.buildContact("123", null, "address", "city", "country");
        client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("Email address cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create client with contact missing address
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", null, "city", "country");
        client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("Address cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create client with contact missing city
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", null, "country");
        client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("City cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create client with contact missing country
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", null);
        client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact);
        try {
            clientService.createClient(client);
            fail("Country cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
    }
    
 
    /**
     * Test of findClient method, of class ClientServiceImpl.
     */
    @Test
    public void testFindClient() {
        assertThat(clientService.findClient(0l), is(nullValue()));
        try {
            clientService.findClient(null);
            fail("No IllegalArgumentException for null id");
        } catch (IllegalArgumentException e) {
            //Ok
        }

        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Client client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact);
        clientService.createClient(client);
        
        Client testClient1 = clientService.findClient(client.getId());
        Client testClient2 = clientService.findClient(client.getId());
        
        assertThat(testClient1, is(not(sameInstance(testClient2))));
        assertThat(testClient1, is(equalTo(testClient2)));
        assertThat(testClient1.getLastName(), is(equalTo(testClient2.getLastName())));
        assertThat(testClient1.getFirstName(), is(equalTo(testClient2.getFirstName())));
        assertThat(testClient1.getContact().getAddress(), is(equalTo(testClient2.getContact().getAddress())));
        assertThat(testClient1.getContact().getCity(), is(equalTo(testClient2.getContact().getCity())));
        assertThat(testClient1.getContact().getCountry(), is(equalTo(testClient2.getContact().getCountry())));
        assertThat(testClient1.getContact().getEmail(), is(equalTo(testClient2.getContact().getEmail())));
        assertThat(testClient1.getContact().getPhone(), is(equalTo(testClient2.getContact().getPhone())));
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
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Client client1 = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact1);
        try {
            clientService.updateClient(client1);
            fail("Cannot update a client not present in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        Client client = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact1);
        client.setId(30l);
        try {
            clientService.updateClient(client1);
            fail("Cannot update a client not present in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        clientService.createClient(client1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Client client2 = App.DatabaseSampler.buildClient("Marian", "Vysoky", contact2);
        clientService.createClient(client2);
        
        // changing client first name
        client1.setFirstName("Dude");
        clientService.updateClient(client1);
        Client temp = clientService.findClient(client1.getId());
        assertThat("Client name not updated", temp.getFirstName(), is(equalTo("Dude")));
        assertThat("Client name not updated", temp.getLastName(), is(equalTo("Zamocky")));
        assertThat("Client phone number updated", temp.getContact().getPhone(), is(equalTo("123")));
        assertThat("Client email address updated", temp.getContact().getEmail(), is(equalTo("dude@dude.sk")));
        assertThat("Client address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Client city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Client country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing client last name
        client1.setLastName("Nizky");
        clientService.updateClient(client1);
        temp = clientService.findClient(client1.getId());
        assertThat("Client name not updated", temp.getFirstName(), is(equalTo("Dude")));
        assertThat("Client name not updated", temp.getLastName(), is(equalTo("Nizky")));
        assertThat("Client phone number updated", temp.getContact().getPhone(), is(equalTo("123")));
        assertThat("Client email address updated", temp.getContact().getEmail(), is(equalTo("dude@dude.sk")));
        assertThat("Client address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Client city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Client country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing client phone number
        client1.getContact().setPhone("555");
        clientService.updateClient(client1);
        temp = clientService.findClient(client1.getId());
        assertThat("Client name updated", temp.getFirstName(), is(equalTo("Dude")));
         assertThat("Client name not updated", temp.getLastName(), is(equalTo("Nizky")));
        assertThat("Client phone not number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Client email address updated", temp.getContact().getEmail(), is(equalTo("dude@dude.sk")));
        assertThat("Client address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Client city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Client country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing client email address
        client1.getContact().setEmail("ahoj@dude.sk");
        clientService.updateClient(client1);
        temp = clientService.findClient(client1.getId());
        assertThat("Client name updated", temp.getFirstName(), is(equalTo("Dude")));
        assertThat("Client name not updated", temp.getLastName(), is(equalTo("Nizky")));
        assertThat("Client phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Client email not address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Client address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Client city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Client country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing client address
        client1.getContact().setAddress("cowley");
        clientService.updateClient(client1);
        temp = clientService.findClient(client1.getId());
        assertThat("Client name updated", temp.getFirstName(), is(equalTo("Dude")));
         assertThat("Client name not updated", temp.getLastName(), is(equalTo("Nizky")));
        assertThat("Client phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Client email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Client address not updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Client city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Client country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing client city
        client1.getContact().setCity("london");
        clientService.updateClient(client1);
        temp = clientService.findClient(client1.getId());
        assertThat("Client name updated", temp.getFirstName(), is(equalTo("Dude")));
         assertThat("Client name not updated", temp.getLastName(), is(equalTo("Nizky")));
        assertThat("Client phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Client email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Client address updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Client city not updated", temp.getContact().getCity(), is(equalTo("london")));
        assertThat("Client country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing client country
        client1.getContact().setCountry("UK");
        clientService.updateClient(client1);
        temp = clientService.findClient(client1.getId());
        assertThat("Client name updated", temp.getFirstName(), is(equalTo("Dude")));
         assertThat("Client name not updated", temp.getLastName(), is(equalTo("Nizky")));
        assertThat("Client phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Client email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Client address updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Client city updated", temp.getContact().getCity(), is(equalTo("london")));
        assertThat("Client country not updated", temp.getContact().getCountry(), is(equalTo("UK")));
        
        // checking if client2 remained unaffected
        temp = clientService.findClient(client2.getId());
        assertThat("Saved client has wrong name", temp.getFirstName(), is(equalTo("Marian")));
        assertThat("Client name not updated", temp.getLastName(), is(equalTo("Vysoky")));
        assertThat("Saved client has wrong phone number", temp.getContact().getPhone(), is(equalTo("333")));
        assertThat("Saved client has wrong email address", temp.getContact().getEmail(), is(equalTo("lol@lol.sk")));
        assertThat("Saved client has wrong address", temp.getContact().getAddress(), is(equalTo("dress")));
        assertThat("Saved client has wrong city", temp.getContact().getCity(), is(equalTo("ty")));
        assertThat("Saved client has wrong country", temp.getContact().getCountry(), is(equalTo("untry")));
    }
       

    /**
     * Test of findAllClients method, of class ClientServiceImpl.
     */
    @Test
    public void testFindAllClients() {
        assertTrue(clientService.findAllClients().isEmpty());
        
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Client client1 = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact1);
        clientService.createClient(client1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Client client2 = App.DatabaseSampler.buildClient("Fesak","Moloch", contact2);
        clientService.createClient(client2);
        
        assertThat(clientService.findAllClients(), hasItems(client1, client2));
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
        /*
        try {
            clientService.findClientsByLastName("");
            fail("Did not throw IllegalArgumentException on empty string");
        } catch (Exception e) {
            //OK
        }
        */
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Client client1 = App.DatabaseSampler.buildClient("Andrej","Zamocky", contact1);
        clientService.createClient(client1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Client client2 = App.DatabaseSampler.buildClient("Fesak","Moloch", contact2);
        clientService.createClient(client2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "dressdress", "tyty", "untry");
        Client client3 = App.DatabaseSampler.buildClient("Fesak","Vesely", contact3);
        clientService.createClient(client3);
        
        assertThat(clientService.findClientsByName("Andrej"), hasItems(client1));
        assertThat(clientService.findClientsByName("Fesak"), hasItems(client2, client3));
        assertThat(clientService.findClientsByName("Vesely"), hasItems(client3));
        assertThat(clientService.findClientsByName("Andrej Zamocky"), hasItems(client1));
        assertThat(clientService.findClientsByName("Vesely Fesak"), hasItems(client3));
        assertTrue(clientService.findClientsByName("Dude").isEmpty());
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
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Client client1 = App.DatabaseSampler.buildClient("Anton","Vysmiaty", contact1);
        clientService.createClient(client1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Client client2 = App.DatabaseSampler.buildClient("Zoltan","Zeleny", contact2);
        clientService.createClient(client2);
        
        assertThat(clientService.findClient(client1.getId()), is(notNullValue()));
        assertThat(clientService.findClient(client2.getId()), is(notNullValue()));
        
        clientService.deleteClient(client1);
        
        assertThat(clientService.findClient(client1.getId()), is(nullValue()));
        assertThat(clientService.findClient(client2.getId()), is(notNullValue()));
    }
}
   