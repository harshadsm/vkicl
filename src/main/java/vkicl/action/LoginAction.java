package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class LoginAction extends BaseAction {
	private static Logger log = Logger.getLogger(LoginAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		log.info("Loading Login Page");
		ActionForward actionForward = null;
		UserInfoVO userInfoVO = null;
		try {
			request.getSession(true).invalidate();
			userInfoVO = getUserProfile(request);
			setUserProfile(request, userInfoVO);
			if (userInfoVO.isLoggedIn())
				actionForward = mapping
						.findForward(Constants.Mapping.HOME_PAGE);
			else
				actionForward = mapping
						.findForward(Constants.Mapping.LOGIN_PAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;

	}

}
