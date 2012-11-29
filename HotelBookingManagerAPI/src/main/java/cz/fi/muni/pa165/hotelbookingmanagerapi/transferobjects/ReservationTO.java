package cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;


/**
 *
 * @author Andrej Galád
 */
public class ReservationTO {
    
    private Long id;
    
   
    private ClientTO client;
    
    
    private RoomTO room;
    
    
    private Date fromDate;
    
    
    private Date toDate;
    
    
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientTO getClient() {
        return this.client;
    }

    public void setClient(ClientTO client) {
        this.client = client;
    }

    public RoomTO getRoom() {
        return this.room;
    }

    public void setRoom(RoomTO room) {
        this.room = room;
    }

    public Date getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ReservationTO)) {
            return false;
        }
        ReservationTO other = (ReservationTO) object;
        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", client=" + client + ", room=" + room + ", fromDate=" + fromDate + ", toDate=" + toDate + ", price=" + price + '}';
    }
}
