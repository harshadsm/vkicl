package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class BaseAction extends Action {
	private static Logger log = Logger.getLogger(BaseAction.class);
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward checkAccess(ActionMapping mapping,
			HttpServletRequest request, String appName) {
		ActionForward actionForward = null;
		UserInfoVO userInfoVO = getUserProfile(request);
		if (!userInfoVO.isLoggedIn() || null == userInfoVO.getUserName()) {
			actionForward = mapping.findForward(Constants.Mapping.LOGIN_PAGE);
			request.getSession().setAttribute(
					Constants.USER_FORWARD,
					request.getRequestURL() == null ? null : request
							.getRequestURL().toString());
		} else if (!userInfoVO.hasAccess(appName)) {
			actionForward = mapping.findForward(Constants.Mapping.HOME_PAGE);
			String message = prop.get("error.page.access");
			userInfoVO.setMessage(message);
			log.error(userInfoVO.getUserName() + " does not have access to "
					+ appName);
			setUserProfile(request, userInfoVO);
		}
		return actionForward;
	}

	public UserInfoVO getUserProfile(HttpServletRequest request) {
		log.debug("URL = " + request.getRequestURL());

		HttpSession session = request.getSession(true);
		UserInfoVO userInfoVO = null;
		userInfoVO = (UserInfoVO) session
				.getAttribute(Constants.USER_INFO_SESSION);
		if (null == userInfoVO)
			userInfoVO = new UserInfoVO();
		setUserProfile(request, userInfoVO);
		return userInfoVO;
	}

	public void setUserProfile(HttpServletRequest request, UserInfoVO userInfoVO) {
		HttpSession session = request.getSession(true);
		if (null == userInfoVO.getMessage())
			userInfoVO.setMessage("");
		session.setAttribute(Constants.USER_INFO_SESSION, userInfoVO);
	}
}
