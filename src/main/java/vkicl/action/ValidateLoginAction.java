package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.LoginDaoImpl;
import vkicl.form.LoginForm;
import vkicl.util.Constants;
import vkicl.util.Converter;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class ValidateLoginAction extends BaseAction
{
	private static Logger log = Logger.getLogger(ValidateLoginAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		log.info("Validating Login");
		ActionForward actionForward = null;
		LoginForm loginForm = null;
		UserInfoVO userInfoVO = null;
		LoginDaoImpl loginDaoImpl = null;
		try
		{
			loginForm = (LoginForm) form;
			userInfoVO = getUserProfile(request);
			userInfoVO = Converter.formToVo(loginForm, userInfoVO);
			loginDaoImpl = new LoginDaoImpl();
			userInfoVO = loginDaoImpl.validateLogin(userInfoVO);
			form = Converter.voToForm(userInfoVO, loginForm);
			if (userInfoVO.isLoggedIn())
			{
				log.info("Login Successful");
				actionForward = mapping
						.findForward(Constants.Mapping.LOGIN_SUCCESS);
			}
			else
			{
				log.info("Login Failed");
				actionForward = mapping
						.findForward(Constants.Mapping.LOGIN_PAGE);
			}
			setUserProfile(request, userInfoVO);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return actionForward;
	}

}
