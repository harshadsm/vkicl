package vkicl.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import vkicl.form.PortOutwardForm;
import vkicl.services.PortOutwardService;
import vkicl.util.Constants;
import vkicl.util.PropFileReader;
import vkicl.vo.PortOutwardPostDataContainerVO;
import vkicl.vo.PortOutwardRecordVO;
import vkicl.vo.UserInfoVO;

public class PortOutwardAction extends BaseAction {
	private static Logger log = Logger.getLogger(PortOutwardAction.class);
	@SuppressWarnings("unused")
	private static PropFileReader prop = PropFileReader.getInstance();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = null;
		UserInfoVO userInfoVO = null;
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
			
			
			userInfoVO = getUserProfile(request);
			actionForward = mapping.findForward(Constants.Mapping.SUCCESS);
			Gson gson = new Gson();
			String postDataContainerStr = request.getParameter("itemsToSaveJson");
			log.info(postDataContainerStr);
			PortOutwardPostDataContainerVO postDataContainer = gson.fromJson(postDataContainerStr, PortOutwardPostDataContainerVO.class);
			
			PortOutwardService portOutwardService = new PortOutwardService();
			portOutwardService.processPortOutwardEntries(postDataContainer, request, userInfoVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionForward;
	}

}
