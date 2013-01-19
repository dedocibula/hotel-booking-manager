package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanagerpersistence.dao.interfaces.RegUserDAO;
import cz.fi.muni.pa165.hotelbookingmanagerpersistence.entities.RegUser;
import java.util.Set;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Andrej Galád
 */
@UrlBinding("/users/{$event}/{user.id}")
public class UsersActionBean implements ActionBean {
 
    private ActionBeanContext context;
    
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "username", required = true, minlength = 2, maxlength = 30),
            @Validate(on = {"add", "save"}, field = "password", required = true, minlength = 2, maxlength = 30),
            @Validate(on = {"add", "save"}, field = "client.firstName", required = true, minlength = 2, maxlength = 50),
            @Validate(on = {"add", "save"}, field = "client.lastName", required = true, minlength = 2, maxlength = 50),
            @Validate(on = {"add", "save"}, field = "client.contact.phone", required = true, mask = "\\d*", maxlength = 30),
            @Validate(on = {"add", "save"}, field = "client.contact.email", required = true, mask = "[\\w\\-\\.\\+]+@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z]+", minlength = 6, maxlength = 50),
            @Validate(on = {"add", "save"}, field = "client.clcontact.address", required = true, maxlength = 30),
            @Validate(on = {"add", "save"}, field = "client.contact.city", required = true, minlength = 2, maxlength = 50)
    })
    private RegUser user; // temporary, must be DTO

    public RegUser getUser() {
        if (user != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            user = userDAO.findUserByUsername(username);
        }
        return user;
    }

    public void setUser(RegUser user) {
        this.user = user;
    }
    
    @SpringBean
    private RegUserDAO userDAO; // temporary, must be service
    
    private CountryPicker countryPicker = new CountryPicker();

    public Set<String> getCountries() {
        return countryPicker.getCountriesName();
    }
    
    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }
    
    @DefaultHandler
    public Resolution userDetails() {
        return new ForwardResolution("/users/user.jsp");
    }
    
    public Resolution register() {
        return new ForwardResolution("/register.jsp");
    }
    
    public Resolution add() {
        try {
            userDAO.create(user);
            return new ForwardResolution("/index.jsp");
        } catch (DataAccessException e) {
            ValidationErrors errors = new ValidationErrors();
            errors.add( "user.userName", new LocalizableError("/users/validation.user.nameIsExisting") );
            getContext().setValidationErrors(errors);
            return getContext().getSourcePageResolution();
        }
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save"})
    public void loadHotelFromDatabase() {
        String ids = context.getRequest().getParameter("user.id");
        if (ids == null) {
            return;
        }
        user = userDAO.get(Long.parseLong(ids));
    }

    public Resolution edit() {
        return new ForwardResolution("/users/editUser.jsp");
    }

    public Resolution save() {
        userDAO.update(user);
        return new RedirectResolution(this.getClass(), "details");
    }
}
