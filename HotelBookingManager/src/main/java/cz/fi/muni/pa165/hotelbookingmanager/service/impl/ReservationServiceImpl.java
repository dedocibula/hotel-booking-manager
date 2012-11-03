package cz.fi.muni.pa165.hotelbookingmanager.service.impl;

import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.ReservationDAO;
import cz.fi.muni.pa165.hotelbookingmanager.dao.interfaces.RoomDAO;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Client;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Hotel;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Reservation;
import cz.fi.muni.pa165.hotelbookingmanager.entities.Room;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
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
    
    @Override
    @Transactional
    public void createReservation(Reservation reservation) {
        validateReservation(reservation);
        if (reservation.getId() != null)
            throw new IllegalArgumentException("Reservation cannot have manually assigned id");
        
        reservationDAO.create(reservation);
    }

    @Override
    @Transactional
    public void deleteReservation(Reservation reservation) {
        validateReservationIncludingId(reservation);
        reservationDAO.delete(reservation);
    }

    @Override
    @Transactional
    public void updateReservation(Reservation reservation) {
        validateReservationIncludingId(reservation);
        reservationDAO.update(reservation);
    }

    @Override
    @Transactional
    public Reservation getReservation(Long id) {
        if (id == null)
            throw new IllegalArgumentException("ID cannot be null.");
        
        return reservationDAO.get(id);
    }

    @Override
    @Transactional
    public List<Reservation> findAllReservations() {
        return reservationDAO.findAllReservations();
    }

    @Override
    @Transactional
    public List<Reservation> findReservationsByClient(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Client cannot be null.");
        if (client.getId() == null)
            throw new IllegalArgumentException("Client's id cannot be null.");
        
        return reservationDAO.findReservationsByClient(client);
    }

    @Override
    @Transactional
    public List<Reservation> findReservationsByDate(Date from, Date to) {
        if (from == null)
            throw new IllegalArgumentException("From date cannot be null.");
        if (to == null)
            throw new IllegalArgumentException("To date cannot be null.");
        if (from.after(to))
            throw new IllegalArgumentException("From date must be after to date");
        
        return reservationDAO.findReservationsByDate(from, to);
    }

    @Override
    @Transactional
    public List<Reservation> findReservationsByDate(Date from, Date to, Hotel hotel) {
        if (from == null)
            throw new IllegalArgumentException("From date cannot be null.");
        if (to == null)
            throw new IllegalArgumentException("To date cannot be null.");
        if (from.after(to))
            throw new IllegalArgumentException("From date must be after to date");
        if (hotel == null)
            throw new IllegalArgumentException("Hotel cannot be null.");
        if (hotel.getId() == null)
            throw new IllegalArgumentException("Hotel cannot be null.");
        
        return reservationDAO.findReservationsByDate(from, to, hotel);
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
