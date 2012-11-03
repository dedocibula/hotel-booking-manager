package cz.fi.muni.pa165.test.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.App;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.impl.ClientServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanager.service.impl.HotelServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanager.service.impl.ReservationServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanager.service.impl.RoomServiceImpl;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ClientService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.HotelService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.RoomService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ContactTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.RoomTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.Validator;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
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

    private HotelService hotelService;
    private ClientService clientService;
    private ReservationService reservationService;


    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("testApplicationContext.xml");

        hotelService = context.getBean(HotelService.class);
        clientService = context.getBean(ClientService.class);
        reservationService = context.getBean(ReservationService.class);

        Validator validator = context.getBean("validator", org.springframework.validation.beanvalidation.LocalValidatorFactoryBean.class);
        roomDAO = Mockito.mock(RoomDAO.class);
        mapper = context.getBean(DozerBeanMapper.class);
        ReflectionTestUtils.setField(roomService, "roomDAO", roomDAO);
        ReflectionTestUtils.setField(roomService, "mapper", mapper);
        ReflectionTestUtils.setField(roomService, "validator", validator);
    }

    @After
    public void tearDown() {
        roomService = null;
        hotelService = null;
        clientService = null;
        reservationService = null;
        mapper = null;
    }


    /**
     * Test of createRoomRoom method, of class RoomServiceImpl.
     */
    @Test
    public void testCreateAndGetRoom() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "some@email.asdf", "address", "city", "country");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("mynewhotel", contact);
        hotelService.createHotel(hotel);
        RoomTO room = App.DatabaseSampler.buildRoomTO(BigDecimal.ONE, hotel);

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

        hotelService.createHotel(hotel);

        RoomTO room = App.DatabaseSampler.buildRoomTO(BigDecimal.valueOf(333.00), hotel);

        room.setId(1L);

        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Room room = (Room) args[0];
                return room;
            }
        }).when(roomDAO).update(mapper.map(room, Room.class));
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
        hotelService.createHotel(hotel);

        RoomTO room = App.DatabaseSampler.buildRoomTO(BigDecimal.ONE, hotel);

        room.setId(1L);

        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Room room = (Room) args[0];
                return room;
            }
        }).when(roomDAO).delete(mapper.map(room, Room.class));
        roomService.deleteRoom(room);
        Mockito.verify(roomDAO).delete(mapper.map(room, Room.class));
    }

    /**
     * Test of findAllRooms method, of class RoomServiceImpl.
     */
    @Test
    public void testFindAllRooms() {
       List<RoomTO> rooms = roomService.findAllRooms();
       Mockito.verify(roomDAO).findAllRooms();
    }

    /**
     * Test of findRoomsByName method, of class RoomServiceImpl.
     */
    @Test
    public void testFindVacantRooms() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "some@email.asdf", "address", "city", "country");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("mynewhotel", contact);
        hotelService.createHotel(hotel);

        List<RoomTO> rooms = roomService.findVacantRooms(new Date(150,1,1), new Date(150,1,1), hotel);
        Mockito.verify(roomDAO).findAllVacantRooms(new Date(150,1,1), new Date(150,1,1));
    }

    @Test
    public void testFindRoomsByHotel() {
        ContactTO contact = App.DatabaseSampler.buildContactTO("123", "some@email.asdf", "address", "city", "country");
        HotelTO hotel = App.DatabaseSampler.buildHotelTO("mynewhotel", contact);
        hotelService.createHotel(hotel);
        List<RoomTO> rooms = roomService.findRoomsByHotel(hotel);
        //TODO?!
    }
}