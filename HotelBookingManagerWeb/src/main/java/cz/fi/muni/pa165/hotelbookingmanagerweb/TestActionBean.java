package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ClientService;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Marian Rusnak
 */
@UrlBinding("/test/{$event}")
public class TestActionBean implements ActionBean {

	private ActionBeanContext context;
    @Autowired
    protected ClientService clientService;

    @DefaultHandler
    public Resolution home() {
        return new ForwardResolution("/test.jsp");
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }
	
}
