package cz.fi.muni.pa165.hotelbookingmanagerdesktop.tablemodels;

import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.HotelTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Filip Bogyai
 */
public class HotelTableModel extends AbstractTableModel{
    private static final long serialVersionUID = 1L;

    private List<HotelTO> hotels = new ArrayList<>();

    @Override
    public int getRowCount() {
        return hotels.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }
    //ID fname lname address city country tel. mail
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Name";
            case 2:                
                return "Address";
            case 3:
                return "City";
            case 4:
                return "Country";
            case 5:
                return "Telephone";
            case 6:
                return "Email";
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HotelTO hotel = hotels.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return hotel.getId();
            case 1:
                return hotel.getName();
            case 2:
                return hotel.getContact().getAddress();
            case 3:
                return hotel.getContact().getCity();
            case 4:
                return hotel.getContact().getCountry();
            case 5:
                return hotel.getContact().getPhone();
            case 6:
                return hotel.getContact().getEmail();
            default:
                throw new IllegalArgumentException("columnIndex");
        }
    }

    public void addHotel(HotelTO hotel) {
        if (hotel != null) {
            hotels.add(hotel);
            int lastRow = getRowCount() - 1;
            fireTableRowsInserted(lastRow, lastRow);
        }
    }

    public void updateHotel(HotelTO hotel, int row) {
        if (hotel != null) {
            hotels.set(row, hotel);
            fireTableRowsUpdated(row, row);
        }
    }

    public void setHotels(List<HotelTO> hotels) {
        if (hotels != null) {
            this.hotels = new ArrayList<>(hotels);
            fireTableDataChanged();
        }
    }

    public void removeHotel(HotelTO hotel, int row) {
        if (hotel != null) {
            hotels.remove(hotel);
            fireTableRowsDeleted(row, row);
        }
    }
    
}
