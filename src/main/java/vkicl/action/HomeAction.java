package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.util.Constants;
import vkicl.vo.UserInfoVO;

public class HomeAction extends BaseAction {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(HomeAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		UserInfoVO userInfoVO = null;
		try {
			userInfoVO = getUserProfile(request);
			if (userInfoVO.isLoggedIn())
				actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			else
				actionForward = mapping
						.findForward(Constants.Mapping.LOGIN_PAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return actionForward;
	}

}
