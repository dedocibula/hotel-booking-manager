package cz.fi.muni.pa165.hotelbookingmanagerhelper;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ClientService;
import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanager.transferobjects.ReservationTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrej Galád
 */
public class ClientServiceImpl implements ClientService {

    private ReservationService reservationService = new ReservationServiceImpl();
    
    @Override
    public void createClient(ClientTO client) {
        client.setId(DatabaseSimulator.getClientId());
        DatabaseSimulator.clients.add(client);
    }

    @Override
    public ClientTO findClient(Long id) {
        for (ClientTO client : findAllClients()) {
            if (client.getId() == id)
                return client;
        }
        return null;
    }

    @Override
    public void updateClient(ClientTO client) {
        ClientTO clientTO = findClient(client.getId());
        DatabaseSimulator.clients.remove(clientTO);
        DatabaseSimulator.clients.add(client);
    }

    @Override
    public void deleteClient(ClientTO client) {
        for (ReservationTO reservation : reservationService.findAllReservations())
            if (reservation.getClient().equals(client))
                reservationService.deleteReservation(reservation);
        DatabaseSimulator.clients.remove(client);
    }

    @Override
    public List<ClientTO> findAllClients() {
        return DatabaseSimulator.clients;
    }

    @Override
    public List<ClientTO> findClientsByName(String name) {
        List<ClientTO> temp = new ArrayList<>();
        for (ClientTO clientTO : findAllClients()) {
            String fullName = clientTO.getFirstName() + " " + clientTO.getLastName() + " " + clientTO.getFirstName();
            if (fullName.contains(name))
                temp.add(clientTO);
        }
        return temp;
    }
    
}
