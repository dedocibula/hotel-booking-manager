package cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects;

import java.util.Objects;

/**
 *
 * @author Andrej Galád
 */
public class RegUserTO {
    
    private Long id;
    
    private String username;
    
    private String password;
    
    private ClientTO client;  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientTO getClient() {
        return client;
    }

    public void setClient(ClientTO client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RegUserTO)) {
            return false;
        }
        RegUserTO other = (RegUserTO) object;
        if (this.id != null && !Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegUser{" + "id=" + id + ", username=" + username + ", password=" + password + "}" ;
    }
}
