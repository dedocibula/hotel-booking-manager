package cz.fi.muni.pa165.hotelbookingmanager.transferobjects;

import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Andrej Galád
 */
public class ClientTO {

    private Long id;
    
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;
    
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;
    
    @NotNull
    @Valid
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
        return "Client{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", contact=" + contact + '}';
    }
}
