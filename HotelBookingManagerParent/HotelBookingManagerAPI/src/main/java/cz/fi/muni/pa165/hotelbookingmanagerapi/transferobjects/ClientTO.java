package cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects;

import java.util.Objects;


/**
 *
 * @author Andrej Galád
 */
public class ClientTO {

    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private ContactTO contact;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactTO getContact() {
        return contact;
    }

    public void setContact(ContactTO contact) {
        this.contact = contact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClientTO)) {
            return false;
        }
        ClientTO other = (ClientTO) object;
        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", email: " + contact.getEmail();
    }
}
