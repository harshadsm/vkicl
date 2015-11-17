package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.form.PortInwardForm;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.UserInfoVO;

public class PortInwardDetailsAction extends BaseAction {
	private static Logger log = Logger.getLogger(PortInwardDetailsAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		PortInwardForm portInwardForm = null;
		String genericListener = null;
		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.PORT_ENTRY);
			if (null != actionForward)
				return actionForward;

			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			userInfoVO = getUserProfile(request);
			portInwardForm = (PortInwardForm) form;
			genericListener = portInwardForm.getGenericListener();
			if (genericListener.equalsIgnoreCase("addDetails")) {
				PortDaoImpl impl = new PortDaoImpl();
				portInwardForm = impl.addPortInwardDetailsData(portInwardForm,
						userInfoVO);
			} else {
				log.info("Loaded Port - Inward Details");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
