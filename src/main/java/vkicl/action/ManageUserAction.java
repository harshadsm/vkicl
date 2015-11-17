package vkicl.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import vkicl.daoImpl.AdminDaoImpl;
import vkicl.form.UserForm;
import vkicl.util.Constants;
import vkicl.vo.UserInfoVO;

public class ManageUserAction extends BaseAction {
	private static Logger log = Logger.getLogger(ManageUserAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		UserInfoVO userInfoVO = null;
		UserForm userForm = null;
		AdminDaoImpl impl = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.MANAGE_USERS);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			userForm = (UserForm) form;
			impl = new AdminDaoImpl();
			log.info("userForm.getGenericListener() = "
					+ userForm.getGenericListener());
			if (!"".equalsIgnoreCase(userForm.getGenericListener())) {
				userForm = impl.updateUser(userForm, userInfoVO);
			}

			ArrayList<LabelValueBean> userNames, access = null;
			userNames = impl.getList(userInfoVO, "query.admin.user.all");
			userForm.setUserNames(userNames);
			access = impl.getList(userInfoVO, "query.admin.access.all");
			request.setAttribute("access", access);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return actionForward;
	}
}
