package vkicl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vkicl.daoImpl.PortDaoImpl;
import vkicl.form.PortOutwardForm;
import vkicl.services.PortInwardService;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.PortInwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortOutwardAction2 extends BaseAction {
	private static Logger log = Logger.getLogger(PortOutwardAction2.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
//		PortOutwardForm portOutwardForm = null;
//		String genericListener = null;
//		UserInfoVO userInfoVO = null;
		try {
			actionForward = checkAccess(mapping, request,
					Constants.Apps.PORT_ENTRY);
			if (null != actionForward)
				return actionForward;

			//Get the related Port Inward Record
//			String portInwardId = request.getParameter("port_inward_id");
//			PortInwardService portInwardService = new PortInwardService();
//			PortInwardRecordVO portInward = portInwardService.getPortInwardById(portInwardId);
//			request.setAttribute("portInward", portInward);
			
			
			
			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
//			userInfoVO = getUserProfile(request);
//			portOutwardForm = (PortOutwardForm) form;
//			genericListener = portOutwardForm.getGenericListener();
//			if (genericListener.equalsIgnoreCase("add")) {
//				String portInwardIdForLinking = request.getParameter("port_inward_id_for_linking_to_port_outward");
//				log.info("portInwardIdForLinking = " + portInwardIdForLinking);
//				
//				PortDaoImpl impl = new PortDaoImpl();
//				portOutwardForm = impl.addPortOutwardData(portOutwardForm,
//						userInfoVO);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
