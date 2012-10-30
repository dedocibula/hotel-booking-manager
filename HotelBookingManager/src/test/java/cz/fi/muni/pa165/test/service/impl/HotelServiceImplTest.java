/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.test.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.Contact;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import java.math.BigDecimal;
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
 * @author Andrej Galád
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class HotelServiceImplTest {
    
    private HotelService hotelService;
    
    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        hotelService = context.getBean(HotelService.class);
    }
    
    @After
    public void tearDown() {
        hotelService = null;
    }

    /**
     * Test of createHotel method, of class HotelServiceImpl.
     */
    @Test
    public void testCreateHotelWithoutRooms() {
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        hotelService.createHotel(hotel);
        
        Hotel hotel2 = hotelService.findHotel(hotel.getId());
        assertThat(hotel2, is(not(sameInstance(hotel))));
        assertThat(hotel2, is(notNullValue()));
        assertThat(hotel, is(equalTo(hotel2)));
    }
    
    /**
     * Test of createHotel method, of class HotelServiceImpl, with wrong attributes.
     */
    @Test
    public void testCreateHotelWithoutRoomsWithWrongAttributes() {
        // create with null parameter
        try {
            hotelService.createHotel(null);
            fail("No IllegalArgumentException for empty input.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // manually setting id
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        hotel.setId(5l);
        try {
            hotelService.createHotel(hotel);
            fail("Cannot change id for existing hotel.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create hotel with null contact attribute
        hotel = App.DatabaseSampler.buildHotel("Hilton", null);
        try {
            hotelService.createHotel(hotel);
            fail("Contact cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create hotel with null name attribute
        hotel = App.DatabaseSampler.buildHotel(null, contact);
        try {
            hotelService.createHotel(hotel);
            fail("Name cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create hotel with too long name
        hotel = App.DatabaseSampler.buildHotel("Trololololololololololololololololololololololo", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Name cannot exceed 30 characters.");
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }
        
        // create hotel with contact missing phone number
        contact = App.DatabaseSampler.buildContact(null, "dude@dude.sk", "address", "city", "country");
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Phone number cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create hotel with contact missing email address
        contact = App.DatabaseSampler.buildContact("123", null, "address", "city", "country");
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Email address cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create hotel with contact missing address
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", null, "city", "country");
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Address cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create hotel with contact missing city
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", null, "country");
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("City cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // create hotel with contact missing country
        contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", null);
        hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Country cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
    }
    
    /**
     * Test of createHotel method, of class HotelServiceImpl, with rooms.
     */
    @Test
    public void testCreateHotelWithRooms() {
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");        
        Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        Room room2 = App.DatabaseSampler.buildRoom(BigDecimal.TEN, null);
        Hotel hotel = App.DatabaseSampler.buildHotelWithRooms("Hilton", contact, room1, room2);
        hotelService.createHotel(hotel);
        
        Hotel hotel2 = hotelService.findHotel(hotel.getId());
        assertThat(hotel2, is(not(sameInstance(hotel))));
        assertThat(hotel2, is(notNullValue()));
        assertThat(hotel, is(equalTo(hotel2)));
        assertFalse(hotel2.getRooms().isEmpty());
        assertThat(hotel2.getRooms(), hasItems(room1, room2));
        assertThat(hotel2.getRooms().get(0).getHotel(), is(equalTo(hotel)));
        assertThat(hotel2.getRooms().get(1).getHotel(), is(equalTo(hotel)));
    }
    
    /**
     * Test of createHotel method, of class HotelServiceImpl, with rooms having wrong attributes.
     */
    @Test
    public void testCreateHotelWithRoomsWithWrongAttributes() {
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");        
        
        // One of hotel's room is null
        Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        Room room2 = null;
        Hotel hotel = App.DatabaseSampler.buildHotelWithRooms("Hilton", contact, room1, room2);
        try {
            hotelService.createHotel(hotel);
            fail("No IllegalArgumentException for null room.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // One of hotel's room has assigned id
        room2 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        room2.setId(25l);
        hotel = App.DatabaseSampler.buildHotelWithRooms("Hilton", contact, room1, room2);
        try {
            hotelService.createHotel(hotel);
            fail("Room's id cannot be setted manually or room cannot be already in database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // One of hotel's room has null pricePerNight
        room2 = App.DatabaseSampler.buildRoom(null, null);
        hotel = App.DatabaseSampler.buildHotelWithRooms("Hilton", contact, room1, room2);
        try {
            hotelService.createHotel(hotel);
            fail("Room's pricePerNight cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
    }

    /**
     * Test of findHotel method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotel() {
        assertThat(hotelService.findHotel(0l), is(nullValue()));
        try {
            hotelService.findHotel(null);
            fail("No IllegalArgumentException for null id");
        } catch (IllegalArgumentException e) {
            //Ok
        }

        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact);
        hotelService.createHotel(hotel);
        
        Hotel testHotel1 = hotelService.findHotel(hotel.getId());
        Hotel testHotel2 = hotelService.findHotel(hotel.getId());
        
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
     * Test of updateHotel method, of class HotelServiceImpl.
     */
    @Test
    public void testUpdateHotelWithoutRooms() {
        try {
            hotelService.updateHotel(null);
            fail("No IllegalArgumentException for empty input.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        try {
            hotelService.updateHotel(hotel1);
            fail("Cannot update a hotel not present in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        Hotel hotel = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotel.setId(30l);
        try {
            hotelService.updateHotel(hotel1);
            fail("Cannot update a hotel not present in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        hotelService.createHotel(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelService.createHotel(hotel2);
        
        // changing hotel name
        hotel1.setName("Dude");
        hotelService.updateHotel(hotel1);
        Hotel temp = hotelService.findHotel(hotel1.getId());
        assertThat("Hotel name not updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("123")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("dude@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel phone number
        hotel1.getContact().setPhone("555");
        hotelService.updateHotel(hotel1);
        temp = hotelService.findHotel(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone not number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("dude@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel email address
        hotel1.getContact().setEmail("ahoj@dude.sk");
        hotelService.updateHotel(hotel1);
        temp = hotelService.findHotel(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email not address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("address")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel address
        hotel1.getContact().setAddress("cowley");
        hotelService.updateHotel(hotel1);
        temp = hotelService.findHotel(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Hotel address not updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("city")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel city
        hotel1.getContact().setCity("london");
        hotelService.updateHotel(hotel1);
        temp = hotelService.findHotel(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Hotel city not updated", temp.getContact().getCity(), is(equalTo("london")));
        assertThat("Hotel country updated", temp.getContact().getCountry(), is(equalTo("country")));
        
        // changing hotel country
        hotel1.getContact().setCountry("UK");
        hotelService.updateHotel(hotel1);
        temp = hotelService.findHotel(hotel1.getId());
        assertThat("Hotel name updated", temp.getName(), is(equalTo("Dude")));
        assertThat("Hotel phone number updated", temp.getContact().getPhone(), is(equalTo("555")));
        assertThat("Hotel email address updated", temp.getContact().getEmail(), is(equalTo("ahoj@dude.sk")));
        assertThat("Hotel address updated", temp.getContact().getAddress(), is(equalTo("cowley")));
        assertThat("Hotel city updated", temp.getContact().getCity(), is(equalTo("london")));
        assertThat("Hotel country not updated", temp.getContact().getCountry(), is(equalTo("UK")));
        
        // checking if hotel2 remained unaffected
        temp = hotelService.findHotel(hotel2.getId());
        assertThat("Saved hotel has wrong name", temp.getName(), is(equalTo("Crowne")));
        assertThat("Saved hotel has wrong phone number", temp.getContact().getPhone(), is(equalTo("333")));
        assertThat("Saved hotel has wrong email address", temp.getContact().getEmail(), is(equalTo("lol@lol.sk")));
        assertThat("Saved hotel has wrong address", temp.getContact().getAddress(), is(equalTo("dress")));
        assertThat("Saved hotel has wrong city", temp.getContact().getCity(), is(equalTo("ty")));
        assertThat("Saved hotel has wrong country", temp.getContact().getCountry(), is(equalTo("untry")));
    }
    
    /**
     * Test of updateHotel, of class HotelServiceImpl, with rooms.
     */
    @Test
    public void testUpdateHotelWithRooms() {
        Contact contact = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");        
        
        Room room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        Hotel hotel = App.DatabaseSampler.buildHotelWithRooms("Hilton", contact, room1);
        hotelService.createHotel(hotel);
        
        // One of hotel's room is null
        Room room2 = null;
        hotel = hotelService.findHotel(hotel.getId());
        hotel.getRooms().add(room2);
        try {
            hotelService.updateHotel(hotel);
            fail("No IllegalArgumentException for null room.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // One of hotel's room has null id
        room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        hotel = App.DatabaseSampler.buildHotelWithRooms("Hilton", contact, room1);
        hotelService.createHotel(hotel);
        room2 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        hotel.getRooms().add(room2);
        try {
            hotelService.updateHotel(hotel);
            fail("Room must exist in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // One of hotel's room belongs to another hotel
        room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        hotel = App.DatabaseSampler.buildHotelWithRooms("Hilton", contact, room1);
        hotelService.createHotel(hotel);
        room2 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        room2.setId(25l);
        hotel.getRooms().add(room2);
        try {
            hotelService.updateHotel(hotel);
            fail("Room cannot belong to another hotel.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        
        // One of hotel's room has null pricePerNight
        room1 = App.DatabaseSampler.buildRoom(BigDecimal.ONE, null);
        hotel = App.DatabaseSampler.buildHotelWithRooms("Hilton", contact, room1);
        hotelService.createHotel(hotel);
        hotel.getRooms().get(0).setPricePerNight(null);
        try {
            hotelService.updateHotel(hotel);
            fail("Room's pricePerNight cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
    }

    /**
     * Test of deleteHotel method, of class HotelServiceImpl.
     */
    @Test
    public void testDeleteHotel() {
        try {
            hotelService.deleteHotel(null);
            fail("Did not throw IllegalArgumentException on deleting null Hotel");
        } catch (Exception e) {
            //OK
        }
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelService.createHotel(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelService.createHotel(hotel2);
        
        assertThat(hotelService.findHotel(hotel1.getId()), is(notNullValue()));
        assertThat(hotelService.findHotel(hotel2.getId()), is(notNullValue()));
        
        hotelService.deleteHotel(hotel1);
        
        assertThat(hotelService.findHotel(hotel1.getId()), is(nullValue()));
        assertThat(hotelService.findHotel(hotel2.getId()), is(notNullValue()));
    }

    /**
     * Test of findAllHotels method, of class HotelServiceImpl.
     */
    @Test
    public void testFindAllHotels() {
        assertTrue(hotelService.findAllHotels().isEmpty());
        
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelService.createHotel(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelService.createHotel(hotel2);
        
        assertThat(hotelService.findAllHotels(), hasItems(hotel1, hotel2));
    }

    /**
     * Test of findHotelsByName method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotelsByName() {
        try {
            hotelService.findHotelsByName(null);
            fail("Did not throw IllegalArgumentException on null");
        } catch (Exception e) {
            //OK
        }
        try {
            hotelService.findHotelsByName("");
            fail("Did not throw IllegalArgumentException on empty string");
        } catch (Exception e) {
            //OK
        }
        
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelService.createHotel(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelService.createHotel(hotel2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "dressdress", "tyty", "untry");
        Hotel hotel3 = App.DatabaseSampler.buildHotel("Crowne", contact3);
        hotelService.createHotel(hotel3);
        
        assertThat(hotelService.findHotelsByName("Hilton"), hasItems(hotel1));
        assertThat(hotelService.findHotelsByName("Crowne"), hasItems(hotel2, hotel3));
        assertTrue(hotelService.findHotelsByName("Dude").isEmpty());
    }

    /**
     * Test of findHotelsByAddress method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotelsByAddress() {
        try {
            hotelService.findHotelsByAddress(null);
            fail("Did not throw IllegalArgumentException on null");
        } catch (Exception e) {
            //OK
        }
        try {
            hotelService.findHotelsByAddress("");
            fail("Did not throw IllegalArgumentException on empty string");
        } catch (Exception e) {
            //OK
        }
        
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelService.createHotel(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "troll", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelService.createHotel(hotel2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "address", "tyty", "untry");
        Hotel hotel3 = App.DatabaseSampler.buildHotel("Jewel", contact3);
        hotelService.createHotel(hotel3);
        
        assertThat(hotelService.findHotelsByAddress("troll"), hasItems(hotel2));
        assertThat(hotelService.findHotelsByAddress("address"), hasItems(hotel1, hotel3));
        assertTrue(hotelService.findHotelsByAddress("Dude").isEmpty());
    }

    /**
     * Test of findHotelsByCity method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotelsByCity() {
        try {
            hotelService.findHotelsByCity(null);
            fail("Did not throw IllegalArgumentException on null");
        } catch (Exception e) {
            //OK
        }
        try {
            hotelService.findHotelsByCity("");
            fail("Did not throw IllegalArgumentException on empty string");
        } catch (Exception e) {
            //OK
        }
        
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "country");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelService.createHotel(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "untry");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelService.createHotel(hotel2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "dressdress", "city", "untry");
        Hotel hotel3 = App.DatabaseSampler.buildHotel("Jewel", contact3);
        hotelService.createHotel(hotel3);
        
        assertThat(hotelService.findHotelsByCity("ty"), hasItems(hotel2));
        assertThat(hotelService.findHotelsByCity("city"), hasItems(hotel1, hotel3));
        assertTrue(hotelService.findHotelsByCity("Dude").isEmpty());
    }

    /**
     * Test of findHotelsByCountry method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotelsByCountry() {
        try {
            hotelService.findHotelsByCountry(null);
            fail("Did not throw IllegalArgumentException on null");
        } catch (Exception e) {
            //OK
        }
        try {
            hotelService.findHotelsByCountry("");
            fail("Did not throw IllegalArgumentException on empty string");
        } catch (Exception e) {
            //OK
        }
        
        Contact contact1 = App.DatabaseSampler.buildContact("123", "dude@dude.sk", "address", "city", "USA");
        Hotel hotel1 = App.DatabaseSampler.buildHotel("Hilton", contact1);
        hotelService.createHotel(hotel1);
        
        Contact contact2 = App.DatabaseSampler.buildContact("333", "lol@lol.sk", "dress", "ty", "UK");
        Hotel hotel2 = App.DatabaseSampler.buildHotel("Crowne", contact2);
        hotelService.createHotel(hotel2);
        
        Contact contact3 = App.DatabaseSampler.buildContact("321", "lolx@lolx.sk", "dressdress", "tyty", "USA");
        Hotel hotel3 = App.DatabaseSampler.buildHotel("Crowne", contact3);
        hotelService.createHotel(hotel3);
        
        assertThat(hotelService.findHotelsByCountry("UK"), hasItems(hotel2));
        assertThat(hotelService.findHotelsByCountry("USA"), hasItems(hotel1, hotel3));
        assertTrue(hotelService.findHotelsByCountry("Dude").isEmpty());
    }
}
