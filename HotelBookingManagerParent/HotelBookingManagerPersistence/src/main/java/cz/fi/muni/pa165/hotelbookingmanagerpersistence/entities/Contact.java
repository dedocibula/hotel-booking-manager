package cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Embeddable class Contact
 * 
 * @author Andrej Galád
 */
@Embeddable
public class Contact implements Serializable {

    @NotNull
    @Size(max = 30)
    @Pattern(regexp = "\\d*")
    @Column(nullable = false, length = 30)
    private String phone;
    
    @NotNull
    @Size(min = 6, max = 50)
    @Pattern(regexp = "[\\w\\-\\.\\+]+@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z]+")
    @Column(nullable = false, length = 50)
    private String email;
    
    @NotNull
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String address;
    
    @NotNull
    @Size(min = 2, max = 30)
    @Column(nullable = false, length = 30)
    private String city;
    
    @NotNull
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String country;

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.phone);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.address);
        hash = 29 * hash + Objects.hashCode(this.city);
        hash = 29 * hash + Objects.hashCode(this.country);
        return hash;
    }

    @Override
    public String toString() {
        return "Contact{" + "phone=" + phone + ", email=" + email + ", address=" + address + ", city=" + city + ", country=" + country + '}';
    }
}