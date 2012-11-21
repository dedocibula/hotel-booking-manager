package cz.fi.muni.pa165.test.dao.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.Contact;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import javax.validation.ConstraintViolationException;
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
 * @author Andrej Galád
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class HotelDAOImplTest {
    
    private HotelDAO hotelDAO;

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        hotelDAO = context.getBean(HotelDAO.class);
    }
    
    @After
    public void tearDown() {
        hotelDAO = null;
    }

    /**
     * Test of create method, of class HotelDAOImpl.
     */
    @Test
    public void testCreateHotel() {
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        hotelDAO.create(hotel);
        
        Hotel hotel2 = hotelDAO.get(hotel.getId());
        assertThat(hotel2, is(not(sameInstance(hotel))));
        assertThat(hotel2, is(notNullValue()));
        assertThat(hotel, is(equalTo(hotel2)));
    }

    /**
     * Test of create method, of class HotelDAOImpl, with wrong attributes.
     */
    @Test
    public void testCreateHotelWithWrongAttributes() {
        // create with null parameter
        try {
            hotelDAO.create(null);
            fail("No DataAccessException for empty input.");
        } catch (DataAccessException e) {
            //OK
        }
        
        // manually setting id
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        hotel.setId(5l);
        try {
            hotelDAO.create(hotel);
            fail("Cannot change id for existing hotel.");
        } catch (DataAccessException e) {
            //OK
        }
        
        // create hotel with null contact attribute
        hotel = App.DatabaseSampler.buildHotel("Hilton", null);
        try {
            hotelDAO.create(hotel);
            fail("Contact cannot be null.");
        } catch (ConstraintViolationException e) {
            //OK
        }
        
        // create hotel with null name attribute
        hotel = App.DatabaseSampler.buildHotel(null, contact);
        try {
            hotelDAO.create(hotel);
            fail("Name cannot be null.");
        } catch (ConstraintViolationException e) {
            //OK
        }
        
        // create hotel with too long name
        hotel = App.DatabaseSampler.buildHotel("Trololololololololololololololololololololololo", contact);
        try {
            hotelDAO.create(hotel);
            fail("Name cannot exceed 30 characters.");
        } catch (ConstraintViolationException e) {
            System.out.println("");
        }
        
        // create hotel with contact missing phone number
        contact = App.DatabaseSampler.buildContact(null, "dude@dude.sk", "address", "city", "country");
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelDAO.create(hotel);
            fail("Phone number cannot be null.");
        } catch (ConstraintViolationException e) {
            //OK
        }
        
        // create hotel with contact missing email address
        contact = App.DatabaseSampler.buildContact("123", null, "address", "city", "country");
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelDAO.create(hotel);
            fail("Email address cannot be null.");
        } catch (ConstraintViolationException e) {
            //OK
        }
        
        // create hotel with contact missing address
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", null, "city", "country");
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelDAO.create(hotel);
            fail("Address cannot be null.");
        } catch (ConstraintViolationException e) {
            //OK
        }
        
        // create hotel with contact missing city
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", null, "country");
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelDAO.create(hotel);
            fail("City cannot be null.");
        } catch (ConstraintViolationException e) {
            //OK
        }
        
        // create hotel with contact missing country
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", null);
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelDAO.create(hotel);
            fail("Country cannot be null.");
        } catch (ConstraintViolationException e) {
            //OK
        }
    }
    
    /**
     * Test of get method, of class HotelDAOImpl.
     */
    @Test
    public void testGetHotel() {
        assertThat(hotelDAO.get(0l), is(nullValue()));
        try {
            hotelDAO.get(null);
            fail("No DataAccessException for null id");
        } catch (DataAccessException e) {
            //Ok
        }

        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        hotelDAO.create(hotel);
        
        Hotel testHotel1 = hotelDAO.get(hotel.getId());
        Hotel testHotel2 = hotelDAO.get(hotel.getId());
        
        assertThat(testHotel1, is(not(sameInstance(testHotel2))));
        assertThat(testHotel1, is(equalTo(testHotel2)));
        assertThat(testHotel1.getName(), is(equalTo(testHotel2.getName())));
        assertThat(testHotel1.getContact().getAddress(), is(equalTo(testHotel2.getContact().getAddress())));
        assertThat(testHotel1.getContact().getCity(), is(equalTo(testHotel2.getContact().getCity())));
        assertThat(testHotel1.getContact().getCountry(), is(equalTo(testHotel2.getContact().getCountry())));
        assertThat(testHotel1.getContact().getEmail(), is(equalTo(testHotel2.getContact().getEmail())));
        assertThat(testHotel1.getContact().getPhone(), is(equalTo(testHotel2.getContact().getPhone())));
    }

    /**
     * Test of update method, of class HotelDAOImpl.
     */
    @Test
    public void testUpdateHotel() {
        try {
            hotelDAO.update(null);
            fail("No DataAccessException for empty input.");
        } catch (DataAccessException e) {
            //OK
        }
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelDAO.create(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelDAO.create(hotel2);
        
        // changing hotel name
        hotel1.setName("Dude");
        hotelDAO.update(hotel1);
        Hotel temp = hotelDAO.get(hotel1.getId());
        assertThat("Hotel name not updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("123")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("dude@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel phone number
        hotel1.getContact().setPhone("555");
        hotelDAO.update(hotel1);
        temp = hotelDAO.get(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone not number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("dude@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel email address
        hotel1.getContact().setEmail("ahoj@dude.sk");
        hotelDAO.update(hotel1);
        temp = hotelDAO.get(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email not address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel address
        hotel1.getContact().setAddress("cowley");
        hotelDAO.update(hotel1);
        temp = hotelDAO.get(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Hotel address not updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel city
        hotel1.getContact().setCity("london");
        hotelDAO.update(hotel1);
        temp = hotelDAO.get(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Hotel city not updated", temp.getContact().getCity(), is(equalTo("london")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel country
        hotel1.getContact().setCountry("UK");
        hotelDAO.update(hotel1);
        temp = hotelDAO.get(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("london")));
        assertThat("Hotel country not updated", temp.getContact().getCountry(), is(equalTo("UK")));
        
        // checking if hotel2 remained unaffected
        temp = hotelDAO.get(hotel2.getId());
        assertThat("Saved hotel has wrong name", temp.getName(), is(equalTo("Crowne")));
        assertThat("Saved hotel has wrong phone number", temp.getContact().getPhone(), is(equalTo("333")));
        assertThat("Saved hotel has wrong email address", temp.getContact().getEmail(), is(equalTo("lol@lol.sk")));
        assertThat("Saved hotel has wrong address", temp.getContact().getAddress(), is(equalTo("dress")));
        assertThat("Saved hotel has wrong city", temp.getContact().getCity(), is(equalTo("ty")));
        assertThat("Saved hotel has wrong country", temp.getContact().getCountry(), is(equalTo("untry")));
    }

    /**
     * Test of delete method, of class HotelDAOImpl.
     */
    @Test
    public void testDeleteHotel() {
        try {
            hotelDAO.delete(null);
            fail("Did not throw DataAccessException on deleting null Hotel");
        } catch (Exception e) {
            //OK
        }
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelDAO.create(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelDAO.create(hotel2);
        
        assertThat(hotelDAO.get(hotel1.getId()), is(notNullValue()));
        assertThat(hotelDAO.get(hotel2.getId()), is(notNullValue()));
        
        hotelDAO.delete(hotel1);
        
        assertThat(hotelDAO.get(hotel1.getId()), is(nullValue()));
        assertThat(hotelDAO.get(hotel2.getId()), is(notNullValue()));
    }

    /**
     * Test of findAll method, of class HotelDAOImpl.
     */
    @Test
    public void testFindAllHotels() {
        assertTrue(hotelDAO.findAll().isEmpty());
        
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelDAO.create(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelDAO.create(hotel2);
        
        assertThat(hotelDAO.findAll(), hasItems(hotel1, hotel2));
    }
    
    /**
     * Test of findHotelsByName method, of class HotelDAOImpl.
     */
    @Test
    public void testFindHotelsByName() {
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelDAO.create(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelDAO.create(hotel2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "dressdress", "tyty", "untry");
        Hotel hotel3 = App.DatabaseSampler.buildHotel("Crowne", contact3);
        hotelDAO.create(hotel3);
        
        assertThat(hotelDAO.findHotelsByName("Hilton"), hasItems(hotel1));
        assertThat(hotelDAO.findHotelsByName("Crowne"), hasItems(hotel2, hotel3));
        assertTrue(hotelDAO.findHotelsByName("Dude").isEmpty());
    }

    /**
     * Test of findHotelsByAddress method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotelsByAddress() {
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelDAO.create(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "troll", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelDAO.create(hotel2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "address", "tyty", "untry");
        Hotel hotel3 = App.DatabaseSampler.buildHotel("Jewel", contact3);
        hotelDAO.create(hotel3);
        
        assertThat(hotelDAO.findHotelsByAddress("troll"), hasItems(hotel2));
        assertThat(hotelDAO.findHotelsByAddress("address"), hasItems(hotel1, hotel3));
        assertTrue(hotelDAO.findHotelsByAddress("Dude").isEmpty());
    }

    /**
     * Test of findHotelsByCity method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotelsByCity() {       
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelDAO.create(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelDAO.create(hotel2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "dressdress", "city", "untry");
        Hotel hotel3 = App.DatabaseSampler.buildHotel("Jewel", contact3);
        hotelDAO.create(hotel3);
        
        assertThat(hotelDAO.findHotelsByCity("ty"), hasItems(hotel2));
        assertThat(hotelDAO.findHotelsByCity("city"), hasItems(hotel1, hotel3));
        assertTrue(hotelDAO.findHotelsByCity("Dude").isEmpty());
    }

    /**
     * Test of findHotelsByCountry method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotelsByCountry() {
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "USA");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelDAO.create(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "UK");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelDAO.create(hotel2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "dressdress", "tyty", "USA");
        Hotel hotel3 = App.DatabaseSampler.buildHotel("Crowne", contact3);
        hotelDAO.create(hotel3);
        
        assertThat(hotelDAO.findHotelsByCountry("UK"), hasItems(hotel2));
        assertThat(hotelDAO.findHotelsByCountry("USA"), hasItems(hotel1, hotel3));
        assertTrue(hotelDAO.findHotelsByCountry("Dude").isEmpty());
    }
}
