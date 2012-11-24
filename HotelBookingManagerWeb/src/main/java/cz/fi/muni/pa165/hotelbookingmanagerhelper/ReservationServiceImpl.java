/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanagerhelper;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.HotelTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ReservationTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Andrej Galád
 */
public class ReservationServiceImpl implements ReservationService {

    @Override
    public void createReservation(ReservationTO reservationTO) {
        reservationTO.setId(DatabaseSimulator.getReservationId());
        DatabaseSimulator.reservations.add(reservationTO);
    }

    @Override
    public void deleteReservation(ReservationTO reservationTO) {
        DatabaseSimulator.reservations.remove(reservationTO);
    }

    @Override
    public void updateReservation(ReservationTO reservationTO) {
        ReservationTO reservation = getReservation(reservationTO.getId());
        DatabaseSimulator.reservations.remove(reservation);
        DatabaseSimulator.reservations.add(reservationTO);
    }

    @Override
    public ReservationTO getReservation(Long id) {
        for (ReservationTO reservation : findAllReservations()) {
            if (reservation.getId() == id)
                return reservation;
        }
        return null;
    }

    @Override
    public List<ReservationTO> findAllReservations() {
        return DatabaseSimulator.reservations;
    }

    @Override
    public List<ReservationTO> findReservationsByClient(ClientTO clientTO) {
        List<ReservationTO> temp = new ArrayList<>();
        for (ReservationTO reservationTO : findAllReservations()) {
            if (reservationTO.getClient().equals(clientTO))
                temp.add(reservationTO);
        }
        return temp;
    }

    @Override
    public List<ReservationTO> findReservationsByDate(Date from, Date to) {
        List<ReservationTO> temp = new ArrayList<>();
        for (ReservationTO reservationTO : findAllReservations()) {
            if ((reservationTO.getFromDate().before(from) ||
                reservationTO.getFromDate().equals(from)) && 
                (reservationTO.getToDate().after(to) ||
                reservationTO.getToDate().equals(to)))
                temp.add(reservationTO);
        }
        return temp;
    }

    @Override
    public List<ReservationTO> findReservationsByDate(Date from, Date to, HotelTO hotelTO) {
        List<ReservationTO> temp = new ArrayList<>();
        for (ReservationTO reservationTO : findAllReservations()) {
            if ((reservationTO.getFromDate().before(from) ||
                reservationTO.getFromDate().equals(from)) && 
                (reservationTO.getToDate().after(to) ||
                reservationTO.getToDate().equals(to)) &&
                reservationTO.getRoom().getHotel().equals(hotelTO))
                temp.add(reservationTO);
        }
        return temp;
    }
    
}
