package solubris.marketmon.web;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Example on how to redirect users based on permissions
 * Not being used currently
 * 
 * @author walterst
 */
@Controller
public class LoginRedirectController {
 
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, SecurityContextHolderAwareRequestWrapper request) {
	    if(request.isUserInRole("ROLE_ADMIN")) {
	        return "redirect:/superusers";
	    } else {
	        return "redirect:/allusers";
	    }
	} 
}