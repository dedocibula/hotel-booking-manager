package cz.muni.fi.pa165.jsonrpc.hotelbookingmanagerconsoleclient;

import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ContactTO;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static ClientRESTManager clientRESTManager;
    private static HotelRESTManager hotelRESTManager;
    
    public static void main( String[] args ) {
        clientRESTManager = new ClientRESTManager();
        hotelRESTManager = new HotelRESTManager();
        
        TestClientRESTManager();
        TestHotelRESTManager();
    }
    
    private static void TestHotelRESTManager() {
        TestFindAllHotels();
        TestFindHotelsByName();
        TestFindHotelsByAddress();
        TestFindHotelsByCity();
        TestFindHotelsByCountry();
        TestFindHotel();
        TestCreateHotel();
        TestUpdateHotel();
        TestDeleteHotel();
    }

    private static void TestClientRESTManager() {
        TestFindAllClients();
        TestFindClientsByName();
        TestFindClient();
        TestCreateClient();
        TestUpdateClient();
        TestDeleteClient();
    }
    
    private static void TestDeleteHotel() {
        List<HotelTO> hotels = hotelRESTManager.findAllHotels();
        HotelTO hotel = hotels.get(hotels.size() - 1);
        hotelRESTManager.deleteHotel(hotel);
        System.out.println("Hotel deleted successfully");
        DumpResults(hotelRESTManager.findAllHotels());
    }
    
    private static void TestUpdateHotel() {
        List<HotelTO> hotels = hotelRESTManager.findAllHotels();
        HotelTO hotel = hotels.get(hotels.size() - 1);
        hotel.setName("Brutality");
        hotelRESTManager.updateHotel(hotel);
        System.out.println("Hotel updated successfully");
        DumpResults(hotelRESTManager.findAllHotels());
    }
    
    private static void TestCreateHotel() {
        HotelTO hotel = buildHotel();
        hotelRESTManager.createHotel(hotel);
        System.out.println("Hotel created successfully");
        DumpResults(hotelRESTManager.findAllHotels());
    }
    
    private static void TestFindHotel() {
        HotelTO hotel = hotelRESTManager.findHotel(1l);
        System.out.println(hotel);
        System.out.println("");
    }

    private static void TestFindHotelsByName() {
        List<HotelTO> hotels = hotelRESTManager.findHotelsByName("Hi");
        DumpResults(hotels);
    }
    
    private static void TestFindHotelsByAddress() {
        List<HotelTO> hotels = hotelRESTManager.findHotelsByAddress("Co");
        DumpResults(hotels);
    }
    
    private static void TestFindHotelsByCity() {
        List<HotelTO> hotels = hotelRESTManager.findHotelsByCity("Ox");
        DumpResults(hotels);
    }
    
    private static void TestFindHotelsByCountry() {
        List<HotelTO> hotels = hotelRESTManager.findHotelsByCountry("UK");
        DumpResults(hotels);
    }

    private static void TestFindAllHotels() {
        List<HotelTO> hotels = hotelRESTManager.findAllHotels();
        DumpResults(hotels);
    }
    
    private static void TestDeleteClient() {
        List<ClientTO> clients = clientRESTManager.findAllClients();
        ClientTO client = clients.get(clients.size() - 1);
        clientRESTManager.deleteClient(client);
        System.out.println("Client deleted successfully");
        DumpResults(clientRESTManager.findAllClients());
    }
    
    private static void TestUpdateClient() {
        List<ClientTO> clients = clientRESTManager.findAllClients();
        ClientTO client = clients.get(clients.size() - 1);
        client.setLastName("Creeper");
        clientRESTManager.updateClient(client);
        System.out.println("Client updated successfully");
        DumpResults(clientRESTManager.findAllClients());
    }
    
    private static void TestCreateClient() {
        ClientTO client = buildClient();
        clientRESTManager.createClient(client);
        System.out.println("Client created successfully");
        DumpResults(clientRESTManager.findAllClients());
    }
    
    private static void TestFindClient() {
        ClientTO client = clientRESTManager.findClient(1l);
        System.out.println(client);
        System.out.println("");
    }

    private static void TestFindClientsByName() {
        List<ClientTO> clients = clientRESTManager.findClientsByName("Pe");
        DumpResults(clients);
    }

    private static void TestFindAllClients() {
        List<ClientTO> clients = clientRESTManager.findAllClients();
        DumpResults(clients);
    }
    
    private static void DumpResults(List<?> results) {
        for (Object result : results) {
            System.out.println(result);
        }
        System.out.println("");
    }
    
    private static ContactTO buildContact() {
        ContactTO contact = new ContactTO();
        contact.setAddress("Kitchenetter");
        contact.setCountry("USA");
        contact.setCity("New York");
        contact.setEmail("h@h.sk");
        contact.setPhone("999");
        return contact;
    }
    
    private static ClientTO buildClient() {
        ClientTO client = new ClientTO();
        client.setFirstName("Jojo");
        client.setLastName("Masterchief");
        client.setContact(buildContact());
        return client;
    }
    
    private static HotelTO buildHotel() {
        HotelTO hotel = new HotelTO();
        hotel.setName("Tropicana");
        hotel.setContact(buildContact());
        return hotel;
    }
}
