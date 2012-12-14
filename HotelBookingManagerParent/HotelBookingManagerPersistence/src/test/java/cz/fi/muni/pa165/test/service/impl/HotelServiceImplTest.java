package cz.fi.muni.pa165.test.service.impl;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.HotelService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.service.RoomService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ContactTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomType;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.App;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.HotelDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl.HotelServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Validator;
import org.dozer.Mapper;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Andrej Galád
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class HotelServiceImplTest {

    private HotelDAO hotelDAO;
    private HotelService hotelService = new HotelServiceImpl();
    private Mapper mapper;
    private RoomService roomService;

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");

        Validator validator = context.getBean("validator", org.springframework.validation.beanvalidation.LocalValidatorFactoryBean.class);
        mapper = context.getBean("mapper", org.dozer.DozerBeanMapper.class);
        hotelDAO = Mockito.mock(HotelDAO.class);

        roomService = context.getBean(RoomService.class);

        ReflectionTestUtils.setField(hotelService, "hotelDAO", hotelDAO);
        ReflectionTestUtils.setField(hotelService, "mapper", mapper);
        ReflectionTestUtils.setField(hotelService, "validator", validator);
    }

    @After
    public void tearDown() {
        hotelService = null;
        mapper = null;
        hotelDAO = null;
    }

    /**
     * Test of createHotel method, of class HotelServiceImpl.
     */
    @Test
    public void testCreateHotelWithoutRooms() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        HotelTO hotelTO = App.DatabaseSampler.buildHotelTO("Hilton", contact);
        hotelService.createHotel(hotelTO);
        Mockito.verify(hotelDAO).create(mapper.map(hotelTO, Hotel.class));
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
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("Hilton", contact);
        hotel.setId(5l);
        try {
            hotelService.createHotel(hotel);
            fail("Cannot change id for existing hotel.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create hotel with null contact attribute
        hotel = App.DatabaseSampler.buildHotelTO("Hilton", null);
        try {
            hotelService.createHotel(hotel);
            fail("Contact cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create hotel with null name attribute
        hotel = App.DatabaseSampler.buildHotelTO(null, contact);
        try {
            hotelService.createHotel(hotel);
            fail("Name cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create hotel with too long name
        hotel = App.DatabaseSampler.buildHotelTO("Trololololololololololololololololololololololo", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Name cannot exceed 30 characters.");
        } catch (IllegalArgumentException e) {
            System.out.println("");
        }

        // create hotel with contact missing phone number
        contact = App.DatabaseSampler.buildContactTO(null, "dude@dude.sk", "address", "city", "country");
        hotel = App.DatabaseSampler.buildHotelTO("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Phone number cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create hotel with contact missing email address
        contact = App.DatabaseSampler.buildContactTO("123", null, "address", "city", "country");
        hotel = App.DatabaseSampler.buildHotelTO("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Email address cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create hotel with contact missing address
        contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", null, "city", "country");
        hotel = App.DatabaseSampler.buildHotelTO("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Address cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create hotel with contact missing city
        contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", null, "country");
        hotel = App.DatabaseSampler.buildHotelTO("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("City cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // create hotel with contact missing country
        contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", null);
        hotel = App.DatabaseSampler.buildHotelTO("Hilton", contact);
        try {
            hotelService.createHotel(hotel);
            fail("Country cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        Mockito.verifyZeroInteractions(hotelDAO);
    }

    /**
     * Test of createHotel method, of class HotelServiceImpl, with rooms.
     */
    @Test
    public void testCreateHotelWithRooms() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        RoomTO room1 = App.DatabaseSampler.buildRoomTO(RoomType.Family, BigDecimal.ONE, null);
        RoomTO room2 = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.TEN, null);
        HotelTO hotelTO = App.DatabaseSampler.buildHotelTOWithRooms("Hilton", contact, room1, room2);

        hotelService.createHotel(hotelTO);
        Mockito.verify(hotelDAO).create(mapper.map(hotelTO, Hotel.class));
    }

    /**
     * Test of createHotel method, of class HotelServiceImpl, with rooms having wrong attributes.
     */
    @Test
    public void testCreateHotelWithRoomsWithWrongAttributes() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");

        // One of hotel's room is null
        RoomTO room1 = App.DatabaseSampler.buildRoomTO(RoomType.Double, BigDecimal.ONE, null);
        RoomTO room2 = null;
        HotelTO hotel = App.DatabaseSampler.buildHotelTOWithRooms("Hilton", contact, room1, room2);
        try {
            hotelService.createHotel(hotel);
            fail("No IllegalArgumentException for null room.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // One of hotel's room has assigned id
        room2 = App.DatabaseSampler.buildRoomTO(RoomType.Royal, BigDecimal.ONE, null);
        room2.setId(25l);
        hotel = App.DatabaseSampler.buildHotelTOWithRooms("Hilton", contact, room1, room2);
        try {
            hotelService.createHotel(hotel);
            fail("Room's id cannot be setted manually or room cannot be already in database.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // One of hotel's room has null pricePerNight
        room2 = App.DatabaseSampler.buildRoomTO(RoomType.Single, null, null);
        hotel = App.DatabaseSampler.buildHotelTOWithRooms("Hilton", contact, room1, room2);
        try {
            hotelService.createHotel(hotel);
            fail("Room's pricePerNight cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        Mockito.verifyZeroInteractions(hotelDAO);
    }

    /**
     * Test of findHotel method, of class HotelServiceImpl.
     */
    @Test
    public void testFindHotel() {
        try {
            hotelService.findHotel(null);
            fail("No IllegalArgumentException for null id");
        } catch (IllegalArgumentException e) {
            //Ok
        }
        Mockito.verifyZeroInteractions(hotelDAO);

        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("Hilton", contact);
        hotelService.createHotel(hotel);
        hotel.setId(1L);

        Mockito.when(hotelDAO.get(hotel.getId())).thenReturn(mapper.map(hotel, Hotel.class));
        HotelTO testHotel1 = hotelService.findHotel(hotel.getId());
        Mockito.verify(hotelDAO).get(hotel.getId());

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
        ContactTO contact1 = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        HotelTO hotel1 = App.DatabaseSampler.buildHotelTO("Hilton", contact1);
        try {
            hotelService.updateHotel(hotel1);
            fail("Cannot update a hotel not present in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("Hilton", contact1);
        hotel.setId(30l);
        try {
            hotelService.updateHotel(hotel1);
            fail("Cannot update a hotel not present in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        Mockito.verifyZeroInteractions(hotelDAO);
        hotelService.createHotel(hotel1);

        ContactTO contact2 = App.DatabaseSampler.buildContactTO("333", "lol@lol.sk", "dress", "ty", "untry");
        HotelTO hotel2 = App.DatabaseSampler.buildHotelTO("Crowne", contact2);
        hotelService.createHotel(hotel2);

        hotel2.setId(1L);

        Mockito.when(hotelDAO.get(hotel2.getId())).thenReturn(mapper.map(hotel2, Hotel.class));
        hotelService.updateHotel(hotel2);
        Mockito.verify(hotelDAO).update(mapper.map(hotel2, Hotel.class));
    }

    /**
     * Test of updateHotel, of class HotelServiceImpl, with rooms.
     */
    @Test
    public void testUpdateHotelWithRooms() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");

        RoomTO room1 = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, null);
        HotelTO hotel = App.DatabaseSampler.buildHotelTOWithRooms("Hilton", contact, room1);

        hotel.setId(1L);



        // One of hotel's room is null
        RoomTO room2 = null;
        hotel.getRooms().add(room2);
        try {
            hotelService.updateHotel(hotel);
            fail("No IllegalArgumentException for null room.");
        } catch (IllegalArgumentException e) {
            //OK
        }


        // One of hotel's room has null id
        room1 = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, null);
        hotel = App.DatabaseSampler.buildHotelTOWithRooms("Hilton", contact, room1);
        hotel.setId(1L);
        room2 = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, null);
        hotel.getRooms().add(room2);
        try {
            hotelService.updateHotel(hotel);
            fail("Room must exist in a database.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // One of hotel's room belongs to another hotel
        room1 = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, null);
        hotel = App.DatabaseSampler.buildHotelTOWithRooms("Hilton", contact, room1);
        hotel.setId(1L);
        room2 = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, null);
        room2.setId(25l);
        hotel.getRooms().add(room2);
        try {
            hotelService.updateHotel(hotel);
            fail("Room cannot belong to another hotel.");
        } catch (IllegalArgumentException e) {
            //OK
        }

        // One of hotel's room has null pricePerNight
        room1 = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, null);
        hotel = App.DatabaseSampler.buildHotelTOWithRooms("Hilton", contact, room1);
        hotel.setId(1L);
        hotel.getRooms().get(0).setPricePerNight(null);
        try {
            hotelService.updateHotel(hotel);
            fail("Room's pricePerNight cannot be null.");
        } catch (IllegalArgumentException e) {
            //OK
        }
        Mockito.verify(hotelDAO, Mockito.times(4)).get(1L);
    }

    /**
     * Test of deleteHotel method, of class HotelServiceImpl.
     */
    @Test
    public void testDeleteHotel() {
        try {
            hotelService.deleteHotel(null);
            fail("Did not throw IllegalArgumentException on deleting null HotelTO");
        } catch (Exception e) {
            //OK
        }
        Mockito.verifyZeroInteractions(hotelDAO);
        ContactTO contact1 = App.DatabaseSampler.buildContactTO("123", "dude@dude.sk", "address", "city", "country");
        HotelTO hotelTO = App.DatabaseSampler.buildHotelTO("Hilton", contact1);
        hotelService.createHotel(hotelTO);
        hotelTO.setId(1L);

        Mockito.when(hotelDAO.get(hotelTO.getId())).thenReturn(mapper.map(hotelTO, Hotel.class));
        hotelService.deleteHotel(hotelTO);


    }

    /**
     * Test of findAllHotels method, of class HotelServiceImpl.
     */
    @Test
    public void testFindAllHotels() {
        List<HotelTO> hotels = hotelService.findAllHotels();
        Mockito.verify(hotelDAO).findAll();
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
        Mockito.verifyZeroInteractions(hotelDAO);

        List<HotelTO> hotels = hotelService.findHotelsByName("Dude");
        Mockito.verify(hotelDAO).findHotelsByName("Dude");
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
        Mockito.verifyZeroInteractions(hotelDAO);

        List<HotelTO> hotels = hotelService.findHotelsByAddress("Dude");
        Mockito.verify(hotelDAO).findHotelsByAddress("Dude");
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
        Mockito.verifyZeroInteractions(hotelDAO);

        List<HotelTO> hotels = hotelService.findHotelsByCity("Dude");
        Mockito.verify(hotelDAO).findHotelsByCity("Dude");
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
        Mockito.verifyZeroInteractions(hotelDAO);

        List<HotelTO> hotels = hotelService.findHotelsByCountry("Dude");
        Mockito.verify(hotelDAO).findHotelsByCountry("Dude");
    }
}
