package cz.fi.muni.pa165.hotelbookingmanagerdesktop.tablemodels;

import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.ClientTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Thanh Dang Hoang Minh
 */
public class ClientTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private List<ClientTO> clients = new ArrayList<>();

    @Override
    public int getRowCount() {
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }
    //ID fname lname address city country tel. mail
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "First name";
            case 2:
                return "Last name";
            case 3:
                return "Address";
            case 4:
                return "City";
            case 5:
                return "Country";
            case 6:
                return "Telephone";
            case 7:
                return "Email";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClientTO client = clients.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return client.getId();
            case 1:
                return client.getFirstName();
            case 2:
                return client.getLastName();
            case 3:
                return client.getContact().getAddress();
            case 4:
                return client.getContact().getCity();
            case 5:
                return client.getContact().getCountry();
            case 6:
                return client.getContact().getPhone();
            case 7:
                return client.getContact().getEmail();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public void addClient(ClientTO client) {
        if (client != null) {
            clients.add(client);
            int lastRow = getRowCount() - 1;
            fireTableRowsInserted(lastRow, lastRow);
        }
    }

    public void updateClient(ClientTO client, int row) {
        if (client != null) {
            clients.set(row, client);
            fireTableRowsUpdated(row, row);
        }
    }

    public void setClients(List<ClientTO> clients) {
        if (clients != null) {
            this.clients = new ArrayList<>(clients);
            fireTableDataChanged();
        }
    }

    public void removeClient(ClientTO client, int row) {
        if (client != null) {
            clients.remove(client);
            fireTableRowsDeleted(row, row);
        }
    }
}