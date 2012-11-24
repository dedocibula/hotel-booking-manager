package cz.fi.muni.pa165.hotelbookingmanagerweb;

import cz.fi.muni.pa165.hotelbookingmanager.service.interfaces.ReservationService;
import cz.fi.muni.pa165.hotelbookingmanagerhelper.ReservationServiceImpl;
import java.text.DateFormatSymbols;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 *
 * @author Marian Rusnak
 */
@UrlBinding("/reservations/{$event}")
public class ReservationsActionBean implements ActionBean {
	
	private ActionBeanContext context;
	private ReservationService reservationService = new ReservationServiceImpl();
//	private Object[] months = (new DateFormatSymbols()).getMonths();
//
//	public Object[] getMonths() {
//		return months;
//	}
//
//	public void setMonths(Object[] months) {
//		this.months = months;
//	}
	
	@Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }
	
	@DefaultHandler
	public Resolution all() {
//		String[] monthNames = (new DateFormatSymbols()).getMonths();
//		Object[] months = new Object[12];
//		for(int i = 0; i < 12; i++) {
////			Month month = new Month();
////			month.setIndex(i);
////			month.setName(monthNames[i]);
//			months[i] = monthNames[i];
//		}
//		setMonths(months);
		//setMonths((new DateFormatSymbols()).getMonths());
		return new ForwardResolution("test.jsp");
	}
	
	public Resolution other() {
//		String[] monthNames = (new DateFormatSymbols()).getMonths();
//		Object[] months = new Object[12];
//		for(int i = 0; i < 12; i++) {
////			Month month = new Month();
////			month.setIndex(i);
////			month.setName(monthNames[i]);
//			months[i] = monthNames[i];
//		}
//		setMonths(months);
		//setMonths((new DateFormatSymbols()).getMonths());
		return new RedirectResolution(this.getClass(), "all");
	}
}
