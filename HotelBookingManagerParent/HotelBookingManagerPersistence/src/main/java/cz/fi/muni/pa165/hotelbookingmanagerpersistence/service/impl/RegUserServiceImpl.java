package cz.fi.muni.pa165.hotelbookingmanagerpersistence.service.impl;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.RegUserService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RegUserTO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.ClientDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RegUserDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.RegUser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Marian Rusnak
 */
public class RegUserServiceImpl implements RegUserService {

	@Autowired
	private RegUserDAO userDAO;
	@Autowired
	private ClientDAO clientDAO;
	@Autowired
	private Validator validator;
	@Autowired
	private Mapper mapper;
	
	@Override
	public void create(RegUserTO userTO) {
		if (userTO == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		RegUser user = mapper.map(userTO, RegUser.class);
		validateUser(user);
		if (user.getId() != null) {
			throw new IllegalArgumentException("User cannot have manually assigned id.");
		}

		userDAO.create(user);

		userTO.setId(user.getId());
	}

	@Override
	public RegUserTO get(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null.");
		}

		RegUser reservation = userDAO.get(id);
		return reservation != null ? mapper.map(reservation, RegUserTO.class) : null;
	}

	@Override
	public void update(RegUserTO userTO) {
		if (userTO == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		RegUser user = mapper.map(userTO, RegUser.class);
		validateUserIncludingId(user);
		
		clientDAO.update(user.getClient());
		userDAO.update(user);
	}

	@Override
	public void delete(RegUserTO userTO) {
		if (userTO == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		RegUser user = mapper.map(userTO, RegUser.class);

		userDAO.delete(user);
		clientDAO.delete(user.getClient());
	}

	@Override
	public RegUserTO findUserByUsername(String username) {
		if (username == null || "".equals(username.trim())) {
			throw new IllegalArgumentException("Username cannot be empty.");
		}
		RegUser user = userDAO.findUserByUsername(username);
		return mapper.map(user, RegUserTO.class);
	}
	
	/**
	 * Retrieves a user record containing the user's credentials and access.
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		// Declare a null Spring User
		UserDetails user = null;

		try {

			RegUserTO userTO = findUserByUsername(username);

			user = new User(userTO.getUsername(),
					userTO.getPassword().toLowerCase(),
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
	
	private void validateUser(RegUser user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		validateUserAttrributes(user);
	}
	
	private void validateUserIncludingId(RegUser user) {
		validateUser(user);
		if (user.getId() == null) {
			throw new IllegalArgumentException("User must have id already set.");
		}
	}
	
	private void validateUserAttrributes(RegUser user) throws IllegalArgumentException {
		Set<ConstraintViolation<RegUser>> validationResults = validator.validate(user);
		if (!validationResults.isEmpty()) {
			throw new IllegalArgumentException("User parameters are invalid: "
					+ validationResults.iterator().next().getMessage());
		}
	}
}
