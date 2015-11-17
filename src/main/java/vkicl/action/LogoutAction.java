package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.util.Constants;
import vkicl.util.PropFileReader;

public class LogoutAction extends BaseAction {
	private static Logger log = Logger.getLogger(LogoutAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("Logging Out");
		ActionForward actionForward;
		HttpSession session = request.getSession();
		session.invalidate();
		session = null;
		actionForward = mapping.findForward(Constants.Mapping.LOGIN_PAGE);
		return actionForward;
	}

}
