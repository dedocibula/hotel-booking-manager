package cz.fi.muni.pa165.hotelbookingmanager.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ReservationDAO;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ReservationTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Marian Rusnak
 */
@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationDAO reservationDAO;
    
    @Autowired
    private ClientDAO clientDAO;
    
    @Autowired
    private RoomDAO roomDAO;
    
    @Autowired
    private Validator validator;
    
    @Autowired
    private Mapper mapper;

    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
    
    @Override
    @Transactional
    public void createReservation(ReservationTO reservationTO) {
        if (reservationTO == null)
            throw new IllegalArgumentException();
        Reservation reservation = mapper.map(reservationTO, Reservation.class);
        validateReservation(reservation);
        if (reservation.getId() != null)
            throw new IllegalArgumentException("Reservation cannot have manually assigned id");
        
        reservationDAO.create(reservation);
    }

    @Override
    @Transactional
    public void deleteReservation(ReservationTO reservationTO) {
        Reservation reservation = mapper.map(reservationTO, Reservation.class);
        validateReservationIncludingId(reservation);
        reservationDAO.delete(reservation);
    }

    @Override
    @Transactional
    public void updateReservation(ReservationTO reservationTO) {
        Reservation reservation = mapper.map(reservationTO, Reservation.class);
        validateReservationIncludingId(reservation);
        reservationDAO.update(reservation);
    }

    @Override
    public ReservationTO getReservation(Long id) {
        if (id == null)
            throw new IllegalArgumentException("ID cannot be null.");
        
        Reservation reservation = reservationDAO.get(id);
        return reservation != null ? mapper.map(reservation, ReservationTO.class) : null;
    }

    @Override
    public List<ReservationTO> findAllReservations() {
        List<ReservationTO> reservationTOs = new ArrayList<>();
        for (Reservation reservation : reservationDAO.findAllReservations()) {
            reservationTOs.add(mapper.map(reservation, ReservationTO.class));
        }
        return reservationTOs;
    }

    @Override
    public List<ReservationTO> findReservationsByClient(ClientTO clientTO) {
        if (clientTO == null)
            throw new IllegalArgumentException("Client cannot be null.");
        if (clientTO.getId() == null)
            throw new IllegalArgumentException("Client's id cannot be null.");
        Client client = mapper.map(clientTO, Client.class);
        List<ReservationTO> reservationTOs = new ArrayList<>();
        for (Reservation reservation : reservationDAO.findReservationsByClient(client)) {
            reservationTOs.add(mapper.map(reservation, ReservationTO.class));
        }
        return reservationTOs;
    }

    @Override
    public List<ReservationTO> findReservationsByDate(Date from, Date to) {
        if (from == null)
            throw new IllegalArgumentException("From date cannot be null.");
        if (to == null)
            throw new IllegalArgumentException("To date cannot be null.");
        if (from.after(to))
            throw new IllegalArgumentException("From date must be after to date");
        
        List<ReservationTO> reservationTOs = new ArrayList<>();
        for (Reservation reservation : reservationDAO.findReservationsByDate(from, to)) {
            reservationTOs.add(mapper.map(reservation, ReservationTO.class));
        }
        return reservationTOs;
    }

    @Override
    public List<ReservationTO> findReservationsByDate(Date from, Date to, HotelTO hotelTO) {
        if (from == null)
            throw new IllegalArgumentException("From date cannot be null.");
        if (to == null)
            throw new IllegalArgumentException("To date cannot be null.");
        if (from.after(to))
            throw new IllegalArgumentException("From date must be after to date");
        if (hotelTO == null)
            throw new IllegalArgumentException("Hotel cannot be null.");
        if (hotelTO.getId() == null)
            throw new IllegalArgumentException("Hotel cannot be null.");
        Hotel hotel = mapper.map(hotelTO, Hotel.class);
        List<ReservationTO> reservationTOs = new ArrayList<>();
        for (Reservation reservation : reservationDAO.findReservationsByDate(from, to, hotel)) {
            reservationTOs.add(mapper.map(reservation, ReservationTO.class));
        }
        return reservationTOs;
    }

    private void validateReservation(Reservation reservation) {
        if (reservation == null)
            throw new IllegalArgumentException("Reservation cannot be null.");
        validateReservationAttrributes(reservation);
        validateReservationDate(reservation);
        validateReservationClient(reservation);
        validateReservationRoom(reservation);
    }
    
    private void validateReservationIncludingId(Reservation reservation) {
        validateReservation(reservation);
        if (reservation.getId() == null)
            throw new IllegalArgumentException("Reservation must have id already set.");
    }
    
    private void validateReservationAttrributes(Reservation reservation) throws IllegalArgumentException {
        Set<ConstraintViolation<Reservation>> validationResults = validator.validate(reservation);
        if (!validationResults.isEmpty()) {
            throw new IllegalArgumentException("Reservation parameters are invalid: " + 
                    validationResults.iterator().next().getMessage());
        }
    }
    
    private void validateReservationDate(Reservation reservation) {
        Date fromDate = reservation.getFromDate();
        Date toDate = reservation.getToDate();
        
        if (fromDate.after(toDate))
            throw new IllegalArgumentException("From date must be after to date");
    }
    
    private void validateReservationClient(Reservation reservation) {
        Long clientId = reservation.getClient().getId();
        if (clientId == null)
            throw new IllegalArgumentException("Client id cannot be null and client must exists.");
        
        Client client = clientDAO.get(reservation.getClient().getId());
        if (client == null)
            throw new IllegalArgumentException("Client must exists.");
    }
    
    private void validateReservationRoom(Reservation reservation) {
        Long roomId = reservation.getRoom().getId();
        if (roomId == null)
            throw new IllegalArgumentException("Room id cannot be null and room must exists.");
        
        Room room = roomDAO.get(roomId);
        if (room == null)
            throw new IllegalArgumentException("Room must exists.");
        //if (!roomDAO.isVacant(room, reservation.getFromDate(), reservation.getToDate()))
        //    throw new IllegalArgumentException("Room must be vacant.");
    }
}
