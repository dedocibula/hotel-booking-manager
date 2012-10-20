package cz.fi.muni.pa165.hotelbookingmanager.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.*;

/**
 * Entity Room
 *
 * @author Marián Rusnák
 */
@Entity(name="Room")
@TableGenerator(name="Room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable=false)
    private boolean vacant;
    @Column(nullable=false, length=15)
    private BigDecimal pricePerNight;

    @ManyToOne
    @JoinColumn(nullable=false)
    private Hotel hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isVacant() {
        return this.vacant;
    }

    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }

    public BigDecimal getPricePerNight() {
        return this.pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
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
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;

        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", vacant=" + vacant + ", pricePerNight=" + pricePerNight + ", hotel=" + hotel + '}';
    }
}
