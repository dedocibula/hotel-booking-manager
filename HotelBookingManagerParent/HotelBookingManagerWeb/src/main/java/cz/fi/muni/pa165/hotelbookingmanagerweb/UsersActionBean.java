package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanagerapi.service.RegUserService;
import cz.fi.muni.pa165.hotelbookingmanagerapi.transferobjects.RegUserTO;
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
            @Validate(on = {"add"}, field = "username", required = true, minlength = 2, maxlength = 30),
			@Validate(on = {"save"}, field = "password", minlength = 2, maxlength = 30),
            @Validate(on = {"add"}, field = "password", required = true, minlength = 2, maxlength = 30),
            @Validate(on = {"add", "save"}, field = "client.firstName", required = true, minlength = 2, maxlength = 50),
            @Validate(on = {"add", "save"}, field = "client.lastName", required = true, minlength = 2, maxlength = 50),
            @Validate(on = {"add", "save"}, field = "client.contact.phone", required = true, mask = "\\d*", maxlength = 30),
            @Validate(on = {"add", "save"}, field = "client.contact.email", required = true, mask = "[\\w\\-\\.\\+]+@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z]+", minlength = 6, maxlength = 50),
            @Validate(on = {"add", "save"}, field = "client.contact.address", required = true, maxlength = 30),
            @Validate(on = {"add", "save"}, field = "client.contact.city", required = true, minlength = 2, maxlength = 50)
    })
    private RegUserTO user;
    
    public RegUserTO getLogged() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.findUserByUsername(username);
    }

    public RegUserTO getUser() {
        return user;
    }

    public void setUser(RegUserTO user) {
        this.user = user;
    }
    
    @SpringBean
    private RegUserService userService;
    
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
            userService.create(user);
            return new RedirectResolution("/index.jsp");
        } catch (DataAccessException e) {
            ValidationErrors errors = new ValidationErrors();
            errors.add( "user.username", new LocalizableError("validation.user.nameIsExisting") );
            getContext().setValidationErrors(errors);
            return getContext().getSourcePageResolution();
        }
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit"})
    public void loadUserFromDatabase() {
        String ids = context.getRequest().getParameter("user.id");
        if (ids == null) {
            return;
        }
        user = userService.get(Long.parseLong(ids));
    }

    public Resolution edit() {
        return new ForwardResolution("/users/editUser.jsp");
    }

    public Resolution save() {
        String ids = context.getRequest().getParameter("user.id");
        if (ids == null) {
            return new RedirectResolution(this.getClass(), "details");
        }
        RegUserTO userTmp = userService.get(Long.parseLong(ids));
        user.setUsername(userTmp.getUsername());
        if (user.getPassword() == null) {
                user.setPassword(userTmp.getPassword());
        }
        user.getClient().setId(userTmp.getClient().getId());
		
        userService.update(user);
        return new RedirectResolution(this.getClass(), "details");
    }
}
