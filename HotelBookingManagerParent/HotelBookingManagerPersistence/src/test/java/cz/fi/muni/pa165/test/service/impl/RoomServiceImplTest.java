package cz.fi.muni.pa165.test.service.impl;


import cz.fi.muni.pa165.hotelbookingmanagerapi.service.RoomService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ContactTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RoomType;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.App;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl.RoomServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Validator;
import org.dozer.Mapper;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
@TransactionConfiguration(defaultRollback = true)
@Transactional
@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

    private RoomDAO roomDAO;
    private RoomService roomService = new RoomServiceImpl();
    private Mapper mapper;


    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");


        Validator validator = context.getBean("validator", org.springframework.validation.beanvalidation.LocalValidatorFactoryBean.class);
        roomDAO = Mockito.mock(RoomDAO.class);
        mapper = context.getBean("mapper", org.dozer.DozerBeanMapper.class);
        ReflectionTestUtils.setField(roomService, "roomDAO", roomDAO);
        ReflectionTestUtils.setField(roomService, "mapper", mapper);
        ReflectionTestUtils.setField(roomService, "validator", validator);
    }

    @After
    public void tearDown() {
        roomService = null;
        mapper = null;
        roomDAO = null;
    }


    /**
     * Test of createRoomRoom method, of class RoomServiceImpl.
     */
    @Test
    public void testCreateAndGetRoom() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "some@email.asdf", "address", "city", "country");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("mynewhotel", contact);
        hotel.setId(1L);

        RoomTO room = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, hotel);

        roomService.createRoom(room);
        Mockito.verify(roomDAO, Mockito.times(1)).create(mapper.map(room, Room.class));

        //Create a null Room
        try {
            Mockito.doThrow(new IllegalArgumentException()).when(roomDAO).create(null);
            roomService.createRoom(null);
            fail("No IllegalArgumentException thrown while creating a null Room.");
        } catch (IllegalArgumentException iae) {
            //Works as intended
        }

        //Get a null Room
        try {
            Mockito.doThrow(new IllegalArgumentException()).when(roomDAO).get(null);
            roomService.getRoom(null);
            fail("No IllegalArgumentException thrown while removing a null Room.");
        } catch (IllegalArgumentException iae) {
            //Works as intended
        }

        room.setHotel(null);
        //Create a Room with null Hotel
        try {
            Mockito.doThrow(new IllegalArgumentException()).when(roomDAO).create(mapper.map(room, Room.class));
            roomService.createRoom(room);
            fail("Room with null Hotel was created.");
        } catch (IllegalArgumentException iae) {
            //All works well
        }
    }

    /**
     * Test of updateRoom method, of class RoomServiceImpl.
     */
    @Test
    public void testUpdateRoom() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("12345", "something@random.wtf", "streetz", "town", "land");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("MyHotel", contact);

        hotel.setId(1L);

        RoomTO room = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.valueOf(333.00), hotel);

        room.setId(1L);

        Mockito.when(roomDAO.get(room.getId())).thenReturn(mapper.map(room, Room.class));
        roomService.updateRoom(room);
        Mockito.verify(roomDAO).update(mapper.map(room, Room.class));

    }

    /**
     * Test of deleteRoom method, of class RoomServiceImpl.
     */
    @Test
    public void testDeleteRoom() {
        //Delete a null Room
        try {
            roomService.deleteRoom(null);
            fail("No IllegalArgumentException thrown while removing null Room");
        } catch (IllegalArgumentException iae) {
            //All works as intended
        }

        ContactTO contact = App.DatabaseSampler.buildContactTO("12345", "something@random.wtf", "streetz", "town", "land");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("MyHotel", contact);
        hotel.setId(1L);

        RoomTO room = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, hotel);

        room.setId(1L);


        roomService.deleteRoom(room);
        Mockito.verify(roomDAO).delete(mapper.map(room, Room.class));
    }

    /**
     * Test of findRoomsByName method, of class RoomServiceImpl.
     */
    @Test
    public void testFindVacantRooms() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "some@email.asdf", "address", "city", "country");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("mynewhotel", contact);
        hotel.setId(1L);

        List<RoomTO> rooms = roomService.findVacantRooms(new Date(150,1,1), new Date(150,1,1), hotel);
        Mockito.verify(roomDAO).findAllVacantRooms(new Date(150,1,1), new Date(150,1,1));
    }

    @Test
    public void testFindRoomsByHotel() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "some@email.asdf", "address", "city", "country");
        HotelTO hotelTO = App.DatabaseSampler.buildHotelTO("mynewhotel", contact);
        hotelTO.setId(1L);
        RoomTO roomTO = App.DatabaseSampler.buildRoomTO(RoomType.Single, BigDecimal.ONE, hotelTO);
        List<RoomTO> roomsTO = new ArrayList<>();
        roomsTO.add(roomTO);
        hotelTO.setRooms(roomsTO);
        List<RoomTO> rooms = roomService.findRoomsByHotel(hotelTO);
        Mockito.verifyZeroInteractions(roomDAO);
    }
}