package cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects;

import java.util.List;
import java.util.Objects;
import org.codehaus.jackson.annotate.JsonIgnore;


/**
 *
 * @author Andrej Galád
 */
public class HotelTO {

    private Long id;
    
    
    private String name;
    
    
    private ContactTO contact;
    
    private List<RoomTO> rooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactTO getContact() {
        return contact;
    }

    public void setContact(ContactTO contact) {
        this.contact = contact;
    }

    @JsonIgnore
    public List<RoomTO> getRooms() {
        return this.rooms;
    }

    @JsonIgnore
    public void setRooms(List<RoomTO> rooms) {
        this.rooms = rooms;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof HotelTO)) {
            return false;
        }
        HotelTO other = (HotelTO) object;
        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ", " + contact.getAddress() + ", " + contact.getCity() + ", " + contact.getCountry();
    }
}
