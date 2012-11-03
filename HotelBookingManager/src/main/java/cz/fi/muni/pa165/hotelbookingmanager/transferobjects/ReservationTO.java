package cz.fi.muni.pa165.hotelbookingmanager.transferobjects;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Andrej Galád
 */
public class ReservationTO {
    
    private Long id;
    
    @NotNull
    @Valid
    private ClientTO client;
    
    @NotNull
    @Valid
    private RoomTO room;
    
    @NotNull
    @Future
    private Date fromDate;
    
    @NotNull
    @Future
    private Date toDate;
    
    @NotNull
    @Min(value = 0)
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
