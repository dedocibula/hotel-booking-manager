package cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects;

import java.math.BigDecimal;
import java.util.Objects;


/**
 *
 * @author Andrej Galád
 */
public class RoomTO {
    
    private Long id;

    private BigDecimal pricePerNight;
    
    private HotelTO hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPricePerNight() {
        return this.pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public HotelTO getHotel() {
        return this.hotel;
    }

    public void setHotel(HotelTO hotel) {
        this.hotel = hotel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RoomTO)) {
            return false;
        }
        RoomTO other = (RoomTO) object;

        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", pricePerNight=" + pricePerNight + ", hotel=" + hotel + '}';
    }
}
