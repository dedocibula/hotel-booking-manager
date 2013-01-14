/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.impl.RegUserDAOImpl;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RegUserDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.RegUser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
 
/**
 * A custom service for retrieving users from a custom datasource, such as a database.
 * <p>
* This custom service must implement Spring's {@link UserDetailsService}
 */
@Transactional(readOnly = true)
public class RegUserDetailsService implements UserDetailsService {
  
 
     @Autowired
     private RegUserDAO userDAO;

     /**
      * Retrieves a user record containing the user's credentials and access. 
      */
    @Override
     public UserDetails loadUserByUsername(String username)
       throws UsernameNotFoundException, DataAccessException {

      // Declare a null Spring User
      UserDetails user = null;

      try {
       
         RegUser regUser = userDAO.findUserByUsername(username);       

         user =  new User(regUser.getUsername(), 
                 regUser.getPassword().toLowerCase(),
                 true,
                 true,
                 true,
                 true,
                 getAuthorities());

      } catch (Exception e) {
       
       throw new UsernameNotFoundException("Error in retrieving user");
      }
      
      return user;
     }
     
      public Collection<GrantedAuthority> getAuthorities() {
       // Create a list of grants for this user
       List<GrantedAuthority> authList = new ArrayList<>(2);

       // All users are granted with ROLE_USER access
       // Therefore this user gets a ROLE_USER by default
       
       authList.add(new GrantedAuthorityImpl("ROLE_USER"));

       return authList;
       }
}